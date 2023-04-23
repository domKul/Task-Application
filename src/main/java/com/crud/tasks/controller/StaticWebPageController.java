package com.crud.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class StaticWebPageController {

    String first = "2 * 2 = 4";
    String second = "2 * 2 + 2 = 6";
    String third = "2 - 2 * 2 = -2";

    @RequestMapping("/")
    public String index(Map<String, Object > model){
       model.put("variable", "My Thymeleaf variable");
       model.put("one", first);
       model.put("two", second);
       model.put("three",third);
        return "index";
    }
}
