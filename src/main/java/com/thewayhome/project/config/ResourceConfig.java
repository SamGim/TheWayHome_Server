package com.thewayhome.project.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("classpath:/static/images/");
//        .addResourceLocations("file:./src/main/resources/static/images");
//        registry.addResourceHandler("/carzenResource/**")
//                .addResourceLocations("file:/Users/jykim/Desktop/carzenResource/");
    }
}
