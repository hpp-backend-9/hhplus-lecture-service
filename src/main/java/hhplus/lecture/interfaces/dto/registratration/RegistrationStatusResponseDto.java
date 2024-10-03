package hhplus.lecture.interfaces.dto.registratration;

import hhplus.lecture.domain.model.Registration;
import hhplus.lecture.domain.model.RegistrationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 특강 신청 상태 응답
 */
@Getter
@NoArgsConstructor
public class RegistrationStatusResponseDto {

    private String userCode; // 사용자 고유 코드
    private String lectureItemCode; // 강의 일정 고유 코드
    private RegistrationStatus status; // 신청 상태
    private LocalDateTime registrationDate; // 신청 날짜

    public RegistrationStatusResponseDto(String userCode, String lectureItemCode, RegistrationStatus status, LocalDateTime registrationDate) {
        this.userCode = userCode;
        this.lectureItemCode = lectureItemCode;
        this.status = status;
        this.registrationDate = registrationDate;
    }

    public static RegistrationStatusResponseDto fromDomain(Registration registration) {
        return new RegistrationStatusResponseDto(
                registration.getUserCode(),
                registration.getLectureItemCode(),
                registration.getStatus(),
                registration.getRegistrationDate()
        );
    }
}
