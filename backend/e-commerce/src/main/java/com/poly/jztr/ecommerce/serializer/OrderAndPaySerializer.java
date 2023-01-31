package com.poly.jztr.ecommerce.serializer;

import com.poly.jztr.ecommerce.model.Order;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderAndPaySerializer {
    private Order order;
    private String qrCode;

    public OrderAndPaySerializer(Order order){
        this.order = order;
        this.qrCode = "https://momosv3.apimienphi.com/api/QRCode?phone=0973588761" +
                "&amount=" + (int) order.getTotal().doubleValue() +
                "&note=" + order.getPaymentCode();
    }
}
