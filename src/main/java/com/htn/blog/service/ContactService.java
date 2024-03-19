package com.htn.blog.service;

import com.htn.blog.dto.ContactDTO;
import com.htn.blog.entity.Contact;

public interface ContactService {
    Contact addSendIdea(ContactDTO contactDTO);
    Contact addSendContact(ContactDTO contactDTO);
}
