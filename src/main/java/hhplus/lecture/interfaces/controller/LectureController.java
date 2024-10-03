package hhplus.lecture.interfaces.controller;

import hhplus.lecture.application.service.LectureService;
import hhplus.lecture.application.service.RegistrationService;
import hhplus.lecture.interfaces.dto.lecture.LectureDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {
    private final LectureService lectureService;
    private final RegistrationService registrationService;

    // 특강 목록 구현
    @GetMapping
    public ResponseEntity<List<LectureDto>> getLectures() {
        List<LectureDto> lectureDtos = lectureService.getAllLectures();
        return ResponseEntity.ok(lectureDtos);
    }

    public LectureController(LectureService lectureService, RegistrationService registrationService) {
        this.lectureService = lectureService;
        this.registrationService = registrationService;
    }
}
