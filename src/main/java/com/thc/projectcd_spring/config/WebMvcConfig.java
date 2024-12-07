package com.thc.projectcd_spring.controller.config;//package com.thc.followup.config;

//import com.thc.projectcd_spring.interceptor.DefaultInterceptor;
import com.thc.projectcd_spring.util.TokenFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final TokenFactory tokenFactory;

    @Value("${file.upload.path}")
    private String uploadPath;

    public WebMvcConfig(TokenFactory tokenFactory) {

        this.tokenFactory = tokenFactory;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE")
                .allowedHeaders("*");
    }

    //파일 접근하는 통로 열어두고 싶다면 사용하면 됨
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        // 절대경로 file:///c:/upload/
        // 상대경로 file:./upload/
        registry
                .addResourceHandler("/files/**")
                //접근할 파일이 있는 위치를 지정
                .addResourceLocations("file:" + uploadPath)
                .setCachePeriod(60 * 60) // 초 단위
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

//    //인터셉터 설정을 위함
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new DefaultInterceptor())
//                .addPathPatterns("/api/**") //인터셉터가 실행되야 하는 url 패턴 설정
//                .excludePathPatterns("/resources/**", "/api/tbuser/login", "/api/tbuser/accessToken"); //인터셉터가 실행되지 않아야 하는 url 패턴
//    }

}