package hhplus.lecture.application.service.test;

import hhplus.lecture.application.service.RegistrationService;
import hhplus.lecture.domain.LectureItemEntity;
import hhplus.lecture.infrastructure.repository.LectureItemRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class RegistrationServiceIntegrationTest {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private LectureItemRepository lectureItemRepository;

    @Test
    void 동시에_40명이_특강_신청_시_30명만_성공하도록() {
        // given: 강의 세팅
        String lectureItemCode = "LE001"; // 강의 일정 코드
        LocalDate lectureDate = LocalDate.of(2024, 10, 3);

        // 기존 강의 일정 확인
        LectureItemEntity lectureItem = lectureItemRepository.findByLectureItemCode(lectureItemCode);
        if (lectureItem == null) {
            lectureItem = new LectureItemEntity(lectureItemCode, "Java", lectureDate, 30, 0);
            lectureItemRepository.save(lectureItem);
        }

        // 40명의 신청을 위한 사용자 코드 생성
        String userCodePrefix = "UC";
        List<String> userCodes = IntStream.range(1, 41)
                .mapToObj(i -> userCodePrefix + String.format("%03d", i))
                .collect(Collectors.toList());

        // 신청 진행
        List<Boolean> results = userCodes.stream()
                .map(userCode -> registrationService.registerLecture(userCode, lectureItemCode))
                .collect(Collectors.toList());

        // then: 성공한 신청자는 30명, 실패한 신청자는 10명인지 확인
        long successCount = results.stream().filter(result -> result).count();
        long failureCount = results.size() - successCount;

        assertThat(successCount).isEqualTo(30);
        assertThat(failureCount).isEqualTo(10);
    }

    @Test
    void 동일한_유저가_같은_특강에_5번_신청할_때_1번만_성공한다() {
        // given: 강의 세팅
        String lectureItemCode = "LE001"; // 강의 일정 코드
        LocalDate lectureDate = LocalDate.of(2024, 10, 3);

        // 기존 강의 일정 확인
        LectureItemEntity lectureItem = lectureItemRepository.findByLectureItemCode(lectureItemCode);
        if (lectureItem == null) {
            lectureItem = new LectureItemEntity(lectureItemCode, "Java", lectureDate, 30, 0);
            lectureItemRepository.save(lectureItem);
        }

        // 동일한 사용자 코드 생성
        String userCode = "UC001";

        // 동일한 사용자로 5번 신청 진행
        List<Boolean> results = IntStream.range(0, 5)
                .mapToObj(i -> registrationService.registerLecture(userCode, lectureItemCode))
                .collect(Collectors.toList());

        // then: 첫 번째 신청은 성공하고 나머지 신청은 실패하는지 확인
        long successCount = results.stream().filter(result -> result).count();
        long failureCount = results.size() - successCount;

        assertThat(successCount).isEqualTo(1);
        assertThat(failureCount).isEqualTo(4);
    }

}