package com.onseotestapp.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Class MVC configuration.
 *
 * @author Yehor Kachur
 * @version 1.0
 */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    /** Field uploadPath for path to the folder, where we can save an images. */
    @Value("${upload.path}")
    public String uploadPath;

    /**
     * Method creating a patterns path and them locations.And open access to them.
     *
     * @param registry - instance of ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**","/webjars/**","/static/**")
                .addResourceLocations("file:///" + uploadPath + "/")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/");
    }

}
