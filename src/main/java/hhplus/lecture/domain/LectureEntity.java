package hhplus.lecture.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DB와 매핑
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lecture")
public class LectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId; // 자동 증가하는 ID (기본 키)

    @Column(nullable = false, unique = true, length = 5)
    private String lectureCode; // 강의 코드 (고유)

    @Column(nullable = false, length = 40)
    private String lectureName; // 강의명

    @Column(nullable = false, length = 20)
    private String instructorCode; // 강사 코드

    public LectureEntity(String lectureCode, String lectureName, String instructorCode) {
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.instructorCode = instructorCode;
    }
}
