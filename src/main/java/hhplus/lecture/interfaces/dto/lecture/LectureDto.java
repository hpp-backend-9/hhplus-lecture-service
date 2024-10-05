package hhplus.lecture.interfaces.dto.lecture;

import hhplus.lecture.domain.LectureEntity;
import hhplus.lecture.domain.LectureItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 정보 전달을 위한 DTO
 * (강의 목록 조회)
 */
@Getter
@NoArgsConstructor
public class LectureDto {
    private String lectureCode; // 강의 코드
    private String lectureItemCode;
    private String lectureName; // 강의명
    private String instructorCode; // 강사 코드
    private String instructorName; // 강사명
    private LocalDate lectureDate; // 강의 날짜
    private int maxCapacity; // 정원

    public LectureDto(String lectureCode, String lectureItemCode, String lectureName, String instructorCode, String instructorName, LocalDate lectureDate, int maxCapacity) {
        this.lectureCode = lectureCode;
        this.lectureItemCode = lectureItemCode;
        this.lectureName = lectureName;
        this.instructorCode = instructorCode;
        this.instructorName = instructorName;
        this.lectureDate = lectureDate;
        this.maxCapacity = maxCapacity;
    }

    public static LectureDto fromEntity(LectureEntity entity, LectureItemEntity lectureItem, String instructorName) {
        return new LectureDto(
                entity.getLectureCode(),
                lectureItem.getLectureItemCode(),
                entity.getLectureName(),
                entity.getInstructorCode(),
                instructorName,
                lectureItem.getLectureDate(),
                lectureItem.getMaxCapacity()
        );
    }
}
