package com.htn.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
@Configuration
public class I18nConfig {
    @Value("${i18n.locale.default}")
    private String defaultLocale;
    @Value("${i18n.locale.supported}")
    private String supportedLocales;
    @Bean
    public LocaleResolver localeResolver() {
        //SessionLocaleResolver localeResolver = new SessionLocaleResolver();
//        localeResolver.setDefaultLocale(Locale.forLanguageTag(defaultLocale));

        CustomLocaleResolver customLocaleResolver = new CustomLocaleResolver();
        customLocaleResolver.setDefaultLocale(Locale.forLanguageTag(defaultLocale));

        if (supportedLocales != null && !supportedLocales.isEmpty()) {
            List<String> locates = Arrays.stream(supportedLocales.split(",")).toList();
            List<Locale> localeList = locates.stream().map(Locale::forLanguageTag).toList();
            customLocaleResolver.setSupportedLocales(localeList);
        }
        return customLocaleResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
