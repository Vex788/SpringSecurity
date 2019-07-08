package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(final UserService userService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                roleService.createRoleIfNotFound("ROLE_ADMIN");
                roleService.createRoleIfNotFound("ROLE_USER");

                String salt = BCrypt.gensalt(4);
                userService.addUser("admin",
                        passwordEncoder.encode("admin") + salt, salt,
                        Arrays.asList(roleService.findRoleByName("ROLE_ADMIN")), "", "");

                salt = BCrypt.gensalt(4);
                userService.addUser("user",
                        passwordEncoder.encode("user") + salt, salt,
                        Arrays.asList(roleService.findRoleByName("ROLE_USER")), "", "");
            }
        };
    }
}