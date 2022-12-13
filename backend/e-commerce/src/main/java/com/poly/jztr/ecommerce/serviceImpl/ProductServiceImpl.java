package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.dto.ProductDto;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.ProductVariant;
import com.poly.jztr.ecommerce.repository.ProductRepository;
import com.poly.jztr.ecommerce.serializer.ProductStatic;
import com.poly.jztr.ecommerce.service.ProductService;
import com.poly.jztr.ecommerce.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository repository;

    @Autowired
    ProductVariantService productVariantService;

    @Override
    public <S extends Product> S save(S entity) {
        Product p =  repository.save(entity);
        List<ProductVariant> productVariants = p.getProductVariants();
        if(productVariants != null){
            productVariants.stream().forEach(productVariant -> {
                productVariant.setProduct(p);
                productVariantService.save(productVariant);
            });
        }
        return (S) p;
    }

    @Override
    public Page<Product> findByNameLike(String name, Pageable pageable) {
        return repository.findByNameContains(name, pageable);
    }

    @Override
    public Optional<Product> findByName(String name){
        return repository.findByName(name);

    }

    @Override
    public Product toProduct(ProductDto dto){
        Product product= new Product();
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setStatus(dto.getStatus());
        List<ProductVariant> productVariants = productVariantService.
                toProductVariantFromDto(dto.getProductVariantList());
        product.setProductVariants(productVariants);
        product.setCategory(new Category(dto.getCategoryId()));
        return product;
    }
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Product> findTopSaleByCategory(Long categoryId, Pageable page) {
        return repository.findTopSaleByCategory(categoryId, page);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ProductStatic> findStaticsProductTop() {
        String time = LocalDate.now().withDayOfMonth(1) + "";
        List<Object[]> lst = repository.findStaticsProduct(time);
        return lst.stream().map(pro ->
            new ProductStatic(pro[0]+"", Long.valueOf(pro[1]+""))
        ).collect(Collectors.toList());
     }

     @Override
     public List<ProductStatic> findStaticsProductsBot(){
         String time = LocalDate.now().withDayOfMonth(1) + "";
         List<Object[]> lst = repository.findStaticsProduct(time,"bot");
         return lst.stream().map(pro ->
                 new ProductStatic(pro[0]+"", Long.valueOf(pro[1]+""))
         ).collect(Collectors.toList());
     }

    public Page<Product> getProductsByCategoryId(Long categoryId, Pageable page) {
        return repository.getProductsByCategoryId(categoryId, page);
    }

    @Override
    public Long getProductSoldThisMonth(){
        String time = LocalDate.now().withDayOfMonth(1) + "";
        Optional<Long> optional = repository.totalProductSold(time);
        if (optional.isPresent()) return optional.get();
        return 0L;
    }


}
