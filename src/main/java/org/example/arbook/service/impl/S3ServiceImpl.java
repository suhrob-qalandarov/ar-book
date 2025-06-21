package org.example.arbook.service.impl;

import org.example.arbook.service.interfaces.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class S3ServiceImpl implements S3Service {

    @Override
    public String uploadAttachment(MultipartFile file) throws IOException {
        return "";
    }

    @Override
    public byte[] getFile(String url) throws IOException {
        return new byte[0];
    }
}
