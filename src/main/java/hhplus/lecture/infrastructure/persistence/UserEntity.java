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
    private Long userId;

    @Column(nullable = false, unique = true, length = 8)
    private String userCode;

    @Column(nullable = false, length = 20)
    private String userName;

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
