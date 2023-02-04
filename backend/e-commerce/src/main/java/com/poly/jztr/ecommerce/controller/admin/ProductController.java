package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.ProductDto;
import com.poly.jztr.ecommerce.expection.DuplicateEntryException;
import com.poly.jztr.ecommerce.model.Brand;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.CategoryService;
import com.poly.jztr.ecommerce.service.ImageService;
import com.poly.jztr.ecommerce.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestControllerAdvice("admin-products-controller")
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/admin/products")
public class ProductController {

    @Autowired
    ProductService service;

    @Autowired
    CategoryService categoryService;


//    @PostMapping("import")
//    @Transactional(rollbackFor=Exception.class)
//    public ResponseEntity<ResponseObject> importFile(@RequestParam MultipartFile file) throws Exception {
//        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//
//        XSSFSheet productSheet = workbook.getSheetAt(0);
//
//        String title = "";
//        Random random = new Random();
//        Product product = new Product();
//        System.out.println(productSheet.getPhysicalNumberOfRows());
//        for (int i = 1; i < productSheet.getPhysicalNumberOfRows(); i++) {
//            XSSFRow row = productSheet.getRow(i);
//            String productName = row.getCell(0) + "".trim();
//            if(productName.equalsIgnoreCase("end"))break;
//            if (productName != "" && productName != "null" && !productName.isEmpty() && !productName.isBlank() && productName != null) {
//                product = new Product();
//                product.setName(productName);
//                Category category = new Category(random.nextLong(1, 9));//categoryService.findByName(row.getCell(1).toString().trim()).get();
//                product.setCategory(category);
//                System.out.println("------" +i + "--------");
//                product.setDescription(row.getCell(2)+"".trim());
//                title = row.getCell(3).toString().trim();
//                product.setStatus(0);
//                product = service.save(product);
//                String imgLink = row.getCell(8).toString().trim();
//                System.out.println(imgLink);
//                System.out.println(row.getCell(7).toString().trim());
//                Image img = saveImg(imgLink, "product_avt" + product.getId() + random.nextInt() + ".jpg", Constant.IMAGE_TYPE_PRODUCT_AVT, 0L, null);
//                product.setImage(img);
//                service.save(product);
//            } else {
//                String unitPrice = (row.getCell(5)+"").trim();
//                if(unitPrice.equalsIgnoreCase("null")){
//                    break;
//                }
//                ProductVariant variant = new ProductVariant();
//                variant.setProduct(product);
//                variant.setUnitPrice(row.getCell(4).getNumericCellValue());
//                variant.setQuantity((int) row.getCell(5).getNumericCellValue());
//                System.out.println(row.getCell(4));
//
//                variant.setDescription("{\"title\": \"" + title + "\", \"" + title + "\": " + row.getCell(7) + "".trim() + "\"}");
//                String imgLink = row.getCell(8).toString().trim();
//                variant.setDisplayName(product.getName());
//                saveImg(imgLink, "product_lst" + product.getId() + random.nextInt() + ".jpg", Constant.IMAGE_TYPE_PRODUCT_MULTIPLE, product.getId(),variant);
//            }
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
//                Constant.RESPONSE_STATUS_SUCCESS, "import ok", ""
//        ));
//    }

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody @Validated ProductDto dto) throws DuplicateEntryException {
        Product p = new Product();
        p.setBrand(new Brand(dto.getBrandId()));
        p.setCategory(new Category(dto.getCategoryId()));
        BeanUtils.copyProperties(dto,p);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Create product successfully", service.save(p)));
    }

    @GetMapping
    public ResponseEntity<ResponseObject> index(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer perPage,
                                                @RequestParam(defaultValue = "") String name,
                                                @RequestParam() Optional<Boolean> status) {
        Pageable pageable = CustomPageable.getPage(page, perPage, "updatedAt", Constant.SORT_DESC);
        PageableSerializer data = null;
        if (status.isEmpty()) data = new PageableSerializer(service.findByNameLike(name, pageable));
        else data = new PageableSerializer(service.findByNameContainsAndStatus(name, status.get(), pageable));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Get products successfully", data));
    }

    @GetMapping("{/id}")
    public ResponseEntity<ResponseObject> getOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        Constant.RESPONSE_STATUS_SUCCESS, "Get product success",
                        service.findById(id).get())
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody @Validated ProductDto dto) throws DuplicateEntryException {
        Product p = new Product();
        p.setBrand(new Brand(dto.getBrandId()));
        p.setCategory(new Category(dto.getCategoryId()));
        BeanUtils.copyProperties(dto,p);
        p.setId(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Update successfully", service.save(p)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        Product p = service.findById(id).get();
        p.setDeletedAt(Instant.now());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Delete successfully", service.save(p)));
    }

    @Autowired
    ImageService imageService;


//    private Image saveImg(String surl, String fileName, Integer type, Long id, ProductVariant variant) throws Exception {
//        File file = new File(surl);
//        File des = new File("uploads/" + fileName);
//        Files.copy(file.toPath(),des.toPath());
//        Image image = new Image();
//        image.setTitle(fileName);
//        image.setType(type);
//
//        if (id != 0L) {
//            image.setProductId(id);
//        }
//        image = imageService.save(image);
//        if(variant != null){
//            variant.setImage(image);
//            productVariantService.save(variant);
//        }
//        return image;
//    }
}
