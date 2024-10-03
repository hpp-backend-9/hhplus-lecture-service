package hhplus.lecture.application.service;

import hhplus.lecture.domain.model.Lecture;
import hhplus.lecture.infrastructure.persistence.InstructorEntity;
import hhplus.lecture.infrastructure.persistence.LectureItemEntity;
import hhplus.lecture.infrastructure.repository.InstructorRepository;
import hhplus.lecture.infrastructure.repository.LectureItemRepository;
import hhplus.lecture.infrastructure.repository.LectureRepository;
import hhplus.lecture.infrastructure.persistence.LectureEntity;
import hhplus.lecture.interfaces.dto.lecture.LectureDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    void 존재하지_않는_특강을_조회하면_예외_발생(){

        // given : 존재하지 않는 특강 세팅
        String lectureCode = "123ab";

        // when : 특강 조회
        when(lectureRepository.findByLectureCode(lectureCode)).thenReturn(null);

        // then : 특강 조회 시 NoSuchElementException 발생
        assertThatThrownBy(() -> lectureService.getLecture(lectureCode))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("특강을 찾을 수 없습니다.");
    }

    @Test
    void 특강을_조회하면_특강_정보를_반환한다(){
        // given : 강의 정보 세팅
        String lectureCode = "LE001";
        LectureEntity lectureEntity = new LectureEntity(lectureCode, "Java", "IN001");
        when(lectureRepository.findByLectureCode(lectureCode)).thenReturn(lectureEntity);

        // when : 특강 조회
        Lecture result = lectureService.getLecture(lectureCode);

        // then : 조회된 특강 정보가 원하는 특강 정보와 일치하는지 확인
        assertThat(result).isNotNull();
        assertThat(result.getLectureCode()).isEqualTo(lectureCode);
        assertThat(result.getLectureName()).isEqualTo("Java");
        assertThat(result.getInstructorCode()).isEqualTo("IN001");
    }

    @Test
    void 모든_강의_목록_조회() {
        // given : 특강 목록 생성
        LectureEntity lecture1 = new LectureEntity("LE001", "Java", "IN001");
        LectureEntity lecture2 = new LectureEntity("LE002", "Spring Boot", "IN002");

        List<LectureEntity> lectureEntities = Arrays.asList(lecture1, lecture2);
        when(lectureRepository.findAll()).thenReturn(lectureEntities);

        // LectureItemEntity
        LectureItemEntity lectureItem1 = new LectureItemEntity("LE001", "LE001", LocalDate.now(), 30, 0);
        LectureItemEntity lectureItem2 = new LectureItemEntity("LE002", "LE001", LocalDate.now(), 30, 0);
        when(lectureItemRepository.findByLectureItemCode("LE001")).thenReturn(lectureItem1);
        when(lectureItemRepository.findByLectureItemCode("LE002")).thenReturn(lectureItem2);

        // 강사 정보 설정
        InstructorEntity instructor1 = new InstructorEntity("IN001", "이석범");
        InstructorEntity instructor2 = new InstructorEntity("IN002", "렌");
        when(instructorRepository.findByInstructorCode("IN001")).thenReturn(instructor1);
        when(instructorRepository.findByInstructorCode("IN002")).thenReturn(instructor2);

        // when : 모든 강의 목록 조회
        List<LectureDto> result = lectureService.getAllLectures();

        // then : 조회된 강의 목록이 예상한 강의 목록과 일치하는지 확인
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getLectureCode()).isEqualTo("LE001");
        assertThat(result.get(0).getInstructorName()).isEqualTo("이석범");
        assertThat(result.get(1).getLectureCode()).isEqualTo("LE002");
        assertThat(result.get(1).getInstructorName()).isEqualTo("렌");
    }

    @Test
    void 날짜별_현재_신청가능한_특강_목록_조회(){
        // given : 특정 날짜 설정, 강의 목록 세팅
        LocalDate targetDate = LocalDate.of(2024,10,3);

        // 특강 목록
        LectureItemEntity lectureItem1 = new LectureItemEntity("LE001", "LE001",targetDate, 30, 1);
        LectureItemEntity lectureItem2 = new LectureItemEntity("LE002", "LE002",targetDate, 30, 30);

        when(lectureItemRepository.findAllByLectureDate(targetDate)).thenReturn(List.of(lectureItem1, lectureItem2));

        // 특강 상세 설정
        when(lectureRepository.findByLectureCode("LE001")).thenReturn(new LectureEntity("LE001", "JAVA", "IN001"));

        // 강사 설정
        when(instructorRepository.findByInstructorCode("IN001")).thenReturn(new InstructorEntity("IN001", "이석범"));
        
        // when : 특강 조회
        List<LectureDto> availableLectures = lectureService.getAvailableLecturesByDate(targetDate);

        // then : 신청 가능한 강의 목록 확인
        assertThat(availableLectures).hasSize(1);
        assertThat(availableLectures).extracting("lectureItemCode").containsExactly("LE001");
    }
}
