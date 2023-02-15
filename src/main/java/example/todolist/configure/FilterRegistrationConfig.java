package example.todolist.configure;

import example.todolist.filter.HealthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfig {
    @Bean
    public FilterRegistrationBean<HealthFilter> healthFilterRegistration() {
        FilterRegistrationBean<HealthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HealthFilter());
        registration.addUrlPatterns("/health");
        return registration;
    }
}
