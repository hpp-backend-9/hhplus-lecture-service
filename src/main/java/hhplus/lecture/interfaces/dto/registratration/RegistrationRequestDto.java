package hhplus.lecture.interfaces.dto.registratration;

import hhplus.lecture.domain.model.RegistrationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자가 특강 신청할 때 사용할 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequestDto {

    private String userCode; // 신청자의 사용자 고유 코드
    private String lectureItemCode; // 신청하려는 강의 일정 고유 코드
    private RegistrationStatus status = RegistrationStatus.REFUSAL; // 신청 상태 (기본값으로 REFUSAL 설정)

    public RegistrationRequestDto(String userCode, String lectureItemCode, RegistrationStatus status) {
        this.userCode = userCode;
        this.lectureItemCode = lectureItemCode;
        this.status = status;
    }
}
