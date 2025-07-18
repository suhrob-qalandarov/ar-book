package org.example.arbook.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.arbook.model.dto.response.AttachmentRes;
import org.example.arbook.service.interfaces.AttachmentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.example.arbook.util.ApiConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + ADMIN + ATTACHMENT)
public class AdminAttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UUID> uploadFile(@RequestParam("file") MultipartFile file) {
          log.debug("Uploading single file: {}", file.getOriginalFilename());
        UUID savedAttachmentId = attachmentService.uploadOne(file);
        return ResponseEntity.ok(savedAttachmentId);
    }

//    @PostMapping()
//    public ResponseEntity<List<Long>> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> multipartFiles) {
//        log.debug("Uploading multiple files: {}", multipartFiles.size());
//        List<Long> filesLongId = attachmentService.uploadMultiple(multipartFiles);
//        return ResponseEntity.ok(filesLongId);
//    }




    @PutMapping("/{attachmentId}")
    public ResponseEntity<Void> updateFile(@PathVariable UUID attachmentId, @RequestParam("file") MultipartFile file) {
        log.debug("Updating attachment ID: {}", attachmentId);
        attachmentService.update(attachmentId, file);
        return ResponseEntity.ok().build();
    }
}
