package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    
    // 사용자 조회
    User findByUserCode(String userCode);
}
