package com.htn.blog.service.impl;

import com.htn.blog.dto.UploadResponseDTO;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.exception.FileStorageException;
import com.htn.blog.exception.MyFileNotFoundException;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.FileMasterRepository;
import com.htn.blog.repository.UserRepository;
import com.htn.blog.service.FileMasterService;
import com.htn.blog.utils.FileAbstract;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileMasterServiceImpl extends FileAbstract implements FileMasterService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileMasterRepository fileMasterRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public FileMaster uploadFile(Long userId, MultipartFile file){
        try {
            log.info("upload file start");
            List<FileMaster> fileMasterList = handleUploadFiles(userId, List.of(file));
            return fileMasterList.size() > 0 ? fileMasterList.get(0) : null;
        }catch (Exception exception){
            throw new FileStorageException(exception.getMessage());
        }
    }

    @Override
    public List<FileMaster> uploadMultipleFiles(Long userId, MultipartFile[] files){
        try {
            log.info("upload multiple files start");
            return handleUploadFiles(userId, List.of(files));
        }catch (Exception exception){
            throw new FileStorageException(exception.getMessage());
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            FileMaster fileMaster = fileMasterRepository.findByFileName(fileName).orElseThrow(
                    () -> new MyFileNotFoundException("File not found with id" + fileName)
            );

            return loadFileStore(fileMaster.getFileUrl());
        }catch (Exception exception){
            throw new FileStorageException(exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteFile(String fileName) {
        try {
            FileMaster fileMaster = fileMasterRepository.findByFileName(fileName).orElseThrow(
                    () -> new FileStorageException("File not found with id" + fileName)
            );
            fileMasterRepository.delete(fileMaster);
            if(!deleteFileStore(fileName)){
                throw new FileStorageException("File can not delete");
            }
        }catch (Exception exception){
            throw new FileStorageException(exception.getMessage());
        }
    }

    private List<FileMaster> handleUploadFiles(Long userId, List<MultipartFile> files) throws Exception {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user not exists with id = " + userId)
        );

        List<FileMaster> fileMasterList = new ArrayList<>();
        //validation
        validationFile(files);
        createDirectory();
        for (MultipartFile file : files) {
            fileMasterList.add(saveFile(file));
        }
        //data save to DB
        return fileMasterList.stream()
                .map(fileMaster -> {
                    fileMaster.setRegId(userId);
                    return fileMasterRepository.save(fileMaster);
                })
                .toList();
    }

    private FileMaster saveFile(MultipartFile file) throws IOException {
        UploadResponseDTO savePath = uploadFileStore(file);
        return modelMapper.map(savePath,FileMaster.class);
    }
}
