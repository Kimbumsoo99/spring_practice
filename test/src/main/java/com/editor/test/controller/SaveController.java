package com.editor.test.controller;

import com.editor.test.dto.SaveDto;
import com.editor.test.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class SaveController {

    private static final Logger LOG = LoggerFactory.getLogger(SaveController.class);
    private ContentService contentService;

    @Autowired
    public SaveController(ContentService contentService) {
        this.contentService = contentService;
    }


    @PostMapping("/save")
    public String saveFunc(SaveDto saveDto) {
        LOG.debug("SaveDto - {}", saveDto);
        contentService.saveContent(saveDto);

        return "redirect:/";
    }

    @PostMapping("/save/{id}")
    public String saveFunc(SaveDto saveDto, @PathVariable("id") String id) {
        LOG.debug("SaveDto - {}", saveDto);
        contentService.updateOneContent(saveDto, id);

        return "redirect:/content/" + id;
    }
}
