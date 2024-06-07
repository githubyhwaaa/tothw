package com.example.demo.utiles;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.util.Date;
import java.util.Map;

public class jwtutil {
//    Map为集合,一个集合两个类型的参数String和Object
    public static String grtToken(Map<String, Object> claims){
        return JWT.create().withClaim("claims",claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))
                .sign(Algorithm.HMAC256("qwer"));
    }
    public static Map<String,Object> parsetoken(String token){
        return JWT.require(Algorithm.HMAC256("qwer"))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}
