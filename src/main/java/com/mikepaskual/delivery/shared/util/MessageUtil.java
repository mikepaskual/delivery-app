package com.mikepaskual.delivery.shared.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil {

    @Autowired
    private final MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String code, Locale locale) {
        return this.get(code, null, locale);
    }

    public String get(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, locale);
    }
}
