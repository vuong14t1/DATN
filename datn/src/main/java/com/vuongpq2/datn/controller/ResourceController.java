package com.vuongpq2.datn.controller;

import com.vuongpq2.datn.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class ResourceController {

    @Autowired
    StorageService storageService;

    @RequestMapping(value = "/image/{id:.+}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getImage(
            @PathVariable("id")
                    String id) throws IOException {
        Resource resource = storageService.loadFile("img/"+ id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
