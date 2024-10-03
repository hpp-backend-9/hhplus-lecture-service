package hhplus.lecture.infrastructure.persistence;

import hhplus.lecture.domain.model.Instructor;
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

    // 도메인 모델로 변환
    public Instructor toDomain() {
        return new Instructor(
                instructorCode,
                instructorName
        );
    }

    // 도메인 모델에서 엔티티로 변환하는 정적 메서드
    public static InstructorEntity fromDomain(Instructor instructor) {
        return new InstructorEntity(
                instructor.getInstructorCode(),
                instructor.getInstructorName()
        );
    }
}
