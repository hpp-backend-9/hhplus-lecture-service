package hhplus.lecture.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    private Long userId; // 고유 식별자
    private String userCode; // 사용자 고유 코드 (학번 등)
    private String userName; // 사용자 이름

    public User(String userCode, String userName) {
        this.userCode = userCode;
        this.userName = userName;
    }
}
