package hhplus.lecture.interfaces.controller;

import hhplus.lecture.application.service.LectureService;
import hhplus.lecture.application.service.RegistrationService;
import hhplus.lecture.interfaces.dto.lecture.LectureDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {
    private final LectureService lectureService;

    // 특강 목록 구현
    @GetMapping
    public ResponseEntity<List<LectureDto>> getLectures() {
        List<LectureDto> lectureDtos = lectureService.getAllLectures();
        return ResponseEntity.ok(lectureDtos);
    }

    // 신청 가능한 특강 목록
    @GetMapping("/date/{targetDate}")
    public ResponseEntity<List<LectureDto>> getAvailableLecturesByDate(@PathVariable String targetDate) {
        LocalDate date = LocalDate.parse(targetDate);
        List<LectureDto> availableLectures = lectureService.getAvailableLecturesByDate(date);
        return ResponseEntity.ok(availableLectures);
    }

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }
}
