package com.auth.controllers;

import com.auth.entity.LoginEntity;
import com.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apps")
public class LoginController {

    @Autowired
    private JwtUtils jwt;

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody LoginEntity login){

        Map<String,Object> rs = new HashMap<>();

        if(login.getMobile().equals("9284765897") && login.getPassword().equals("prajakta@123")){

            Map<String,Object> tokenDetails = new HashMap<>();
            tokenDetails.put("userId",1); // comman data needs to be store along with token
            String token = null;
            try {
                token = jwt.generateToken(tokenDetails);
            }catch (Exception e){
                e.printStackTrace();
            }

            if(token == null){
                rs.put("status",500);
                rs.put("message", "Internal Server Error");
            }else{
                rs.put("jwtToken", token);
                rs.put("status",200);
                rs.put("message", "Successfully logged-In");
            }



        }else{
            rs.put("status",401);
            rs.put("message", "invalid mobile or password");
        }

        return rs;
    }
}
