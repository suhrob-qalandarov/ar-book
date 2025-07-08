package org.example.arbook.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.arbook.service.interfaces.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    private final S3Client s3Client;

    @Override
    public String uploadAttachment(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }

        String filename = file.getOriginalFilename() != null ? file.getOriginalFilename() : file.getName();
        System.out.println("ORIGINAL ✅✅✅ :" +file.getOriginalFilename());
        System.out.println("ORIGINAL 😆😆😆 :" +file.getName());
        String key = System.currentTimeMillis() + "_" + filename;

        // Set metadata explicitly
        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("Content-Type", file.getContentType());

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .metadata(metadataMap) // pass content-type here
                .build();

        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(objectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
            log.info("Uploaded file to S3 with key: {}", key);
        } catch (S3Exception e) {
            log.error("Failed to upload file to S3: {}", e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Unable to upload file to S3", e);
        }

        return key;
    }

    @Override
    public byte[] getFile(String key) throws IOException {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("S3 key cannot be null or empty");
        }

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try (var inputStream = s3Client.getObject(request)) {
            log.debug("Retrieved file from S3 with key: {}", key);
            return inputStream.readAllBytes();

        } catch (S3Exception e) {
            log.error("Failed to retrieve file from S3 with key: {}", key, e);
            throw new RuntimeException("Unable to retrieve file from S3", e);
        }
    }
}
