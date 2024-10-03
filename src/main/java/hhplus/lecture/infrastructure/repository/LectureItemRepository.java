package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.infrastructure.persistence.LectureItemEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDate;
import java.util.List;

public interface LectureItemRepository extends JpaRepository<LectureItemEntity, String> {

    // 특강 정보
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    LectureItemEntity findByLectureItemCode(String lectureItemCode);

    List<LectureItemEntity> findAllByLectureDate(LocalDate targetDate);
}
