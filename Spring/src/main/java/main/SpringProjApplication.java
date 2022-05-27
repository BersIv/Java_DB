package main;

import main.Entity.User;
import main.Repositories.UserRepository;
import main.Service.ArticleService;
import main.Service.BalanceService;
import main.Service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class SpringProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public CommandLineRunner test(ArticleService articleService, BalanceService balanceService,
                                  OperationService operationService) {
        return args -> {

            //userRepository.save(new User("user", passwordEncoder().encode("pwd"), Collections.singletonList("ROLE_USER")));
            //articleService.deleteArticleById(6);
            //balanceService.deleteById(3);
            //balanceService.addBalance(700, 44);
            //articleService.addArticle("Test");
            //operationService.addOperation(articleService.findById(1), balanceService.findById(1));
            //operationService.deleteByName("Article");
            //operationService.updateByName("ds", "postman");
            //operationService.updateById(1, "OKK");

        };
    }
}
