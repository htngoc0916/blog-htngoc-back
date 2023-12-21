package com.htn.blog.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.exception.FileStorageException;
import com.htn.blog.exception.MyFileNotFoundException;
import com.htn.blog.repository.FileMasterRepository;
import com.htn.blog.service.CloudinaryService;
import com.htn.blog.utils.BlogUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    FileMasterRepository fileMasterRepository;
    private final String cloudinaryFolder;
    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(@Value("${cloudinary.cloud_name}") String cloudName,
                                 @Value("${cloudinary.api_key}") String apiKey,
                                 @Value("${cloudinary.api_secret}") String apiSecret,
                                 @Value("${cloudinary.upload_folder}") String uploadFolder) {
        this.cloudinaryFolder = uploadFolder;
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret));
    }

    @Override
    @Transactional
    public FileMaster uploadCloudinary(Long userId, MultipartFile multipartFile) throws FileStorageException {
        FileMaster fileMaster = null;
        File file = null;
        try {
            validateImage(multipartFile);
            file = convert(multipartFile);
            String fileType = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String publicId = BlogUtils.generateFileName();
            String fileName = publicId + fileType;

            //upload to cloudinary
            Map params = getUploadOptions(publicId);
            Map result = cloudinary.uploader().upload(file, params);

            //save database
            fileMaster = fileMasterRepository.save(FileMaster.builder()
                    .fileUrl(result.get("secure_url").toString())
                    .publicId(result.get("public_id").toString())
                    .fileName(fileName)
                    .fileType(fileType)
                    .fileSize(multipartFile.getSize())
                    .fileOriginName(multipartFile.getOriginalFilename())
                    .build());
            log.info("Cloudinary uploaded file: " + result.get("url").toString());
        } catch (Exception ex) {
            throw new RuntimeException("File upload to Cloudinary failed!", ex);
        } finally {
            deleteTempFile(file);
        }
        return fileMaster;
    }

    @Override
    @Transactional
    public void deleteCloudinary(String fileName){
        try {
            FileMaster fileMaster = fileMasterRepository.findByFileName(fileName).orElseThrow(
                    () -> new MyFileNotFoundException("File not found with filename = " + fileName)
            );
            //1702129743169_ac008ea3c5c447e79ccf515e09090742
            String publicId = fileMaster.getPublicId();
            //database delete
            fileMasterRepository.delete(fileMaster);
            //cloudinary delete
            Map result = delete(publicId);
            if(Objects.equals(result.get("result").toString(), "not found")){
                throw new FileStorageException("Delete file cloudinary failed!");
            }
            log.info("Cloudinary deleted file: " + fileName);
        }catch (Exception exception){
            throw new FileStorageException("Delete file cloudinary failed!");
        }
    }

    private Map getUploadOptions(String publicId){
        return ObjectUtils.asMap("folder", cloudinaryFolder,
                                        "public_id", publicId,
                                        "resource_type", "image",
                                        "type", "upload");
    };

    private void validateImage(MultipartFile file) {
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed.");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds the limit of 5MB.");
        }
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fo = new FileOutputStream(file)) {
            fo.write(multipartFile.getBytes());
        }
        return file;
    }

    private void deleteTempFile(File file) {
        if (file != null && !file.delete()) {
            throw new RuntimeException("Failed to delete temporary file: " + file.getAbsolutePath());
        }
    }

    public Map delete(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "image", "type", "upload"));
    }
}
