package hhplus.lecture.application.service;

import hhplus.lecture.domain.*;
import hhplus.lecture.domain.model.RegistrationStatus;
import hhplus.lecture.infrastructure.repository.*;
import hhplus.lecture.interfaces.dto.user.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UsersRepository userRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private LectureItemRepository lectureItemRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private UserService userService;

    private final String userCode = "UC001";
    private final String lectureCode1 = "LE001";
    private final String lectureCode2 = "LE002";

    @Test
    void 존재하지_않는_사용자_조회_시_NoSearchElementException_발생() {
        // given : 존재하지 않는 사용자 세팅
        when(userRepository.findByUserCode(userCode)).thenReturn(null);

        // when, then : 사용자가 존재하지 않으므로 Exception 발생
        assertThatThrownBy(() -> userService.getUserAndRegisteredLectures(userCode))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("사용자를 찾을 수 없습니다.");
    }

    @Test
    void 사용자_조회_시_사용자_정보와_신청한_특강_목록_출력() {
        // given : 사용자 정보 + 신청한 특강 목록 설정
        UserEntity user = new UserEntity(userCode, "김지혜");
        when(userRepository.findByUserCode(userCode)).thenReturn(user);

        // 강의 신청 내역 세팅
        setupRegistrationMocks();

        // when : 사용자 조회 시 사용자 정보를 가지고 옴(강의 신청 목록 포함)
        UserResponseDto userDetail = userService.getUserAndRegisteredLectures(userCode);

        // then: 사용자 정보와 신청한 강의 목록 반환 확인
        assertThat(userDetail.getUser()).isEqualTo(user);
        assertThat(userDetail.getRegisteredLectures()).hasSize(2);
        assertThat(userDetail.getRegisteredLectures())
                .extracting("lectureCode", "instructorName")
                .containsExactlyInAnyOrder(
                        tuple(lectureCode1, "이석범"),
                        tuple(lectureCode2, "렌")
                );
    }

    @Test
    void 사용자_조회_시_신청한_특강이_없는_경우_빈_목록_반환() {
        // given : 사용자 정보 설정
        UserEntity user = new UserEntity(userCode, "김지혜");
        when(userRepository.findByUserCode(userCode)).thenReturn(user);
        when(registrationRepository.findByUserCode(userCode)).thenReturn(Collections.emptyList());

        // when : 사용자 조회(사용자 정보 + 강의 신청 정보)
        UserResponseDto userDetail = userService.getUserAndRegisteredLectures(userCode);

        // then : 사용자 정보 조회, 신청한 강의 목록 비어 있음 확인
        assertThat(userDetail.getUser()).isEqualTo(user);
        assertThat(userDetail.getRegisteredLectures()).isEmpty();
    }

    private void setupRegistrationMocks() {
        List<RegistrationEntity> registrations = List.of(
                new RegistrationEntity(userCode, lectureCode1, RegistrationStatus.APPROVAL, LocalDateTime.parse("2024-09-30T09:00:50")),
                new RegistrationEntity(userCode, lectureCode2, RegistrationStatus.APPROVAL, LocalDateTime.parse("2024-10-01T09:00:50"))
        );
        when(registrationRepository.findByUserCode(userCode)).thenReturn(registrations);

        // 강의 상세 설정
        setupLectureMocks();
    }

    private void setupLectureMocks() {
        LectureItemEntity lectureItem1 = new LectureItemEntity(lectureCode1, lectureCode1, LocalDate.now(), 30, 0);
        LectureItemEntity lectureItem2 = new LectureItemEntity(lectureCode2, lectureCode2, LocalDate.now(), 30, 0);
        when(lectureItemRepository.findByLectureItemCode(lectureCode1)).thenReturn(lectureItem1);
        when(lectureItemRepository.findByLectureItemCode(lectureCode2)).thenReturn(lectureItem2);

        // 강의 상세 설정
        when(lectureRepository.findByLectureCode(lectureCode1)).thenReturn(new LectureEntity(lectureCode1, "JAVA", "IN001"));
        when(lectureRepository.findByLectureCode(lectureCode2)).thenReturn(new LectureEntity(lectureCode2, "Spring Boot", "IN002"));

        // 강사 정보 설정
        when(instructorRepository.findByInstructorCode("IN001")).thenReturn(new InstructorEntity("IN001", "이석범"));
        when(instructorRepository.findByInstructorCode("IN002")).thenReturn(new InstructorEntity("IN002", "렌"));
    }
}