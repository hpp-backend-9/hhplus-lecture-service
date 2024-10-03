package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.domain.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // 사용자의 특강 신청 정보 목록 조회
    List<Registration> findByUserCode(String userCode);
}
