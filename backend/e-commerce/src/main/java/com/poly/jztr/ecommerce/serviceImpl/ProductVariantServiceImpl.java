package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.dto.ProductVariantDto;
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
      return products.stream().map(product -> {
          if(product.getProductVariants().size() > 0)
                return product.getProductVariants().get(0);
          return null;
      }).collect(Collectors.toList());
    }

    @Override
    public List<ProductVariant> toProductVariantFromDto(List<ProductVariantDto> list) {
        if(list == null) return null;
       return list.stream().map(dto -> new ProductVariant(dto)).collect(Collectors.toList());
    }

    @Override
    public <S extends ProductVariant> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public void minusQuantity(Long id, Integer quantity) {
        ProductVariant p = repository.findById(id).get();
        p.setQuantity(p.getQuantity() - quantity);
        repository.save(p);
    }

    @Override
    public ProductVariant findById(Long id) {
        return repository.findById(id).get();
    }
}
