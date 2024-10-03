package hhplus.lecture.infrastructure.repository;

import hhplus.lecture.infrastructure.persistence.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    
    // 사용자 조회
    UserEntity findByUserCode(String userCode);
}
