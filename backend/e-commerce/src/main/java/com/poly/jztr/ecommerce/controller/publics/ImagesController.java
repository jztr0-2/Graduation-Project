package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.service.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController("public.images")
@RequestMapping("api/v1/public/images")
public class ImagesController {
    ImageService service;

    @GetMapping("{file}")
    public ResponseEntity<ByteArrayResource> getImg(@PathVariable String file) {
        Path fileName = Paths.get("uploads", file);
        try {
            byte[] buffer = Files.readAllBytes(fileName);
            ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
            return ResponseEntity.status(HttpStatus.OK).contentLength(buffer.length)
                    .contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
