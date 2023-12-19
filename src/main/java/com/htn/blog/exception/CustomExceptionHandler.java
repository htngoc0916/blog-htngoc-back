package com.htn.blog.exception;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.module.ResolutionException;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResolutionException.class)
    public ResponseEntity<?> handleResourceNotFoundException(NotFoundException exception, WebRequest webRequest){
        log.error(exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(BlogConstants.FAILED)
                .message(exception.getMessage())
                .data(webRequest.getDescription(false)).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<?> handleBlogApiException(BlogApiException exception, WebRequest webRequest){
        log.error(exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.builder()
                                            .status(BlogConstants.FAILED)
                                            .message(exception.getMessage())
                                            .data(webRequest.getDescription(false)).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception, WebRequest webRequest){
        log.error(exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.builder()
                                            .status(BlogConstants.FAILED)
                                            .message(exception.getMessage())
                                            .data(webRequest.getDescription(false)).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    //global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest webRequest){
        log.error(exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.builder()
                                            .status(BlogConstants.FAILED)
                                            .message(exception.getMessage())
                                            .data(webRequest.getDescription(false)).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //file storage
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<?> handleFileStorageException(Exception exception, WebRequest webRequest){
        log.error(exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(BlogConstants.FAILED)
                .message(exception.getMessage())
                .data(webRequest.getDescription(false)).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //file not found
    @ExceptionHandler(MyFileNotFoundException.class)
    public ResponseEntity<?> handleMyFileNotFoundException(Exception exception, WebRequest webRequest){
        log.error(exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(BlogConstants.FAILED)
                .message(exception.getMessage())
                .data(webRequest.getDescription(false)).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<?> handleTokenRefreshException(Exception exception, WebRequest webRequest){
        log.error(exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(BlogConstants.FORBIDDEN)
                .message(exception.getMessage())
                .data(webRequest.getDescription(false)).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.FORBIDDEN);
    }
}
