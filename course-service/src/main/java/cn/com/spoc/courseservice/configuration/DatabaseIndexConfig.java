package cn.com.spoc.courseservice.configuration;

import cn.com.spoc.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseIndexConfig {
    @Autowired
    private CourseService courseService;
    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> courseService.createFullTextIndex();
    }
}
