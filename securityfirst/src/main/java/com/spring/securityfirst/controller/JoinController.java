package com.spring.securityfirst.controller;

import com.spring.securityfirst.model.dto.JoinDTO;
import com.spring.securityfirst.model.service.JoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class JoinController {

    private JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping("/join")
    public String joinP() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){
        System.out.println(joinDTO);
        log.info("joinDTO - {}", joinDTO);

        joinService.joinProcess(joinDTO);
        return "redirect:/login";
    }
}
