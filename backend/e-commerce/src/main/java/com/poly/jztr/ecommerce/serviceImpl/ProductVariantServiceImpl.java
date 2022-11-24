package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.ProductVariant;
import com.poly.jztr.ecommerce.repository.ProductVariantRepository;
import com.poly.jztr.ecommerce.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    ProductVariantRepository repository;
    @Override
    public List<ProductVariant> toProductVariant(List<Product> products){
      return products.stream().map(product -> product.getProductVariants().get(0)).collect(Collectors.toList());
    }
    @Override
    public <S extends ProductVariant> S save(S entity) {
        return repository.save(entity);
    }
}
