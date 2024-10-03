package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.infrastructure.persistence.LectureItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureItemRepository extends JpaRepository<LectureItemEntity, String> {

    // 특강 정보
    LectureItemEntity findByLectureItemCode(String lectureItemCode);
}
