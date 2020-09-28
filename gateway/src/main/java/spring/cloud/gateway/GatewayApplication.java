package spring.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    
    @Bean
    public RouteLocator pathRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("path_route", r -> r.path("/about").uri("http://ityouknow.com"))
                .build();
    }
    
    @Bean
    public RouteLocator cookieRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("path_route", r -> r.cookie("Cookie", "*").uri("http://ityouknow.com"))
                .build();
    }
    
    @Bean
    public RouteLocator headerRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("path_route", r -> r.header("Cookie", "*").uri("http://ityouknow.com"))
                .build();
    }
    
    @Bean
    public RouteLocator methodRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("path_route", r -> r.method("GET", "POST").uri("http://ityouknow.com"))
                .build();
    }
    
    @Bean
    public RouteLocator queryRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("path_route", r -> r.query("red", "gree").uri("http://ityouknow.com"))
                .build();
    }
    
    
}
