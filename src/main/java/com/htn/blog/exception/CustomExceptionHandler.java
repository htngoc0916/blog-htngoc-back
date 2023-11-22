package com.htn.blog.exception;

import com.htn.blog.dto.ErrorDetailsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.module.ResolutionException;
import java.util.Date;

public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResolutionException.class)
    public ResponseEntity<ErrorDetailsDTO> handleResourceNotFoundException(NotFoundException exception, WebRequest webRequest){
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsDTO.builder()
                                                            .timestamp(new Date())
                                                            .message(exception.getMessage())
                                                            .details(webRequest.getDescription(false))
                                                            .build();

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetailsDTO> handleBlogApiException(BlogApiException exception, WebRequest webRequest){
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsDTO.builder()
                                                        .timestamp(new Date())
                                                        .message(exception.getMessage())
                                                        .details(webRequest.getDescription(false))
                                                        .build();
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetailsDTO> handleAccessDeniedException(AccessDeniedException exception,
                                                                    WebRequest webRequest){
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsDTO.builder()
                                                        .timestamp(new Date())
                                                        .message(exception.getMessage())
                                                        .details(webRequest.getDescription(false))
                                                        .build();
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.UNAUTHORIZED);
    }

    //global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDTO> handleGlobalException(Exception exception,
                                                              WebRequest webRequest){
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsDTO.builder()
                                                        .timestamp(new Date())
                                                        .message(exception.getMessage())
                                                        .details(webRequest.getDescription(false))
                                                        .build();
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
