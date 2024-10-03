package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.domain.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    // 코드번호로 강사 조회
    Instructor findByInstructorCode(String instructorCode);
}
