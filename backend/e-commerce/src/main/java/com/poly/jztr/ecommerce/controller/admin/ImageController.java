package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
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

    @PostMapping
    public ResponseEntity<Void> addOneImage(@RequestParam("image")MultipartFile file,
                                            @RequestParam("id") String id,
                                            @RequestParam("type") Integer type){
        saveFile(file,type,id);
        return ResponseEntity.ok().build();
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
