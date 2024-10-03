package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.infrastructure.persistence.LectureItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LectureItemRepository extends JpaRepository<LectureItemEntity, Long> {

    // 특강 정보
    LectureItemEntity findByLectureItemCode(String lectureItemCode);

    // 신청 가능한 특강 목록
    List<LectureItemEntity>  findAllByLectureDate(LocalDate targetDate);
}
