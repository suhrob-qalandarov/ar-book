package org.example.arbook.service.interfaces;

import jakarta.servlet.http.HttpServletResponse;
import org.example.arbook.model.dto.response.AttachmentRes;
import org.example.arbook.model.entity.Attachment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface AttachmentService {

    UUID uploadOne(MultipartFile file);

    List<UUID> uploadMultiple(List<MultipartFile> files);

    void update(UUID attachmentId, MultipartFile file);

    void get(UUID attachmentId, HttpServletResponse response) throws IOException;

    Attachment getAttachment(UUID attachmentId);

    void validateAttachmentId(UUID attachmentId);

    List<AttachmentRes> convertToAttachmentResList(List<Attachment> attachments);

    AttachmentRes convertToAttachmentRes(Attachment attachment);

    void getWithUrl(String attachmentUrl, HttpServletResponse response) throws IOException;
}
