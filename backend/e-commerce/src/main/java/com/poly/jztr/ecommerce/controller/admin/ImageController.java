package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.model.Image;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.ProductImage;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.service.ImageService;
import com.poly.jztr.ecommerce.service.ProductService;
import com.poly.jztr.ecommerce.service.UserService;
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
import java.util.List;
import java.util.Random;

@RestController("users/image")
@RequestMapping("api/v1/admin/images")
@CrossOrigin("http://localhost:3000")
public class ImageController {

    @Autowired
    ImageService service;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<Void> addOneImage(@RequestParam("image")MultipartFile file,
                                            @RequestParam("id") String id,
                                            @RequestParam("type") Integer type){
        saveFile(file,type,id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("upload-multiple")
    public ResponseEntity<Void> uploadPolicyDocument(@RequestParam("images") List<MultipartFile> multipartFile,
                                                     @RequestParam String id,
                                                     @RequestParam("type") Integer type) {
        multipartFile.stream().forEach(file -> saveFile(file,type,id));
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
        Random random = new Random();
        String fileName = "";
        if(type == Constant.IMAGE_TYPE_USER){
            fileName = "user_avatar" + id + ".jpg";
            Image img= saveImg(file,fileName,type);
            User user = userService.findById(Long.valueOf(id)).get();
            user.setImage(img);
            userService.save(user);
        }else if(type == Constant.IMAGE_TYPE_PRODUCT_AVT){
            fileName = "product_avatar" + id + ".jpg";
            Image img= saveImg(file,fileName,type);
            Product product = productService.findById(Long.valueOf(id)).get();
            product.setImage(img);
            productService.save(product);
        }else{
            fileName = "product_img_list" + id +"ran_" + random.nextInt() +Instant.now().getEpochSecond()+ ".jpg";
            Image img= saveImg(file,fileName,type);
            ProductImage productImage = new ProductImage();
            productImage.setImageId(img.getId());
            productImage.setProduct(new Product(Long.valueOf(id)));
            productImage.setCreatedAt(Instant.now());
            productImage.setUpdatedAt(Instant.now());
        }
    }

    private Image saveImg(MultipartFile file, String fileName, Integer type){
        InputStream inputStream = null;
        try {
            Path path= Paths.get("uploads");
            inputStream = file.getInputStream();
            Files.copy(inputStream,path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            Image image = new Image();
            image.setTitle(fileName);
            image.setType(type);
            return    service.save(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
