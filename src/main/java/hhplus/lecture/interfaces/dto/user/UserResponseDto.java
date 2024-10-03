package hhplus.lecture.interfaces.dto.user;

import hhplus.lecture.domain.model.User;
import hhplus.lecture.interfaces.dto.lecture.LectureDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private User user; // 사용자 정보
    private List<LectureDto> registeredLectures; // 사용자가 신청한 강의 목록

    public UserResponseDto(User user, List<LectureDto> registeredLectures) {
        this.user = user;
        this.registeredLectures = registeredLectures;
    }
}