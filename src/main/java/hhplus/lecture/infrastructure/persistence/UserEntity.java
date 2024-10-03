package hhplus.lecture.infrastructure.persistence;

import hhplus.lecture.domain.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자동 증가하는 ID (기본 키)

    @Column(nullable = false, unique = true, length = 8)
    private String userCode; // 사용자 고유 코드 (예: 학번)

    @Column(nullable = false, length = 20)
    private String userName; // 사용자 이름

    public UserEntity(String userCode, String userName) {
        this.userCode = userCode;
        this.userName = userName;
    }

    public User toDomain() {
        return new User(userCode, userName);
    }

    public static UserEntity fromDomain(User user) {
        return new UserEntity(
                user.getUserCode(),
                user.getUserName()
        );
    }
}
