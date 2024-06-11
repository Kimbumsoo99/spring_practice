package com.spring.securityfirst.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
@Slf4j
public class MainController {

    @GetMapping("/")
    public String mainP(Model model){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> it = authorities.iterator();
        GrantedAuthority auth = it.next();
        String role = auth.getAuthority();

        log.debug("====== MainController mainP ======");
        log.debug("id - {}", id);
        log.debug("role - {}", role);

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "main";
    }
}
