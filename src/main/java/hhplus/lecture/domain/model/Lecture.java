package hhplus.lecture.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 강의 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    private Long lectureId; // 고유 식별자
    private String lectureCode; // 강의 코드 (고유)
    private String lectureName; // 강의명
    private String instructorName; // 강사 이름

    public Lecture(String lectureCode, String lectureName, String instructorName) {
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.instructorName = instructorName;
    }
}
