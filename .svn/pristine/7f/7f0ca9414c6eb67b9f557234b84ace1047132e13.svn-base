package com.hbsi.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	private CorsConfiguration buildConfig() {
		List<String> header=new ArrayList<>();
		header.add("authorization");	
		header.add("Authorization");	
		header.add("content-type");
		header.add("*");
		List<String> origin=new ArrayList<>();
		origin.add("*");
		List<String> method=new ArrayList<>();
		method.add("POST");
		method.add("OPTIONS");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 设置访问源地址
        corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头
        corsConfiguration.addAllowedHeader("Authorization");
        corsConfiguration.setAllowedHeaders(header);
        corsConfiguration.setAllowedOrigins(origin);
        corsConfiguration.setAllowedMethods(method); 
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法
//        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.setMaxAge(10000l);
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }
	@Bean
	public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }


}
