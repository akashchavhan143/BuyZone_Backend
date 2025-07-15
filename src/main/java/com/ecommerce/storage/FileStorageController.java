package com.ecommerce.storage;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileStorageController {

    @Autowired
    private  StorageService storageService;
    @Value("${com.onlineshopping.image.folder.path}")
    private String uploadDir;

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Adjust as needed
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file) {

        // Input validation
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new FileUploadResponse("File cannot be empty"));
        }

        try {
            String fileUrl = storageService.store(file);
            return ResponseEntity.ok(new FileUploadResponse(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize(),
                    fileUrl
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new FileUploadResponse("Unexpected error: " + e.getMessage()));
        }
    }

    public static record FileUploadResponse(
            String filename,
            String contentType,
            long size,
            String fileUrl,
            String error
    ) {
        public FileUploadResponse(String filename, String contentType, long size, String fileUrl) {
            this(filename, contentType, size, fileUrl, null);
        }
        public FileUploadResponse(String error) {
            this(null, null, 0, null, error);
        }
    }
}
