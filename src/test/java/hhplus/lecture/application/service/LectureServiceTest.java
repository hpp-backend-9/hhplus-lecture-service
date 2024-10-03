package hhplus.lecture.application.service;

import hhplus.lecture.domain.InstructorEntity;
import hhplus.lecture.domain.LectureItemEntity;
import hhplus.lecture.infrastructure.repository.InstructorRepository;
import hhplus.lecture.infrastructure.repository.LectureItemRepository;
import hhplus.lecture.infrastructure.repository.LectureRepository;
import hhplus.lecture.domain.LectureEntity;
import hhplus.lecture.interfaces.dto.lecture.LectureDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureItemRepository lectureItemRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private LectureService lectureService;

    private LectureEntity lectureEntity;
    private LectureItemEntity lectureItemEntity;

    @BeforeEach
    void setUp() {
        lectureEntity = new LectureEntity("LE001", "Java", "IN001");
        lectureItemEntity = new LectureItemEntity("LE001", "LE001", LocalDate.now(), 30, 0);
    }

    @Test
    void 존재하지_않는_특강을_조회하면_예외_발생() {
        // given: 존재하지 않는 특강 세팅
        String lectureCode = "123ab";
        when(lectureRepository.findByLectureCode(lectureCode)).thenReturn(null);

        // when: 특강 조회
        // then: NoSuchElementException 발생
        assertThatThrownBy(() -> lectureService.getLecture(lectureCode))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("특강을 찾을 수 없습니다.");
    }

    @Test
    void 특강을_조회하면_특강_정보를_반환한다() {
        // given: 강의 정보 세팅
        when(lectureRepository.findByLectureCode("LE001")).thenReturn(lectureEntity);

        // when: 특강 조회
        LectureEntity result = lectureService.getLecture("LE001");

        // then: 조회된 특강 정보가 원하는 특강 정보와 일치하는지 확인
        assertThat(result)
                .isNotNull()
                .extracting(LectureEntity::getLectureCode, LectureEntity::getLectureName, LectureEntity::getInstructorCode)
                .containsExactly("LE001", "Java", "IN001");
    }

    @Test
    void 모든_강의_목록_조회() {
        // given: 특강 목록 생성
        List<LectureEntity> lectureEntities = Arrays.asList(lectureEntity, new LectureEntity("LE002", "Spring Boot", "IN002"));
        when(lectureRepository.findAll()).thenReturn(lectureEntities);
        when(lectureItemRepository.findByLectureItemCode("LE001")).thenReturn(lectureItemEntity);
        when(lectureItemRepository.findByLectureItemCode("LE002")).thenReturn(new LectureItemEntity("LE002", "LE002", LocalDate.now(), 30, 0));
        when(instructorRepository.findByInstructorCode("IN001")).thenReturn(new InstructorEntity("IN001", "이석범"));
        when(instructorRepository.findByInstructorCode("IN002")).thenReturn(new InstructorEntity("IN002", "렌"));

        // when: 모든 강의 목록 조회
        List<LectureDto> result = lectureService.getAllLectures();

        // then: 조회된 강의 목록이 예상한 강의 목록과 일치하는지 확인
        assertThat(result).hasSize(2);
        assertThat(result).extracting(LectureDto::getLectureCode, LectureDto::getInstructorName)
                .containsExactlyInAnyOrder(
                        tuple("LE001", "이석범"),
                        tuple("LE002", "렌")
                );
    }

    @Test
    void 날짜별_현재_신청가능한_특강_목록_조회() {
        // given: 특정 날짜 설정, 강의 목록 세팅
        LocalDate targetDate = LocalDate.of(2024, 10, 3);
        LectureItemEntity lectureItem = new LectureItemEntity("LE001", "LE001", targetDate, 30, 1);
        when(lectureItemRepository.findAllByLectureDate(targetDate)).thenReturn(List.of(lectureItem));
        when(lectureRepository.findByLectureCode("LE001")).thenReturn(lectureEntity);
        when(instructorRepository.findByInstructorCode("IN001")).thenReturn(new InstructorEntity("IN001", "이석범"));

        // when: 특강 조회
        List<LectureDto> availableLectures = lectureService.getAvailableLecturesByDate(targetDate);

        // then: 신청 가능한 강의 목록 확인
        assertThat(availableLectures).hasSize(1)
                .extracting(LectureDto::getLectureItemCode)
                .containsExactly("LE001");
    }
}
