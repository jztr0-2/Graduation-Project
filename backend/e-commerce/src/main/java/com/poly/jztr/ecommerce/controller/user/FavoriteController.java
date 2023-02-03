package com.poly.jztr.ecommerce.controller.user;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import com.poly.jztr.ecommerce.dto.FavoriteDto;
import com.poly.jztr.ecommerce.model.Favorite;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.FavoriteService;
import com.poly.jztr.ecommerce.service.ProductService;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user/favorites")
public class FavoriteController {
    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    ProductService productService;
    @GetMapping
    public ResponseEntity<ResponseObject> index(@RequestHeader(value = "Authorization", defaultValue = "") String jwt,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer perPage) {
        User user =  userService.findByEmailOrPhone(jwtProvider.getUsernameFromToken(jwt));
        Page<Favorite> favoritePage = favoriteService.findByUser(user, CustomPageable.getPage(page,perPage));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "OK",new PageableSerializer(favoritePage))
        );
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createOrRemove(@RequestHeader(value = "Authorization", defaultValue = "") String jwt,
                                                         @RequestBody FavoriteDto favoriteDto){
        User user =  userService.findByEmail(jwtProvider.getUsernameFromToken(jwt)).get();
        Product product = productService.findById(favoriteDto.getProductId()).get();
        Optional<Favorite> favorite = favoriteService.findByProductAndUser(product,user);
        if(favorite.isPresent()){
            favoriteService.deleteById(favorite.get().getId());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Delete success",favorite.get()));
        }else {
            Favorite favorite1 = new Favorite();
            favorite1.setProduct(product);
            favorite1.setUser(user);
            favoriteService.save(favorite1);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "Save success",favorite1));
        }
    }
}
