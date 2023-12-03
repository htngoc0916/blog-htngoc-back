package com.htn.blog.service.impl;

import com.htn.blog.entity.FileMaster;
import com.htn.blog.exception.FileStorageException;
import com.htn.blog.service.FileMasterService;
import com.htn.blog.utils.FileAbstract;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileMasterServiceImpl extends FileAbstract implements FileMasterService{
    @Override
    public FileMaster uploadFile(MultipartFile file) {
        //validation
        if(file.isEmpty()){
            throw new FileStorageException("Please select a file!");
        }

        //upload
        try {
             List<FileMaster> fileMasterList = saveUploadFiles(List.of(file));
             return fileMasterList.size() > 0 ? fileMasterList.get(0) : null;
        }catch (Exception ex){
            throw new FileStorageException(ex.getMessage());
        }
    }

    private List<FileMaster> saveUploadFiles(List<MultipartFile> files) throws Exception {
        List<FileMaster> fileMasterList = new ArrayList<>();
        createDirectory();
        for (MultipartFile file : files) {
            if (validationFile(file)) {
                fileMasterList.add(save(file));
            }
        }
        return fileMasterList;
    }

    private FileMaster save(MultipartFile file) throws IOException {
        String savePath = uploadFileStore(file);
        FileMaster fileMaster = FileMaster.builder()
                .fileUrl(savePath)
                .fileType(file.getContentType())
                .fileOriginName(file.getOriginalFilename())
                .fileName("aaa")
                .fileSize(file.getSize())
                .usedYn("Y")
                .build();
        return null;
    }

    private void saveMetaData(MultipartFile file, String originalFilename) throws IOException {
//        FileUploadMetaData metaData = new FileUploadMetaData();
//        metaData.setName(file.getOriginalFilename());
//        metaData.setContentType(file.getContentType());
//        metaData.setContentSize(file.getSize());
//        fileUploadMetaData.save(metaData);
    }


}
