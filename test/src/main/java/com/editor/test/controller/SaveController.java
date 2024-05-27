package com.editor.test.controller;

import com.editor.test.model.dto.SaveDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class SaveController {

    private static final Logger log = LoggerFactory.getLogger(SaveController.class);

    @PostMapping("/save")
    public String saveFunc(SaveDto saveDto){
        log.debug("SaveDto - {}", saveDto);
        System.out.println("saveDto = " + saveDto);

        return "redirect:/";
    }
}
