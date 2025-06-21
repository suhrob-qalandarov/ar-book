package org.example.arbook.service.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface S3Service {

    String uploadAttachment(MultipartFile file) throws IOException;

    byte[] getFile(String url) throws IOException;
}
