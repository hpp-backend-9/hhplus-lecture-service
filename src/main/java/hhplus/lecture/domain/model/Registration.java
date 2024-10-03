package hhplus.lecture.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 강의 신청에 필요한 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Registration {

    private Long registrationId; // 고유 식별자
    private String userCode; // 사용자 고유 코드
    private String lectureItemCode; // 강의 일정 고유 코드
    private RegistrationStatus status; // 신청 상태
    private LocalDateTime registrationDate; // 신청 날짜

    // 신청 내역 생성
    public Registration(String userCode, String lectureItemCode, RegistrationStatus status, LocalDateTime registrationDate) {
        this.userCode = userCode;
        this.lectureItemCode = lectureItemCode;
        this.status = status;
        this.registrationDate = registrationDate;
    }
}
