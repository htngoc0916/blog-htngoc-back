package com.htn.blog.controller;

import com.htn.blog.common.MessageKeys;
import com.htn.blog.dto.ContactDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.Contact;
import com.htn.blog.service.ContactService;
import com.htn.blog.utils.LocalizationUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    @Autowired
    private LocalizationUtils localizationUtils;
    @Autowired
    private ContactService contactService;

    @Operation(summary = "Add new a contact rest api")
    @PostMapping("/send-idea")
    public ResponseEntity<?> addSendIdea(@Valid @RequestBody ContactDTO contactDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.CONTACT_SEND_SUCCESSFULLY))
                        .data(contactService.addSendIdea(contactDTO))
                        .build()
        );
    }

    @PostMapping("/send-contact")
    public ResponseEntity<?> addSendContact(@Valid @RequestBody ContactDTO contactDTO){
        Contact category = contactService.addSendContact(contactDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.CONTACT_SEND_SUCCESSFULLY))
                        .data(category)
                        .build()
        );
    }
}
