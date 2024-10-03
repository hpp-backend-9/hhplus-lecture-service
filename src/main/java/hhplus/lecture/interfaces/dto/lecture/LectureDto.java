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
    private String instructorName; // 강사 이름

    public LectureDto(String lectureCode, String lectureName, String instructorName) {
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.instructorName = instructorName;
    }

    public static LectureDto fromDomain(Lecture lecture) {
        return new LectureDto(
                lecture.getLectureCode(),
                lecture.getLectureName(),
                lecture.getInstructorName()
        );
    }

    public static LectureDto fromEntity(LectureEntity entity) {
        return new LectureDto(
                entity.getLectureCode(),
                entity.getLectureName(),
                entity.getInstructorName()
        );
    }
}

