package ru.itpark.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itpark.models.User;
import ru.itpark.repositories.UserRepositori;

@SpringBootApplication
@ComponentScan(basePackages = "ru.itpark")
@EntityScan(basePackages = "ru.itpark.models")
@EnableJpaRepositories(basePackages = "ru.itpark.repositories")
@PropertySource("classpath:application.properties")
public class Application {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    static private UserRepositori userRepositori;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
