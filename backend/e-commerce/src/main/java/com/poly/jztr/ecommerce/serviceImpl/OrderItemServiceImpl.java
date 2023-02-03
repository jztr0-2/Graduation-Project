package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.dto.OrderItemDto;
import com.poly.jztr.ecommerce.model.OrderItem;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.service.OrderItemService;
import com.poly.jztr.ecommerce.service.ProductService;
import com.poly.jztr.ecommerce.model.Product;
import com.poly.jztr.ecommerce.repository.OrderItemRepository;
import com.poly.jztr.ecommerce.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    ProductService productService;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> toOrderItem(List<OrderItemDto> dtos){
       return dtos.stream().map(dto ->{
           OrderItem orderItem = new OrderItem();
           orderItem.setQuantity(dto.getQuantity());
           orderItem.setUnitPrice(Double.valueOf(productService.findById(dto.getProductId()).get().getUnitPrice()));
           orderItem.setProduct(new Product(dto.getProductId()));
           return  orderItem;
       }) .collect(Collectors.toList());
    }

    @Override
    public Integer totalSoldProductsByProductId(Long id) {
        return orderItemRepository.totalSoldProductsByProductId(id);
    }

    @Override
    public Page<Product> getDealOfDay(Pageable pageable) {
        return orderItemRepository.getDealOfDay(pageable);
    }

    @Override
    public Page<Product> getBestSellerProducts(Pageable pageable) {
        return orderItemRepository.getBestSellerProducts(pageable);
    }
}
