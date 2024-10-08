package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.domain.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<InstructorEntity, Long> {

    // 코드번호로 강사 조회
    InstructorEntity findByInstructorCode(String instructorCode);
}
