package com.vuongpq2.datn;

import com.vuongpq2.datn.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

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
}
