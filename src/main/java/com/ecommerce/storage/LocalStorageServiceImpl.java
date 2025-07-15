package com.ecommerce.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Service
@Profile("dev")
public class LocalStorageServiceImpl implements StorageService {

    @Value("${com.onlineshopping.image.folder.path}")
    private String basePath;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public List<String> loadAll() {
        return null;
    }

    @Override
    public String store(MultipartFile file) {
        try {
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID() + ext;
            File filePath = new File(basePath, fileName);

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                FileCopyUtils.copy(file.getInputStream(), out);
            }

            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/")
                    .path(fileName)
                    .toUriString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file locally", e);
        }
    }

    @Override
    public Resource load(String fileName) {
        File filePath = new File(basePath, fileName);
        return filePath.exists() ? new FileSystemResource(filePath) : null;
    }

    @Override
    public void delete(String fileName) {
        new File(basePath, fileName).delete();
    }
}