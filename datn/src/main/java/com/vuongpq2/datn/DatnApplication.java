package com.vuongpq2.datn;

import com.vuongpq2.datn.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.Resource;
import java.util.Locale;

@SpringBootApplication
public class DatnApplication implements CommandLineRunner {
    @Resource
    StorageService storageService;
    public static void main(String[] args) {
        SpringApplication.run(DatnApplication.class, args);
    }
    @Override
    public void run(String... arg) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        //localeResolver.setDefaultLocale(Locale.US);
        localeResolver.setDefaultLocale(Locale.forLanguageTag("vi-VI"));
        return localeResolver;
    }
}
