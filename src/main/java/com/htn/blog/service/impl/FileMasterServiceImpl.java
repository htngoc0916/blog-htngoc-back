package com.htn.blog.service.impl;

import com.htn.blog.dto.UploadResponseDTO;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.exception.BlogApiException;
import com.htn.blog.exception.FileStorageException;
import com.htn.blog.repository.FileMasterRepository;
import com.htn.blog.service.FileMasterService;
import com.htn.blog.utils.FileAbstract;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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

    @Override
    public FileMaster uploadFile(MultipartFile file){
        try {
            log.info("upload file start");
            List<FileMaster> fileMasterList = handleUploadFiles(List.of(file));
            return fileMasterList.size() > 0 ? fileMasterList.get(0) : null;
        }catch (Exception exception){
            throw new FileStorageException(exception.getMessage());
        }
    }

    @Override
    public List<FileMaster> uploadMultipleFiles(MultipartFile[] files){
        try {
            log.info("upload multiple files start");
            return handleUploadFiles(List.of(files));
        }catch (Exception exception){
            throw new FileStorageException(exception.getMessage());
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            FileMaster fileMaster = fileMasterRepository.findByFileName(fileName).orElseThrow(
                    () -> new FileStorageException("File not found with id" + fileName)
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

    private List<FileMaster> handleUploadFiles(List<MultipartFile> files) throws Exception {
        List<FileMaster> fileMasterList = new ArrayList<>();
        //validation
        validationFile(files);
        createDirectory();
        for (MultipartFile file : files) {
            fileMasterList.add(saveFile(file));
        }

        return fileMasterList.stream()
                .map(fileMaster -> fileMasterRepository.save(fileMaster))
                .toList();
    }

    private FileMaster saveFile(MultipartFile file) throws IOException {
        UploadResponseDTO savePath = uploadFileStore(file);
        return modelMapper.map(savePath,FileMaster.class);
    }
}
