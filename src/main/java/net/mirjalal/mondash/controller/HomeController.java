package net.mirjalal.mondash.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {
    
    @GetMapping("/")    
    public ModelAndView index(Map<String, Object> model) {
        model.put("environment", "Public1");
        return new ModelAndView("index", model);
    }
}
