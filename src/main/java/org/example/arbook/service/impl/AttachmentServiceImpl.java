package org.example.arbook.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.arbook.model.dto.response.AttachmentRes;
import org.example.arbook.model.entity.Attachment;
import org.example.arbook.repository.AttachmentRepository;
import org.example.arbook.service.interfaces.AttachmentService;
import org.example.arbook.service.interfaces.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final S3Service s3Service;

    @Override
    public Attachment uploadOne(MultipartFile file) {
        return null;
    }

    @Override
    public List<Attachment> uploadMultiple(MultipartFile[] files) {
        return List.of();
    }

    @Override
    public AttachmentRes update(Long attachmentId, MultipartFile file) {
        return null;
    }

    @Override
    public void get(Long attachmentId, HttpServletResponse response) throws IOException {
        try {
            var attachment = getAttachment(attachmentId);
            byte[] fileContent = getFileContent(attachment.getFileUrl());

            response.getOutputStream().write(fileContent);
            response.getOutputStream().flush();
        } catch (IOException | S3Exception e) {
            log.error("Failed to fetch file for attachment ID {}: {}", attachmentId, e.getMessage());
            throw new IOException("Unable to retrieve file", e);
        }
    }

    private byte[] getFileContent(String url) throws IOException, S3Exception {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("S3 URL cannot be null or empty");
        }

       /* if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        } */
        return s3Service.getFile(url);
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
