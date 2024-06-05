package com.example.mongoconnect.controller;

import com.example.mongoconnect.repository.Table1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    Table1Repository table1Repository;

    @Autowired
    public MainController(Table1Repository table1Repository) {
        this.table1Repository = table1Repository;
    }

    @GetMapping("/")
    public String mainP(Model model){

        model.addAttribute("DATA", table1Repository.findAll());
        return "main";
    }
}
