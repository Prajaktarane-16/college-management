package com.gateway.utils;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class JwtFilter extends AbstractGatewayFilterFactory {

    @Autowired
    private JwtUtils authUtil;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            //below piece of code is for list of unsecured apis's/urls.( eg. login)
            final List<String> apiEndpoints = List.of("/apps/login");

            Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                    .noneMatch(uri -> r.getURI().getPath().contains(uri));
            if (isApiSecured.test(request)) { // if "apiEndpoints" is not listed in the above list then only this filter will apply
                if (!request.getHeaders().containsKey("Authorization")) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
                final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
                try {
                    authUtil.validateToken(token);
                } catch (Exception exc) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    return response.setComplete();
                }

                Claims claims = authUtil.getClaims(token);
                String userId = String.valueOf(claims.get("userId"));
//                String loginType= String.valueOf(claims.get("loginType"));

                // here we can write custome for validating the user (eg: whether user is login/active )
//                if(userId == null || !authUtil.validateUser(userId,loginType)){  //|| !authUtil.validateUser(userId,loginType)
//                    ServerHttpResponse response = exchange.getResponse();
//                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                    return response.setComplete();
//                }

                // transferring userid with all incoming rqst
                // user_id we are appending to all rqst
                exchange.getRequest().mutate().header("user_id", userId).build();
            }

            return chain.filter(exchange);
        };

    }
}
