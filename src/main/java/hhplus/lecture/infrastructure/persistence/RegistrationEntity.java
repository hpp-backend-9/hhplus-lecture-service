package hhplus.lecture.infrastructure.persistence;

import hhplus.lecture.domain.model.Registration;
import hhplus.lecture.domain.model.RegistrationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자 등록 정보 엔티티 (데이터베이스 매핑)
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "registration")
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자동 증가하는 ID (기본 키)

    @Column(nullable = false, length = 8)
    private String userCode; // 사용자 고유 코드

    @Column(nullable = false, length = 10)
    private String lectureItemCode; // 강의 일정 고유 코드

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status; // 신청 상태 (WAIT, APPROVAL, REFUSAL)

    @Column(nullable = false)
    private LocalDateTime registrationDate; // 신청 날짜

    public RegistrationEntity(String userCode, String lectureItemCode, RegistrationStatus status, LocalDateTime registrationDate) {
        this.userCode = userCode;
        this.lectureItemCode = lectureItemCode;
        this.status = status;
        this.registrationDate = registrationDate;
    }

    public Registration toDomain() {
        return new Registration(
                userCode,
                lectureItemCode,
                status,
                registrationDate
        );
    }

    public static RegistrationEntity fromDomain(Registration registration) {
        return new RegistrationEntity(
                registration.getUserCode(),
                registration.getLectureItemCode(),
                registration.getStatus(),
                registration.getRegistrationDate()
        );
    }

    public void updateStatus(RegistrationStatus status) {
        this.status = status;
    }
}