package daggerok.admin.userdetails;

import daggerok.admin.data.AdminUserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    AdminUserRepository adminUserRepository;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(final String username) {

        return new AdminDetails(
                adminUserRepository.findByUsername(username).orElseThrow(
                        () -> new UsernameNotFoundException(format("wrong admin '%s'", username))));
    }
}
