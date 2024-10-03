package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.infrastructure.persistence.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<LectureEntity, Long> {
    
    // 강의 조회
    LectureEntity findByLectureCode(String lectureCode);
}
