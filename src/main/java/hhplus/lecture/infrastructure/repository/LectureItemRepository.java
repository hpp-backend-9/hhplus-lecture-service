package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.domain.model.LectureItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureItemRepository extends JpaRepository<LectureItem, Long> {

    // 특강 정보
    LectureItem findByLectureItemCode(String LectureCode);
}
