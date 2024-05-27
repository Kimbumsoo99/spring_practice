package com.editor.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListController {
    @GetMapping("/list")
    public String listPage() {
        return "list";
    }
}