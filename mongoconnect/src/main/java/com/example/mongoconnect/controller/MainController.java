package com.example.mongoconnect.controller;

import com.example.mongoconnect.firstdb.repository.Table1Repository;
import com.example.mongoconnect.seconddb.repository.Table2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    Table1Repository table1Repository;
    Table2Repository table2Repository;

    @Autowired
    public MainController(Table1Repository table1Repository, Table2Repository table2Repository) {
        this.table1Repository = table1Repository;
        this.table2Repository = table2Repository;
    }

    @GetMapping("/")
    public String mainP(Model model){
        model.addAttribute("ONE", table1Repository.findAll());
        model.addAttribute("TWO", table2Repository.findAll());
        return "main";
    }
}
