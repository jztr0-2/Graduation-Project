package com.poly.jztr.ecommerce.serializer;

import com.poly.jztr.ecommerce.model.Order;
import com.poly.jztr.ecommerce.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class RecentOrderSerializer {
    private List<User> user;
    public RecentOrderSerializer(List<Order> order){
        this.user = order.stream().map(Order::getUser).collect(Collectors.toList());
    }

    public List<User> getUser(){
        return user;
    }
}
