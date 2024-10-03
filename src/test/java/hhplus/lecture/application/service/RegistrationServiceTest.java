package hhplus.lecture.application.service;

import hhplus.lecture.domain.model.LectureItem;
import hhplus.lecture.domain.model.RegistrationStatus;
import hhplus.lecture.infrastructure.repository.LectureItemRepository;
import hhplus.lecture.infrastructure.repository.RegistrationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        LectureItem lectureItem = new LectureItem(lectureItemCode,30,30);
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
        LectureItem lectureItem = new LectureItem(lectureItemCode,30,20);
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
}
