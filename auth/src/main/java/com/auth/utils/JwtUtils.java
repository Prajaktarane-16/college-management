package com.auth.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    private static final String jwtSecret= "dfgdhgjhkrtuiukhjdfdgfjhkhjkhugfdsfghgfdrtyghjgfvcxvbnhgfdsfghfdghfdfgdhgjhkrtuiukhjdfdgfjhkhjkhugfdsfghgfdrtyghjgfvcxvbnhgfdsfghfdghfdfgdhgjhkrtuiukhjdfdgfjhkhjkhugfdsfghgfdrtyghjgfvcxvbnhgfdsfghfdghf";

    //this is custom method to expire token at midnight @12am

    public String generateToken(Map<String,Object> userDetail) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date exp = cal.getTime();
        long nowMillis = System.currentTimeMillis();
        return Jwts.builder().setClaims(userDetail).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }
}
