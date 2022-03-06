package com.ltl.demo.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域访问配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

//    //当前跨域请求最大有效时长，这里默认1天
//    private static final long MAX_AGE = 24 * 60 * 60;
//
//    private CorsConfiguration buildConfig(){
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");    //1.设置访问原地址
//        corsConfiguration.addAllowedHeader("*");    //2.设置访问请求头
//        corsConfiguration.addAllowedMethod("*");    //3.设置访问源请求方法
//        corsConfiguration.setMaxAge(MAX_AGE);       //4.设置跨域最大有效时长
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        //对接口配置跨域设置
//        source.registerCorsConfiguration("/**",buildConfig());
//        return new CorsFilter(source);
//    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
                .allowedOriginPatterns("*")
                //放行哪些请求方式
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                //.allowedMethods("*") //或者放行全部
                //放行哪些原始请求头部信息
                .allowedHeaders("*")
                //暴露哪些原始请求头部信息
                .exposedHeaders("*");
    }

}
