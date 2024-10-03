package hhplus.lecture.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "instructor")
public class InstructorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId; // 자동 증가하는 ID (기본 키)

    @Column(nullable = false, length = 10)
    private String instructorCode; // 강사 코드

    @Column(nullable = false, length = 20)
    private String instructorName; // 강사 이름

    public InstructorEntity(String instructorCode, String instructorName) {
        this.instructorCode = instructorCode;
        this.instructorName = instructorName;
    }
}
