package hhplus.lecture.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 강의 일정 정보
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lecture_item")
public class LectureItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureItemId; // 자동 증가하는 ID (기본 키)

    @Column(nullable = false, unique = true, length = 10)
    private String lectureItemCode;

    @Column(nullable = false)
    private String lectureCode;

    @Column(nullable = false)
    private LocalDate lectureDate; // 강의 일정 날짜

    @Column(nullable = false)
    private int maxCapacity = 30; // 정원

    @Column(nullable = false)
    private int currentCapacity = 0; // 현재 신청자 수

    public LectureItemEntity(String lectureItemCode, String lectureCode, LocalDate lectureDate, int maxCapacity, int currentCapacity) {
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
