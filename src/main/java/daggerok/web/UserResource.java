package daggerok.web;

import daggerok.admin.data.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserResource {

    @GetMapping("/me")
    public Principal get(Principal principal) {
        return principal;
    }
}
