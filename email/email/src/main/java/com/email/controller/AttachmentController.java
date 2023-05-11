package com.email.controller;

import com.email.entity.Attachment;
import com.email.entity.User;
import com.email.service.AttachmentService;
import com.email.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;
    private final Path attachmentStorageLocation;

    @Autowired
    public AttachmentController(@Value("${attachment.storage.location}") String attachmentStorageLocation) {
        this.attachmentStorageLocation = Paths.get(attachmentStorageLocation)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.attachmentStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @PostMapping("/upload")
    public R uploadFile(@RequestParam("file") MultipartFile file, HttpSession session) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        String randomPath = UUID.randomUUID().toString().substring(0, 8);
        String randomFilename = UUID.randomUUID().toString().substring(0, 8) + "." + fileExtension;

        try {
            if (randomFilename.contains("..")) {
                throw new IllegalArgumentException("Invalid file name: " + randomFilename);
            }

            Attachment attachment = new Attachment();
            attachment.setFileName(originalFilename);
            attachment.setFileSize((int) file.getSize());
            attachment.setCreatedAt(new Date());
            User user = (User) session.getAttribute("user");
            attachment.setCreator(user.getEmail());

            Path targetLocation = this.attachmentStorageLocation.resolve(randomPath).resolve(originalFilename);
            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            attachment.setFilePath(targetLocation.toString());

            attachmentService.save(attachment);
            return new R(true, attachment.getId());
        } catch (IOException ex) {
            return new R(false, "上传失败");
        }
    }



    @GetMapping("/download/{attachmentId}")
    public ResponseEntity<UrlResource> downloadFile2(@PathVariable Integer attachmentId) throws IOException {
        Attachment attachment = attachmentService.getById(attachmentId);
        if (attachment == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = Paths.get(attachment.getFilePath());
        UrlResource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (resource == null || !resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachment.getFileName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));

        return ResponseEntity.ok().headers(headers).body(resource);
    }



    @GetMapping("/getfilename/{attachmentId}")
    public R downloadFile1(@PathVariable Integer attachmentId) {
        Attachment attachment = attachmentService.getById(attachmentId);
        return new R(true,attachment.getFileName());

    }


}
