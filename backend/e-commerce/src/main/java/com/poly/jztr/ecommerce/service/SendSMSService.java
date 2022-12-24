package com.poly.jztr.ecommerce.service;
public interface SendSMSService {
    boolean send(String phone, String msg);
}
