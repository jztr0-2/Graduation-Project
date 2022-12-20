package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.service.SendSMSService;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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
    public boolean send(){
        try{
            Twilio.init(username,password);
            Message.creator(new PhoneNumber("+84973588761"),
                    new PhoneNumber("+1 978 748 6733"), "Your verify code is: " + generateCode()).create();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private String generateCode(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }
}
