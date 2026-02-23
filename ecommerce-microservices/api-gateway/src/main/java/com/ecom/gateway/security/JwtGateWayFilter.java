package com.ecom.gateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class JwtGateWayFilter implements GatewayFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtGateWayFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private static final List<String> PUBLIC_URLS =
            List.of("/api/users/register", "/api/users/login");

    @Override
    public int getOrder() {
        return 0;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getURI().toString();
        // ✅ Allow public URLs
        if (PUBLIC_URLS.stream().anyMatch(path::contains)) {
            return chain.filter(exchange);
        }

        String authHeader =
                exchange.getRequest().getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

        // ✅ Check Authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        // ✅ Validate token
        if (!jwtUtil.validateToken(token)) {
            return unauthorized(exchange);
        }

        String role = jwtUtil.getRoleFromToken(token);
        if (!isAutherization(role , path , method )){
            return forbidden(exchange);
        }
        // ✅ Extract user info from token
        String userId = jwtUtil.getUserIdFromToken(token);


        // ✅ Add headers to downstream services
        var modifiedRequest = exchange.getRequest()
                .mutate()
                .header("X-User-Id", userId)
                .header("X-User-Role", role)
                .build();

        return chain.filter(
                exchange.mutate()
                        .request(modifiedRequest)
                        .build()
        );
    }

    // ✅ Unauthorized handler
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private static final Map<String, Map<String, List<String>>> ROLE_API_PERMISSION = Map.of(

            "ADMIN", Map.of(
                    "GET", List.of("/api/products"),
                    "POST", List.of("/api/products"),
                    "PUT", List.of("/api/products"),
                    "DELETE", List.of("/api/product")
            ),

            "USER", Map.of(
                    "GET", List.of("/api/products")
            )
    );

    private boolean isAutherization(String role , String path , String method){
        Map<String, List<String>>  permissions = ROLE_API_PERMISSION.get(role);
        if (permissions == null) {
            return   false;
        }
        List<String> allowedPermission = permissions.get(method);

        if (allowedPermission == null) {
            return false;
        }
        return allowedPermission.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}