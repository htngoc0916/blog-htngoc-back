package com.htn.blog.service.impl;

import com.htn.blog.dto.ContactDTO;
import com.htn.blog.entity.Contact;
import com.htn.blog.repository.ContactRepository;
import com.htn.blog.service.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Contact addSendIdea(ContactDTO contactDTO) {
        Contact contact = modelMapper.map(contactDTO, Contact.class);
        contact.setContactCode("IDEA");
        return contactRepository.save(contact);
    }

    @Override
    public Contact addSendContact(ContactDTO contactDTO) {
        Contact contact = modelMapper.map(contactDTO, Contact.class);
        contact.setContactCode("CONTACT");
        return contactRepository.save(contact);
    }
}
