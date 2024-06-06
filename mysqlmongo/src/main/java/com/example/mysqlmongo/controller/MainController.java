package com.example.mysqlmongo.controller;

import com.example.mysqlmongo.mongorepo.Table1Repository;
import com.example.mysqlmongo.mysqlrepo.FirstdbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private Table1Repository table1Repository;
    private FirstdbRepository firstdbRepository;

    @Autowired
    public MainController(Table1Repository table1Repository, FirstdbRepository firstdbRepository) {
        this.table1Repository = table1Repository;
        this.firstdbRepository = firstdbRepository;
    }

    @GetMapping("/")
    public String mainP(Model model){

        model.addAttribute("MON", table1Repository.findAll());
        model.addAttribute("RDB", firstdbRepository.findAll());

        return "main";
    }
}
