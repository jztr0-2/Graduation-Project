package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.model.Image;
import com.poly.jztr.ecommerce.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

@RestController("users/image")
@RequestMapping("api/v1/admin/images")
@CrossOrigin("http://localhost:3000")
public class ImageController {

    @Autowired
    ImageService service;

    @PostMapping
    public ResponseEntity<Void> addOneImage(@RequestParam("image")MultipartFile file,
                                            @RequestParam("id") String id,
                                            @RequestParam("type") Integer type){
        saveFile(file,type,id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("{id}")
    public ResponseEntity<ByteArrayResource> getImg(@PathVariable Long id) {
        Image image = service.findById(id).get();
        String file = image.getTitle();
        return getByteArrayResourceResponseEntity(file);
    }

    private ResponseEntity<ByteArrayResource> getByteArrayResourceResponseEntity(String file) {
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

    private void saveFile(MultipartFile file, Integer type, String id){
        InputStream inputStream = null;
        String fileName = "";
        if(type == Constant.IMAGE_TYPE_USER){
            fileName = "user_avatar" + id + ".jpg";
        }else if(type == Constant.IMAGE_TYPE_PRODUCT_AVT){
            fileName = "product_avatar" + id + ".jpg";
        }else{
            fileName = "product_img_list" + id + +Instant.now().getEpochSecond()+ ".jpg";
        }

        try {
            Path path= Paths.get("uploads");
            inputStream = file.getInputStream();
            Files.copy(inputStream,path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            Image image = new Image();
            image.setTitle(fileName);
            image.setType(type);
            service.save(image);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
