package com.htn.blog.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@AllArgsConstructor
public abstract class FileAbstract {
    @Value("${file.upload-path}")
    private String uploadPath;
    private String folder;

    public FileAbstract() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        this.folder = currentDate.format(formatter);
    }
    public void createDirectory() throws Exception{
        Path path = Paths.get(String.join(File.separator, uploadPath, folder));
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public boolean validationFile(MultipartFile file){
        if (file.isEmpty()) {
            return false;
        }
        return true;
    }

    public String uploadFileStore(MultipartFile file) throws IOException {
        String randomUUID = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = randomUUID.concat(fileExtension);

        String savePath = String.join(File.separator, uploadPath, folder, fileName);

        Files.copy(file.getInputStream(), Paths.get(savePath));

        return savePath;
    }
}
