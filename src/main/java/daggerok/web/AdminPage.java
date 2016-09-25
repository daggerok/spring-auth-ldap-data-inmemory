package daggerok.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminPage {

    @GetMapping("/admin")
    public String get(Principal principal, Model model) {
        model.addAttribute("user", principal);
        return "admin";
    }
}
