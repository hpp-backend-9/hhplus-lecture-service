package hhplus.lecture.interfaces.dto.lecture;

import hhplus.lecture.domain.model.Lecture;
import hhplus.lecture.infrastructure.persistence.LectureEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 정보 전달을 위한 DTO
 * (강의 목록 조회)
 */
@Getter
@NoArgsConstructor
public class LectureDto {
    private String lectureCode; // 강의 코드
    private String lectureName; // 강의명
    private String getInstructorCode; // 강사 코드

    public LectureDto(String lectureCode, String lectureName, String getInstructorCode) {
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.getInstructorCode = getInstructorCode;
    }

    public static LectureDto fromDomain(Lecture lecture) {
        return new LectureDto(
                lecture.getLectureCode(),
                lecture.getLectureName(),
                lecture.getInstructorCode()
        );
    }

    public static LectureDto fromEntity(LectureEntity entity) {
        return new LectureDto(
                entity.getLectureCode(),
                entity.getLectureName(),
                entity.getInstructorCode()
        );
    }
}

