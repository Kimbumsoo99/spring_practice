package com.editor.test.controller;

import com.editor.test.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EditController {

    private static final Logger LOG = LoggerFactory.getLogger(EditController.class);
    private ContentService contentService;

    @Autowired
    public EditController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/editor")
    public String editorPage() {
        return "editor";
    }

    @GetMapping("/editor/{id}")
    public String updatePage(@PathVariable("id") String id, Model model) {
        LOG.debug("id-{}",id);

        model.addAttribute("data", contentService.selectOneContent(id));


        return "editor";

    }
}
