package com.poly.jztr.ecommerce.controller.publics;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.configuration.jwt.JwtProvider;
import com.poly.jztr.ecommerce.dto.LoginDto;
import com.poly.jztr.ecommerce.dto.UserDto;
import com.poly.jztr.ecommerce.expection.DuplicateEntryException;
import com.poly.jztr.ecommerce.model.*;
import com.poly.jztr.ecommerce.repository.BrandRepository;
import com.poly.jztr.ecommerce.service.*;
import lombok.Getter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestControllerAdvice("public/user")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/public/users")
@EnableAsync
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
        String phone = login.getEmail().trim();
        Optional<User> user = null;
        if(phone.startsWith("0") || phone.startsWith("+84") || phone.startsWith("84")){
            if(phone.startsWith("0")) phone = "84" + phone.substring(1);
            if(phone.startsWith("+84")) phone = phone.replace("+","");
            user = service.findByPhone(phone);
            if (user.isPresent()) {
                if (passwordEncoder.matches(login.getPassword(), user.get().getPassword()))
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                            "login  successfully", jwtProvider.generateTokenLogin(phone)));

                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                        new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "Password is incorrect", "")
                );
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "Phone not found", "")
            );
        }else{
            user = service.findByEmail(login.getEmail());
            if (user.isPresent()) {
                if (passwordEncoder.matches(login.getPassword(), user.get().getPassword()))
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                            "login  successfully", jwtProvider.generateTokenLogin(login.getEmail())));

                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                        new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "Password is incorrect", "")
                );
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseObject(Constant.RESPONSE_STATUS_UNPROCESSABLE_ENTITY, "Email not found", "")
            );
        }
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

    @Autowired
    BrandRepository brandRepository;
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

        for(int i = 1; i<11; i ++){
            Brand brand = new Brand();
            brand.setName("BRAND NAME" + i);
            brandRepository.save(brand);
        }

        for(int i = 1; i < 1000; i ++){
            Product product = new Product();
            product.setDescription("PRODUCT" + i + "description");
            product.setStatus(true);
            product.setName("PRODUCT NAME" + i);
            product.setQuantity(1500);
            product.setUnitPrice(random.nextLong(100,1000));
            product.setCategory(new Category(random.nextLong(7)+ 1));
            product.setBrand(new Brand(random.nextLong(7)+ 1));
            productService.save(product);
        }


        //init comment

        for(int i = 1; i < 100; i ++){
            Comment cmt = new Comment();
            cmt.setUser(new User(Long.valueOf(i/2 + 1)));
            cmt.setContent("Comment " + i);
            cmt.setProduct(new Product(random.nextLong(1,100)));
            commentService.save(cmt);
        }
        // init promotion

        for (int i = 1; i < 100; i ++){
            Promotion promotion = new Promotion();
            promotion.setCreatedAt(Instant.now());
            promotion.setStatus(true);
            promotion.setCode( RandomStringUtils.random(6, 'a','b','c','d','e','f','g','h','j','k','q','w','1','2','3','4','5','6','7'));
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
            order.setStatus(random.nextInt(2));
            order.setDescription("Fake order" + i);


            String month = ((i%12) + 1) +"";

            month = month.length() == 1 ? "0" + month : month;
            String stringDate = "09:15:30 PM, "+month+"/09/2023";
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
                orderItem.setProduct(new Product(random.nextLong(190) + 1));
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
//        if(sendSMSService.send()){
//            return "DONE";
//        }
//        return "FAIL";
        for (int i = 1; i < 100; i ++){
            Promotion promotion = new Promotion();
            promotion.setCreatedAt(Instant.now());
            promotion.setStatus(i%2 + 1 == 1);
            promotion.setCode( RandomStringUtils.random(6, 'a','b','c','d','e','f','g','h','j','k','q','w','1','2','3','4','5','6','7'));
            promotionService.save(promotion);
        }
        return "";
    }

    @PostMapping("init-file-data")
    public String initFileData(@RequestParam MultipartFile file) throws IOException {
//        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//        XSSFSheet categorySheet = workbook.getSheetAt(0);
//        for(int i=1;i<categorySheet.getPhysicalNumberOfRows() ;i++) {
//            XSSFRow row = categorySheet.getRow(i);
//            Category category = new Category();
//            category.setName(row.getCell(0).toString().trim());
//            categoryService.save(category);
//        }
//
//        XSSFSheet userSheet = workbook.getSheetAt(1);
//
//        for(int i=1;i<99;i++) {
//            XSSFRow row = userSheet.getRow(i);
//            String fullname = row.getCell(0)+"";
//                User user = new User();
//                user.setFirstName(fullname.substring(0,fullname.lastIndexOf(" ")).trim());
//                user.setLastName(fullname.substring(fullname.lastIndexOf(" ")).trim());
//                user.setEmail(row.getCell(1).toString().trim());
//                user.setPhone(row.getCell(2).toString().trim());
//                user.setPassword(passwordEncoder.encode("123456"));
//                user.setStatus(Constant.USER_STATUS_ACTIVATED);
//                service.save(user);
//        }
//
//        XSSFSheet addressSheet = workbook.getSheetAt(2);
//
//        for(int i=1;i<addressSheet.getPhysicalNumberOfRows() ;i++) {
//            XSSFRow row = addressSheet.getRow(i);
//            Address address = new Address();
//            address.setProvince(row.getCell(0).toString().trim());
//            address.setDistrict(row.getCell(1).toString().trim());
//            address.setCommune(row.getCell(2).toString().trim());
//            address.setWard(row.getCell(3).toString().trim());
//            address.setAppartmentNo(row.getCell(4).toString().trim());
//            address.setPhone(row.getCell(5).toString().trim());
//            addressService.save(address);
//        }
//
//        XSSFSheet productSheet = workbook.getSheetAt(3);
//
//        String title  = "";
//        Random random = new Random();
//        Product product = new Product();
//        for(int i=1;i<productSheet.getPhysicalNumberOfRows() ;i++) {
//            XSSFRow row = productSheet.getRow(i);
//            if(i >= 212) break;
//            String productName = row.getCell(0)+"".trim();
//            if(productName != "" && productName != "null" && !productName.isEmpty() && !productName.isBlank() && productName != null){
//                product = new Product();
//                product.setName(productName);
//                Category category = new Category(random.nextLong(1,9));//categoryService.findByName(row.getCell(1).toString().trim()).get();
//                product.setCategory(category);
//                product.setDescription(row.getCell(2).toString().trim());
//                title = row.getCell(3).toString().trim();
//                product.setStatus(0);
//                product = productService.save(product);
//                String imgLink = row.getCell(8).toString().trim();
//                System.out.println(imgLink);
//                System.out.println(row.getCell(7).toString().trim());
//                Image img = saveImg(imgLink,"product_avt" + product.getId() +  random.nextInt() + ".jpg",Constant.IMAGE_TYPE_PRODUCT_AVT,0L);
//                product.setImage(img);
//                productService.save(product);
//            }else{
//                ProductVariant variant = new ProductVariant();
//                variant.setProduct(product);
//                variant.setQuantity(10000);
//                System.out.println(row.getCell(4));
//                variant.setUnitPrice(row.getCell(4).getNumericCellValue());
//                variant.setDescription("{\"title\": \""+title+"\", \""+title+"\": "+row.getCell(7)+"".trim()+"\"}");
//                String imgLink = row.getCell(8).toString().trim();
//                saveImg(imgLink,"product_lst" + product.getId() + random.nextInt() + ".jpg",Constant.IMAGE_TYPE_PRODUCT_MULTIPLE,product.getId());
//                variant.setDisplayName(product.getName());
//                productVariantService.save(variant);
//            }
//        }

        // init order

//        for(int i = 0; i < 1000; i ++){
//            Address address = new Address(random.nextLong(1,19));
//            User user= service.findById(random.nextLong(90) + 1).get();
//            Order order = new Order();
//            order.setUser(user);
//            order.setStatus(Constant.ORDER_STATUS_SUCCESS);
//            order.setDescription("Fake order" + i);
//
//
//            String month = ((i%12) + 1) +"";
//
//            month = month.length() == 1 ? "0" + month : month;
//            String stringDate = "09:15:30 PM, "+month+"/09/2022";
//            String pattern = "hh:mm:ss a, M/d/uuuu";
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
//            LocalDateTime localDateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
//            ZoneId zoneId = ZoneId.of("America/Chicago");
//            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
//            Instant instant = zonedDateTime.toInstant();
//
//
//            order.setCreatedAt(instant);
//            order.setCreatedAt(instant);
//            List<OrderItem> orderItemList = new ArrayList<>();
//            order.setAddress(address);
//            Double total = 0D;
//            for(int j = 0; j < 5; j ++){
//                OrderItem orderItem = new OrderItem();
//                orderItem.setOrder(order);
//                orderItem.setQuantity(random.nextInt(10));
//                orderItem.setUnitPrice(random.nextDouble(1500));
//                orderItem.setProductVariant(new ProductVariant(random.nextLong(1,137)));
//                total += orderItem.getUnitPrice() * orderItem.getQuantity();
//                orderItemList.add(orderItem);
//            }
//            order.setTotal(total);
//            order.setOrderItems(orderItemList);
//            orderService.save(order);
//        }
        return "OK";
    }

    @Autowired ImageService imageService;

    private Image saveImg(String surl,String fileName, Integer type, Long id) throws IOException {
        URL url = new URL(surl);
        InputStream in = new BufferedInputStream(url.openStream());
        Path path= Paths.get("uploads");
        Files.copy(in,path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        Image image = new Image();
        image.setTitle(fileName);
        image.setType(type);
        return imageService.save(image);
    }

    @GetMapping("test_as")
    public ResponseEntity<ResponseObject> testAsync() throws InterruptedException {
        System.out.println("Start Reqiest");

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "OK", "OK"));
    }

}
