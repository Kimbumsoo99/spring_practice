package com.ssafy.member.controller;

import com.ssafy.member.model.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getMembers(){
        Map<String, Object> map = new HashMap<>();
        map.put("response", memberService.getAllMember());
        System.out.println(map);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }
}
