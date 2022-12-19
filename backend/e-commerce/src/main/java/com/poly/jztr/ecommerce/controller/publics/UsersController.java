package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import com.poly.jztr.ecommerce.dto.LoginDto;
import com.poly.jztr.ecommerce.dto.UserDto;
import com.poly.jztr.ecommerce.expection.DuplicateEntryException;
import com.poly.jztr.ecommerce.model.*;
import com.poly.jztr.ecommerce.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.RandomStringUtils;


import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestControllerAdvice("public/user")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/public/users")
public class UsersController {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    AdminService adminService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    AddressService addressService;

    @Autowired
    CommentService commentService;

    @Autowired PromotionService promotionService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDto login) {
        Optional<User> user = service.findByEmail(login.getEmail());
        if (user.isPresent()) {
            if (passwordEncoder.matches(login.getPassword(), user.get().getPassword()))
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "login  successfully", jwtProvider.generateTokenLogin(login.getEmail())));

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "password is incorrect", "")
            );
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "email not found", "")
        );
    }

    @PostMapping("admin-login")
    public ResponseEntity<ResponseObject> adminLogin(@RequestBody LoginDto login){
        Optional<Admin> admin = adminService.findByLoginName(login.getEmail());
        if(admin.isPresent()){
            if (passwordEncoder.matches(login.getPassword(), admin.get().getPassword()))
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "login  successfully", jwtProvider.generateTokenLoginAdmin(login.getEmail())));

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "password is incorrect", ""));
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "username not found", ""));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody() @Validated UserDto dto) throws DuplicateEntryException {
        User user = new User();
        if(service.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateEntryException("Email", "Email is already exits");
        }
        BeanUtils.copyProperties(dto, user);
        user.setStatus(Constant.USER_STATUS_ACTIVATED);
        if (!dto.getPassword().equals(dto.getPasswordConfirmation()))
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "Password not same", ""));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS, "", service.save(user)));
    }

    @GetMapping("/init-admin")
    public ResponseEntity<ResponseObject> initAdmin(){
        Optional<Admin> admin = adminService.findByLoginName("admin");
        if(admin.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                    "User name: admin, password: 123456", admin.get()));
        }
        Admin admin1 = new Admin();
        admin1.setLoginName("admin");
        admin1.setPassword(passwordEncoder.encode("123456"));
        admin1.setSuperAdmin(true);
        admin1.setStatus(1);
        admin1.setUpdatedAt(Instant.now());
        admin1.setCreatedAt(Instant.now());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                "User name: admin, password: 123456", adminService.save(admin1)));
    }

    @GetMapping("/init-data")
    public String initData(){
        Random random = new Random();
        if(service.findById(1L).isPresent()){
            return "INIT SUCCESSFULLY";
        }
        // Init user
        for(int i = 0; i < 100; i ++){
            User user = new User();
            user.setStatus(1);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setEmail("user_demo"+i + "@gmail.com");
            user.setFirstName("Demo" +i);
            user.setLastName("User");

            service.save(user);
        }


        // Init category
        for(int i = 1; i<11; i ++){
            Category category = new Category();
            category.setName("CATEGORY" + i);
            categoryService.save(category);
        }

        // Init product, each product have 2 product variant

        for(int i = 1; i < 100; i ++){
            Product product = new Product();
            product.setDescription("PRODUCT" + i + "description");
            product.setStatus(1);
            product.setName("PRODUCT NAME" + i);
            product.setCategory(new Category(random.nextLong(7)+ 1));
            System.out.println(product.getCategory().getId());
            List<ProductVariant> productVariantList = new ArrayList<>();
            String[] colors = {"xxx","YELLOW", "BLUE", "PINK", "RED"};
            for (int j = 1; j < 3; j++){
                ProductVariant productVariant = new ProductVariant();
                productVariant.setProduct(product);
                productVariant.setDescription("{\"title\": \"color\", \"color\": \""+ colors[j] +i+ "\"}");
                productVariant.setQuantity(1000);
                productVariant.setUnitPrice(Double.valueOf(100+ i + j));
                productVariant.setDisplayName("PRODUCT NAME" + i);
                productVariantList.add(productVariant);
//                productVariantService.save(productVariant);
            }
            product.setProductVariants(productVariantList);
            productService.save(product);
        }


        //init comment

        for(int i = 1; i < 100; i ++){
            Comment cmt = new Comment();
            cmt.setUser(new User(Long.valueOf(i/2 + 1)));
            cmt.setContent("Comment " + i);
            cmt.setProduct(new Product(Long.valueOf(i)));
            commentService.save(cmt);
        }


        // init promotion

        for (int i = 1; i < 100; i ++){
            Promotion promotion = new Promotion();
            promotion.setCreatedAt(Instant.now());
//            promotion.setType(i%2 + 1);
            promotion.setCode( RandomStringUtils.random(6));

            promotionService.save(promotion);
        }

        // init order

        Address address = new Address();
        address.setCommune("Hoa Minh");
        address.setDistrict("Lien Chieu");
        address.setProvince("Da Nang");
        address.setAppartmentNo("137");
        address.setWard("Nguyen Thi Thap");
        address.setPhone("0973588761");
        address = addressService.save(address);

        for(int i = 0; i < 1000; i ++){
            User user= service.findById(random.nextLong(90) + 1).get();
            Order order = new Order();
            order.setUser(user);
            order.setStatus(Constant.ORDER_STATUS_SUCCESS);
            order.setDescription("Fake order" + i);


            String month = ((i%12) + 1) +"";

            month = month.length() == 1 ? "0" + month : month;
            String stringDate = "09:15:30 PM, "+month+"/09/2022";
            String pattern = "hh:mm:ss a, M/d/uuuu";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
            LocalDateTime localDateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
            ZoneId zoneId = ZoneId.of("America/Chicago");
            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
            Instant instant = zonedDateTime.toInstant();


            order.setCreatedAt(instant);
            order.setCreatedAt(instant);
            List<OrderItem> orderItemList = new ArrayList<>();
            order.setAddress(address);
            for(int j = 0; j < 5; j ++){
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setQuantity(random.nextInt(10));
                orderItem.setUnitPrice(random.nextDouble(1500));
                orderItem.setProductVariant(new ProductVariant(random.nextLong(190) + 1));
                orderItemList.add(orderItem);
            }
            order.setOrderItems(orderItemList);
            orderService.save(order);
        }
        return "INIT SUCCESSFULLY";
    }

    @Autowired SendSMSService sendSMSService;
    @GetMapping("test-sendSMS")
    public String testSendSMS(){
        if(sendSMSService.send()){
            return "DONE";
        }
        return "FAIL";
    }
}
