package com.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private static final String jwtSecret= "dfgdhgjhkrtuiukhjdfdgfjhkhjkhugfdsfghgfdrtyghjgfvcxvbnhgfdsfghfdghfdfgdhgjhkrtuiukhjdfdgfjhkhjkhugfdsfghgfdrtyghjgfvcxvbnhgfdsfghfdghfdfgdhgjhkrtuiukhjdfdgfjhkhjkhugfdsfghgfdrtyghjgfvcxvbnhgfdsfghfdghf";


    //in this example we are extractig data from jwt token which we have inseretd while creating the token
    public Claims getClaims(final String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        } catch (Exception exc) {
            // log.error(exc.getMessage() + " => " + exc);
        }
        return null;
    }

    // validating the token with expiry time
    public void validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        }  catch (Exception exc) {
            //  log.error(exc.getMessage() + " => " + exc);
            throw new RuntimeException(exc.getMessage(), exc);
        }
    }

}
