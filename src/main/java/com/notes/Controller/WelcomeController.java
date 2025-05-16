package com.notes.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String welcomePage(Locale locale, Model model) {
        model.addAttribute("locale", locale);
        return "Welcome";
    }
}
