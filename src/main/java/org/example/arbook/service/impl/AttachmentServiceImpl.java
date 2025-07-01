package org.example.arbook.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.arbook.model.dto.response.AttachmentRes;
import org.example.arbook.model.entity.Attachment;
import org.example.arbook.model.enums.ContentType;
import org.example.arbook.repository.AttachmentRepository;
import org.example.arbook.service.interfaces.AttachmentService;
import org.example.arbook.service.interfaces.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final S3Service s3Service;

    @Override
    public void get(Long attachmentId, HttpServletResponse response) throws IOException {
        try {
            var attachment = getAttachment(attachmentId);
            byte[] fileContent = getFileContent(attachment.getFileUrl());

            String mimeType = switch (attachment.getContentType()) {
                case IMAGE -> "image/jpeg";
                case AUDIO -> "audio/mpeg";
                case FILE_3D -> "model/gltf-binary";
            };

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", "inline; filename=\"" +
                    (attachment.getId() != null ? "attachment_" + attachment.getId() : "attachment") +
                    getFileExtension(attachment.getContentType()) + "\"");
            response.getOutputStream().write(fileContent);
            response.getOutputStream().flush();
        } catch (IOException | S3Exception e) {
            log.error("Failed to fetch file for attachment ID {}: {}", attachmentId, e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new IOException("Unable to retrieve file", e);
        }
    }

    private byte[] getFileContent(String fileUrl) throws IOException, S3Exception {
        if (fileUrl == null || fileUrl.isBlank()) {
            throw new IllegalArgumentException("File URL cannot be null or empty");
        }
        return s3Service.getFile(fileUrl);
    }

    private String getFileExtension(ContentType contentType) {
        return switch (contentType) {
            case IMAGE -> ".jpg";
            case AUDIO -> ".mp3";
            case FILE_3D -> ".glb";
        };
    }

    @Override
    public Long uploadOne(MultipartFile file) {
        validateFile(file);
        String key;
        try {
            key = s3Service.uploadAttachment(file);
        } catch (IOException e) {
            log.error("Failed to upload file {} to S3: {}", file.getOriginalFilename(), e.getMessage());
            throw new RuntimeException("Unable to upload file to S3", e);
        }
        return saveAttachment(file, key);
    }

    @Override
    public List<Long> uploadMultiple(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("No files provided for upload");
        }

        return files.stream().map(this::uploadOne).toList();
    }

    @Override
    public void update(Long attachmentId, MultipartFile file) {
        validateAttachmentId(attachmentId);
        validateFile(file);
        var existingAttachment = getAttachment(attachmentId);

        String newKey;
        try {
            // Yangi faylni S3 ga yuklash
            newKey = s3Service.uploadAttachment(file);
            // Eski faylni S3 dan o'chirish
            //s3Service.deleteFile(existingAttachment.getFileUrl());
        } catch (IOException e) {
            log.error("Failed to update file for attachment ID {}: {}", attachmentId, e.getMessage());
            throw new RuntimeException("Unable to update file in S3", e);
        }

        // Attachment ni yangilash
        existingAttachment.setFileUrl(newKey);
        existingAttachment.setContentType(determineContentType(file));
        try {
            attachmentRepository.save(existingAttachment);
        } catch (Exception e) {
            log.error("Failed to update attachment ID {}: {}", attachmentId, e.getMessage());
            throw new RuntimeException("Unable to update attachment in database", e);
        }
    }

    private Long saveAttachment(MultipartFile file, String key) {
        Attachment newAttachment = Attachment.builder()
                .fileUrl(key)
                .contentType(determineContentType(file))
                .isActive(true)
                .build();
        try {
            Attachment saved = attachmentRepository.save(newAttachment);
            return saved.getId();
        } catch (Exception e) {
            log.error("Failed to save attachment for file {}: {}", file.getOriginalFilename(), e.getMessage());
            //s3Service.deleteFile(key); // Agar DB da xato bo'lsa, S3 dan faylni o'chirish
            throw new RuntimeException("Unable to save attachment to database", e);
        }
    }

    private ContentType determineContentType(MultipartFile file) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        if (contentType == null) {
            throw new IllegalArgumentException("File content type cannot be determined");
        }

        // Handle content type based on MIME type
        return switch (contentType.toLowerCase()) {
            case "image/jpeg", "image/png", "image/gif" -> ContentType.IMAGE;
            case "audio/mpeg", "audio/wav" -> ContentType.AUDIO;
            case "model/gltf-binary", "model/gltf+json" -> ContentType.FILE_3D;
            case "application/octet-stream" -> {
                // Check file extension for octet-stream
                if (fileName != null && fileName.toLowerCase().endsWith(".glb")) {
                    yield ContentType.FILE_3D;
                }
                throw new IllegalArgumentException("Unsupported file type: " + contentType);
            }
            default -> throw new IllegalArgumentException("Unsupported file type: " + contentType);
        };
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }
        if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
    }

    @Override
    public Attachment getAttachment(Long attachmentId) {
        validateAttachmentId(attachmentId);
        return attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new EntityNotFoundException("Active attachment not found with ID: " + attachmentId));
    }

    @Override
    public void validateAttachmentId(Long attachmentId) {
        if (attachmentId == null || attachmentId <= 0) {
            throw new IllegalArgumentException("Invalid attachment ID: " + attachmentId);
        }
    }

    @Override
    public List<AttachmentRes> convertToAttachmentResList(List<Attachment> attachments) {
        return attachments.stream()
                .map(this::convertToAttachmentRes)
                .collect(Collectors.toList());
    }

    @Override
    public AttachmentRes convertToAttachmentRes(Attachment attachment) {
        return AttachmentRes.builder()
                .id(attachment.getId())
                .fileUrl(attachment.getFileUrl())
                .contentType(attachment.getContentType())
                .build();
    }
}