package daggerok;

import daggerok.admin.data.AdminUser;
import daggerok.admin.data.AdminUserRepository;
import daggerok.config.ServerSecurityConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication
@Import({ServerSecurityConfig.class})
public class ServerApplication {

    @Bean
    public CommandLineRunner testdata(AdminUserRepository adminUserRepository) {

        Stream.of("max", "user")
                .map(name -> new AdminUser()
                        .setUsername(name)
                        .setPassword(name)
                        .setEnabled(true)
                        .setRoles(Arrays.asList("user".equals(name) ? "USER" : "ADMIN")))
                .forEach(adminUserRepository::encodePasswordAndSave);

        return args -> adminUserRepository.findAll().forEach(System.out::println);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
