package org.likelion.zagabi.Global.Config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CorsConfig implements WebMvcConfigurer {

    public static CorsConfigurationSource apiConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        ArrayList<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("http://localhost:8080");
        allowedOriginPatterns.add("http://localhost:3000");
        allowedOriginPatterns.add("http://127.0.0.1:8000");
        allowedOriginPatterns.add("http://127.0.0.1:5500");
        allowedOriginPatterns.add("http://127.0.0.1:5000");
        allowedOriginPatterns.add("http://127.0.0.1:5001");
        allowedOriginPatterns.add("http://zagabi.shop");
        allowedOriginPatterns.add("http://www.zagabi.shop");


        ArrayList<String> allowedHttpMethods = new ArrayList<>();
        allowedHttpMethods.add("GET");
        allowedHttpMethods.add("POST");
        allowedHttpMethods.add("PUT");
        allowedHttpMethods.add("PATCH");
        allowedHttpMethods.add("DELETE");

        configuration.setAllowedOriginPatterns(allowedOriginPatterns); // setAllowedOrigins를 setAllowedOriginPatterns로 변경
        configuration.setAllowedMethods(allowedHttpMethods);
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
