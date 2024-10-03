package hhplus.lecture.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 강의 일정과 관련된 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureItem {

    private Long lectureItemId; // 고유 식별자
    private String lectureItemCode; // 일정 고유 코드
    private String lectureCode;
    private LocalDate lectureDate; // 강의 일정 날짜
    private int maxCapacity = 30; // 정원
    private int currentCapacity = 0; // 현재 신청자 수

    // 특강 신청 가능 여부 확인 (정원 확인)
    public LectureItem(String lectureItemCode, String lectureCode, LocalDate lectureDate, int maxCapacity, int currentCapacity) {
        this.lectureItemCode = lectureItemCode;
        this.lectureCode = lectureCode;
        this.lectureDate = lectureDate;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
    }

    // 신청 인원 증가 (정원 확인)
    public boolean addRegistration() {
        if (currentCapacity < maxCapacity) {
            currentCapacity++;
            return true;
        } else {
            return false; // 정원이 다 찬 경우 신청 실패
        }
    }
}