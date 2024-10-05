package hhplus.lecture.application.service;

import hhplus.lecture.domain.model.RegistrationStatus;
import hhplus.lecture.domain.LectureItemEntity;
import hhplus.lecture.domain.RegistrationEntity;
import hhplus.lecture.infrastructure.repository.LectureItemRepository;
import hhplus.lecture.infrastructure.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RegistrationService {

    private final LectureItemRepository lectureItemRepository;
    private final RegistrationRepository registrationRepository;

    // 특강 신청 성공 여부
    public boolean registerLecture(String userCode, String lectureItemCode) {
        // 기존 신청 내역 확인
        if (isAlreadyRegistered(userCode, lectureItemCode)) {
            return false; // 이미 신청한 경우
        }

        // 강의 일정 조회
        LectureItemEntity lectureItem = lectureItemRepository.findByLectureItemCode(lectureItemCode);
        if (lectureItem == null || !canRegister(lectureItem)) {
            return false; // 특강 일정이 없거나 정원이 가득 찼다면
        }

        // 신청자 수 증가
        lectureItem.addRegistration();
        lectureItemRepository.save(lectureItem); // 강의 일정 업데이트
        saveRegistration(userCode, lectureItemCode); // 신청 내역에 추가

        return true;
    }

    // 기존 신청 내역 확인
    private boolean isAlreadyRegistered(String userCode, String lectureItemCode) {
        List<RegistrationEntity> existingRegistrations = registrationRepository.findByUserCode(userCode);
        return existingRegistrations.stream()
                .anyMatch(registration -> registration.getLectureItemCode().equals(lectureItemCode));
    }

    // 등록 가능 여부
    private boolean canRegister(LectureItemEntity lectureItem) {
        return lectureItem.getCurrentCapacity() < lectureItem.getMaxCapacity();
    }

    // 신청 내역 저장
    private void saveRegistration(String userCode, String lectureCde) {
        RegistrationEntity entity = new RegistrationEntity(userCode, lectureCde, RegistrationStatus.APPROVAL, LocalDateTime.now());
        registrationRepository.save(entity);
    }

    // 생성자
    public RegistrationService(LectureItemRepository lectureItemRepository, RegistrationRepository registrationRepository) {
        this.lectureItemRepository = lectureItemRepository;
        this.registrationRepository = registrationRepository;
    }
}

