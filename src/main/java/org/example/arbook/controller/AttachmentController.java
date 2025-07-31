package org.example.arbook.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.arbook.service.interfaces.AttachmentService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

import static org.example.arbook.util.ApiConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + ATTACHMENT)
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/{attachmentId}")
    public void getAttachment(@PathVariable UUID attachmentId, HttpServletResponse response) throws IOException {
        log.debug("Fetching attachment with ID: {}", attachmentId);
        attachmentService.get(attachmentId, response);
        log.info("Successfully served attachment: {}", attachmentId);
    }

    @GetMapping("/with-url/{attachmentUrl}")
    public void getAttachment(@PathVariable String attachmentUrl, HttpServletResponse response) throws IOException {
        log.debug("Fetching attachment with ID: {}", attachmentUrl);
        attachmentService.getWithUrl(attachmentUrl, response);
        log.info("Successfully served attachment: {}", attachmentUrl);
    }
}
