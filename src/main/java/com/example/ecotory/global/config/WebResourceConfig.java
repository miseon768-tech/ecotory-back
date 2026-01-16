package com.example.ecotory.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebResourceConfig implements WebMvcConfigurer {

    // 기능 : 로컬 파일 시스템의 "files" 디렉토리에 접근할 수 있도록 매핑 설정
    // 사용 : 일반 파일 리소스 제공
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        String memberHome = System.getProperty("user.home");

        Path path = Paths.get(memberHome, "files");
        System.out.println(path.toUri().toString());

        registry.addResourceHandler("/files/**").addResourceLocations(path.toUri().toString());
    }

    // 기능 : 로컬 파일 시스템의 "hashtag/images" 디렉토리에 접근할 수 있도록 매핑 설정
    // 사용 : 해시태그 이미지 리소스 제공
/*    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/hashtag/images/**").addResourceLocations(
                Path.of(System.getProperty("member.home"), "hashtag", "images").toUri().toString()
        );
    }*/
}
