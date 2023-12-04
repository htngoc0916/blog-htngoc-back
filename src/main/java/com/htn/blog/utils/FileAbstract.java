package com.htn.blog.utils;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.UploadResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public abstract class FileAbstract {
    @Value("${file.upload-path}")
    private String uploadPath;
    private String folder;

    public FileAbstract() {
        this.folder = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
    public void createDirectory() throws Exception{
        Path path = Paths.get(uploadPath).resolve(folder);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public void validationFile(List<MultipartFile> files){
        if (files.size() > BlogConstants.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new RuntimeException("Can only upload a maximum of 5 files");
        }
        if (files.isEmpty()){
            throw new RuntimeException("Please select a file");
        }
        for(MultipartFile file : files){
            if(file.getContentType() == null || !file.getContentType().startsWith("image/")) {
                throw new RuntimeException("Upload file must be image");
            }
        }
    }

    public UploadResponseDTO uploadFileStore(MultipartFile file) throws IOException {
        String randomUUID = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = randomUUID.concat(fileExtension);
        String savePath = String.join(File.separator, folder, fileName);

        Path path = Paths.get(uploadPath).resolve(savePath);
        Files.copy(file.getInputStream(), path);

        return UploadResponseDTO.builder()
                .fileUrl(savePath)
                .fileName(fileName)
                .fileOriginName(originalFilename)
                .fileType(fileExtension)
                .fileSize(file.getSize())
                .build();
    }

    public Resource loadFileStore(String fileName) throws IOException{
        Path file = Paths.get(uploadPath).resolve(fileName);
        Resource resource = new UrlResource(file.toUri());
        if(resource.exists() && resource.isReadable()){
            return resource;
        }
        throw new RuntimeException("Could not read the file!");
    }

    public boolean deleteFileStore(String fileName) throws IOException{
        Path file = Paths.get(uploadPath).resolve(fileName);
        return Files.deleteIfExists(file);
    }
}
