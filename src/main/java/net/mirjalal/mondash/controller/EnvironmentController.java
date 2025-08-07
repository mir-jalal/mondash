package net.mirjalal.mondash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.mirjalal.mondash.service.EnvironmentService;
import lombok.AllArgsConstructor;


@Controller
public class EnvironmentController {

    @Autowired
    final EnvironmentService environmentService = new EnvironmentService();

    @GetMapping("/restart")
    public String restartEnvironment() {
        environmentService.restartEnvironment();
        
        return "redirect:/";
    }
    
}
