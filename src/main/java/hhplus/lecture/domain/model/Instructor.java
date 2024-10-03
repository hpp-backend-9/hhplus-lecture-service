package hhplus.lecture.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 강사 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Instructor {

    private Long instructorId; // 고유 식별자
    private String instructorCode; // 강사 코드
    private String instructorName; // 강사 이름

    public Instructor(String instructorCode, String instructorName) {
        this.instructorCode = instructorCode;
        this.instructorName = instructorName;
    }
}
