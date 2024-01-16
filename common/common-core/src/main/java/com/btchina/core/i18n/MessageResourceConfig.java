package com.btchina.core.i18n;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageResourceConfig {

    @Value(value = "${spring.messages.basename:i18n.message}")
    private String basename;

    @Value(value = "${spring.messages.encoding:UTF-8}")
    private String encoding;

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(encoding);
        String[] basenames = basename.split(",");
        for (String name : basenames) {
            messageSource.addBasenames(name);
        }
        return messageSource;
    }
}
