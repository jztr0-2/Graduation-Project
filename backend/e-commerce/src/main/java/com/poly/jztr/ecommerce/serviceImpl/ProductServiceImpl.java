package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.dto.ProductDto;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.ProductVariant;
import com.poly.jztr.ecommerce.repository.ProductRepository;
import com.poly.jztr.ecommerce.service.ProductService;
import com.poly.jztr.ecommerce.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository repository;

    @Autowired
    ProductVariantService productVariantService;

    @Override
    public <S extends Product> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public List<Product> findByNameLike(String name) {
        return repository.findByNameLike(name);
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
}
