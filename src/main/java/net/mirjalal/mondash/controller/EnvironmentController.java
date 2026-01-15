package net.mirjalal.mondash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.mirjalal.mondash.service.EnvironmentService;
import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @GetMapping("/restart")
    public String restartEnvironment() {
        environmentService.restartEnvironment();
        
        return "redirect:/";
    }
    
}
