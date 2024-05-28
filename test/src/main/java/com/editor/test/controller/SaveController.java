package com.editor.test.controller;

import com.editor.test.model.dto.SaveDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class SaveController {

    private static final Logger LOG = LoggerFactory.getLogger(SaveController.class);

    @PostMapping("/save")
    public String saveFunc(SaveDto saveDto){
        LOG.debug("SaveDto - {}", saveDto);
        System.out.println("saveDto = " + saveDto);

        return "redirect:/";
    }
}
