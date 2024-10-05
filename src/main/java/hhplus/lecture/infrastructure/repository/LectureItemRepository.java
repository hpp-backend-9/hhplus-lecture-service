package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.domain.LectureItemEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDate;
import java.util.List;

public interface LectureItemRepository extends JpaRepository<LectureItemEntity, Long> {

    // 특강 정보
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    LectureItemEntity findByLectureItemCode(String lectureItemCode);

    List<LectureItemEntity> findAllByLectureDate(LocalDate targetDate);
}
