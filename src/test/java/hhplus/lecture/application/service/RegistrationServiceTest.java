package hhplus.lecture.application.service;

import hhplus.lecture.domain.model.RegistrationStatus;
import hhplus.lecture.infrastructure.persistence.LectureItemEntity;
import hhplus.lecture.infrastructure.persistence.RegistrationEntity;
import hhplus.lecture.infrastructure.repository.LectureItemRepository;
import hhplus.lecture.infrastructure.repository.RegistrationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private LectureItemRepository lectureItemRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void 정원이_초과된_경우_특강_신청_시_실패한다() {
        // given : 정원이 초과된 특강 세팅
        String lectureItemCode = "LE001";
        String userCode = "00001";
        LectureItemEntity lectureItem = new LectureItemEntity(lectureItemCode,LocalDate.parse("2024-10-05"),30,30);
        when(lectureItemRepository.findByLectureItemCode(lectureItemCode)).thenReturn(lectureItem);

        // when : 특강을 신청한다
        boolean result = registrationService.registerLecture(userCode, lectureItemCode);

        // then : 특강 성공 여부에서 false 처리
        assertThat(result).isFalse();
    }

    @Test
    void 정원이_초과되지_않은_특강_신청_시_성공_후_신청_내역에_저장한다(){
        // given : 정원이 초과되지 않은 특강 세팅
        String lectureItemCode = "LE001";
        String userCode = "00001";
        LectureItemEntity lectureItem = new LectureItemEntity(lectureItemCode, LocalDate.parse("2024-10-05"),30,20);
        when(lectureItemRepository.findByLectureItemCode(lectureItemCode)).thenReturn(lectureItem);

        // when : 특강을 신청한다
        boolean result = registrationService.registerLecture(userCode, lectureItemCode);

        // then : 특강 신청 성공
        assertThat(result).isTrue();

        // 신청 특강에 저장되는지 확인
        verify(registrationRepository).save(argThat(registration ->
                registration.getUserCode().equals(userCode) &&
                        registration.getLectureItemCode().equals(lectureItemCode) &&
                        registration.getStatus() == RegistrationStatus.APPROVAL
        ));
    }

    @Test
    void 동일한_신청자는_동일한_강의에_대해_한_번의_수강_신청만_성공해야한다() {
        // given: 특정 강의와 사용자 설정
        String lectureItemCode = "LE001";
        String userCode = "00001";

        // 이미 신청한 등록 정보 설정
        RegistrationEntity existingRegistration = new RegistrationEntity(userCode, lectureItemCode, RegistrationStatus.APPROVAL, LocalDateTime.now());
        List<RegistrationEntity> existingRegistrations = List.of(existingRegistration);

        // mock 설정
        when(registrationRepository.findByUserCode(userCode)).thenReturn(existingRegistrations);

        // when: 동일한 강의에 대해 다시 신청 시도
        boolean result = registrationService.registerLecture(userCode, lectureItemCode);

        // then: 신청 실패 확인
        assertThat(result).isFalse();

        // verify: repository의 findByUserCode 메서드가 호출되었는지 확인
        verify(registrationRepository).findByUserCode(userCode);
    }

}
