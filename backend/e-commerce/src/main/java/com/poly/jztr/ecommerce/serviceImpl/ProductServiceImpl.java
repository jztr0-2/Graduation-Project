package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Brand;
import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.repository.ProductRepository;
import com.poly.jztr.ecommerce.serializer.ProductStatic;
import com.poly.jztr.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository repository;


    @Override
    public <S extends Product> S save(S entity) {
        Product p =  repository.save(entity);
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
    public Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        return repository.getProductsByCategoryId(categoryId, pageable);
    }

    @Override
    public Long getProductSoldThisMonth(){
        String time = LocalDate.now().withDayOfMonth(1) + "";
        Optional<Long> optional = repository.totalProductSold(time);
        if (optional.isPresent()) return optional.get();
        return 0L;
    }
    @Override
    public Page<Product> findByNameContainsAndStatus(String name, Boolean status, Pageable pageable) {
        return repository.findByNameContainsAndStatus(name, status, pageable);
    }

    @Override
    public List<Product> findTopSale() {
        return repository.findTopSale();
    }

    @Override
    public List<Product> findRelatedProduct(Long productId) {
        Random random = new Random();
        int limit = random.nextInt(5,10);
        Product product = repository.findById(productId).get();
        return repository.getRelatedProduct(product.getCategory().getId(), limit);
    }

    @Override
    public List<Product> findByBrandId(Brand id) {
        return repository.findByBrand(id);
    }


    public Page<Product> findByCategoryAndQuantityIsGreaterThan(Category category, Integer quantity, Pageable pageable) {
        return repository.findByCategoryAndQuantityIsGreaterThan(category, quantity, pageable);
    }

    @Override
    public Page<Product> findByBrandAndQuantityIsGreaterThan(Brand brand, Integer quantity, Pageable page) {
        return repository.findByBrandAndQuantityIsGreaterThan(brand,quantity,page);
    }

    @Override
    public int countProducts() {
        return repository.countProducts();
    }

    @Override
    public Page<Product> searchProducts(String searchName, Pageable pageable) {
        return repository.searchProducts(searchName, pageable);
    }

    @Override
    public List<Product> randomProducts(Long id, int limit) {
        return repository.randomProducts(id, limit);
    }
}
