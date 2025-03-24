package org.link.gateway.filter;

import org.link.gateway.utils.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Order(-1)
@Component
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        System.out.println(path);

        if (path.startsWith("/user/login")||path.startsWith("/admin")||path.startsWith("/activity/adminView")||path.startsWith("/activity/admin")) {
            return chain.filter(exchange);
        }

        try{
            String token = request.getHeaders().getFirst("Authorization");
            Map<String, Object> claims = JwtUtil.parseToken(token);
            String userId = (String) claims.get("userId");
//            System.out.println(userId);
            Boolean isBan = (Boolean) claims.get("isBan") ;
//            System.out.println(isBan);
            String authStatus = (String) claims.get("authStatus");
//            System.out.println(authStatus);
            ServerHttpRequest newRequest = request.mutate()
                    .header("userId", userId)
                    .header("isBan", isBan.toString())
                    .header("authStatus", authStatus)
                    .build();
            return chain.filter(exchange.mutate().request(newRequest).build());
//            return chain.filter(exchange);
        }catch (Exception exception){
            System.out.println(exception);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
