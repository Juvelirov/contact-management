package ru.denisov.NauJava;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.denisov.NauJava.Entity.Contact;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Contact> contactContainer(){
        return new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        System.out.println(appName);
        System.out.println("Version: " + appVersion);
    }
}
