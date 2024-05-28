package com.editor.test.controller;

import com.editor.test.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ListController {

    private ContentService contentService;

    @Autowired
    public ListController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/list")
    public String listPage(Model model) {
        model.addAttribute("ContentList", contentService.selectContent());
        return "list";
    }
}
