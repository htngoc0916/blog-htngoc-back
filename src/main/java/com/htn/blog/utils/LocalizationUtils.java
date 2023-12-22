package com.htn.blog.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalizationUtils {
    private final MessageSource messageSource;

    public  String translate(String messageKey){
        return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }
}
