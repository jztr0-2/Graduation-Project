package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.model.ProductVariant;
import com.poly.jztr.ecommerce.service.ProductVariantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Override
    public List<ProductVariant> toProductVariant(List<Product> products){
      return products.stream().map(product -> product.getProductVariants().get(0)).collect(Collectors.toList());
    }
}
