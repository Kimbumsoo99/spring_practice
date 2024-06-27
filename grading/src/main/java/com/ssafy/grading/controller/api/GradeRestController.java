package com.ssafy.grading.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.grading.service.GradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class GradeRestController {
    private final GradeService gradeService;

    @Autowired
    public GradeRestController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping("/grade")
    public ResponseEntity<String> grade(@RequestBody JsonNode request) {
        log.info("request - {}", request);
        try {
            String user = request.get("user").asText();
            String answerCode = request.get("answer").asText();
            int testCaseCount = request.get("testCount").asInt();

            gradeService.saveSolution(user, answerCode);
            gradeService.generateTestData(testCaseCount);
            String result = gradeService.getUserResult();

            return ResponseEntity
                    .ok(result);
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Error processing request: " + e.getMessage());
        }
    }

}
