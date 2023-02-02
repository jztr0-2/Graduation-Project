package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.service.SendSMSService;
import com.poly.jztr.ecommerce.service.UserService;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.converter.Promoter;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SendSMSServiceImpl implements SendSMSService {

    @Value("${twilio.username}")
    private String username;

    @Value("${twilio.password}")
    private String password;

    @Autowired
    UserService userService;

    @Override
    public boolean send(String phone, String msg){
        try{
            Twilio.init(username,password);
            Message.creator(new PhoneNumber(castPhone(phone)),
                    new PhoneNumber("+1 978 748 6733"), msg).create();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private String generateCode(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }

    private String castPhone(String phone){
        return "+84" + phone.substring(1);
    }
}
