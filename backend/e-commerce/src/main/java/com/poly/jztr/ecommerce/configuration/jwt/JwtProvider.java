package com.poly.jztr.ecommerce.configuration.jwt;


import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.poly.jztr.ecommerce.model.User;
import com.poly.jztr.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Autowired
    UserService userService;
    public String generateTokenLogin(String username) {
        String token = null;
        try {
            User user = userService.findByEmail(username).get();
            JWSSigner signer = new MACSigner(generateShareSecret());
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim("Username", username);
            builder.expirationTime(generateExpirationTime());
            String[] roles = {"USER"};
            builder.claim("ROLES",roles);
            builder.claim("firstName",user.getFirstName());
            builder.claim("lastName",user.getLastName());
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }


    public JWTClaimsSet getClaimsSetFromToken(String token) {
        JWTClaimsSet claimsSet = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(generateShareSecret());
            if (signedJWT.verify(verifier)) {
                claimsSet = signedJWT.getJWTClaimsSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claimsSet;
    }

    private Date generateExpirationTime() {
        long expireTime =1000000000000000L; //Constants.JWT_EXPIRATION_TIME;
        Date currentDate= new Date();
        Date expDate = new Date(currentDate.getTime()+expireTime);
        return expDate;
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration = null;
        JWTClaimsSet claimsSet = getClaimsSetFromToken(token);
        expiration = claimsSet.getExpirationTime();
        return expiration;
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            JWTClaimsSet claimsSet = getClaimsSetFromToken(token);
            username = claimsSet.getStringClaim("Username");
            System.out.println("line 83"+ username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    private byte[] generateShareSecret() {
        byte[] sharedSecret = new byte[32];
        sharedSecret = "Constant.dasdsadsadasflkdsgjdsljflmteblilfgnkjdlvxcmgojflkdsjgkldsnfjkdnjkfddkdncdsmfndsjkdskfdsjksvdssjjvkUSERNAME".getBytes();
        return sharedSecret;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateTokenLogin(String token) {
        if (token == null || token.trim().length() == 0) return false;
        String username = getUsernameFromToken(token);
        if (username == null || username.isEmpty()) return false;
        if (isTokenExpired(token)) return false;
        return true;
    }
}