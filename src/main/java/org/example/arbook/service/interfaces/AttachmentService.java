package org.example.arbook.service.interfaces;

import jakarta.servlet.http.HttpServletResponse;
import org.example.arbook.model.dto.response.AttachmentRes;
import org.example.arbook.model.entity.Attachment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface AttachmentService {

    Long uploadOne(MultipartFile file);

    List<Long> uploadMultiple(MultipartFile[] files);

    void update(Long attachmentId, MultipartFile file);

    void get(Long attachmentId, HttpServletResponse response) throws IOException;

    Attachment getAttachment(Long attachmentId);

    void validateAttachmentId(Long attachmentId);

    List<AttachmentRes> convertToAttachmentResList(List<Attachment> attachments);

    AttachmentRes convertToAttachmentRes(Attachment attachment);

}
