package hhplus.lecture.interfaces.controller;

import hhplus.lecture.application.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/lectures/{lectureItemCode}/register")
    public ResponseEntity<String> registerForLecture(
            @PathVariable String lectureItemCode,
            @RequestBody Map<String, String> requestBody) {

        String userCode = requestBody.get("userCode");

        boolean success = registrationService.registerLecture(userCode, lectureItemCode);
        if (success) {
            return ResponseEntity.ok("등록 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("등록 실패");
        }
    }


    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
}
