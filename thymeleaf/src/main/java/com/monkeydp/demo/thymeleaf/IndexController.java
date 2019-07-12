package com.monkeydp.demo.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author iPotato
 * @date 2019/7/12
 */
@Controller
public class IndexController {
    /**
     * http://localhost:8080?name=iPotato
     *
     * @param model
     * @param name
     * @return
     */
    @GetMapping("/")
    public String index(Model model, @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        model.addAttribute("name", name);
        // index.html
        return "index";
    }
}
