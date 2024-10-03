package hhplus.lecture.interfaces.controller;

import hhplus.lecture.application.service.UserService;
import hhplus.lecture.interfaces.dto.user.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 사용자가 신청한 강의 조회
    @GetMapping("/{userCode}/lectures")
    public ResponseEntity<UserResponseDto> getUserRegisteredLectures(@PathVariable String userCode) {
        UserResponseDto userResponse = userService.getUserAndRegisteredLectures(userCode);
        return ResponseEntity.ok(userResponse);
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
