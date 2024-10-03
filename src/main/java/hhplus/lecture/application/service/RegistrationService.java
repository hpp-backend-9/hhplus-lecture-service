package hhplus.lecture.application.service;

import hhplus.lecture.domain.model.Lecture;
import hhplus.lecture.domain.model.RegistrationStatus;
import hhplus.lecture.infrastructure.persistence.LectureItemEntity;
import hhplus.lecture.infrastructure.persistence.RegistrationEntity;
import hhplus.lecture.infrastructure.repository.LectureItemRepository;
import hhplus.lecture.infrastructure.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {

    private final LectureService lectureService;
    private final LectureItemRepository lectureItemRepository;
    private final RegistrationRepository registrationRepository;

    // 특강 정보 조회
    public Lecture lectureDetail(String lectureCode){
        return lectureService.getLecture(lectureCode);
    }

    // 특강 신청 성공 여부
    public boolean registerLecture(String userCode, String lectureCode) {

        // 기존 신청 내역 확인
        List<RegistrationEntity> existingRegistrations = registrationRepository.findByUserCode(userCode);

        boolean alreadyRegistered = existingRegistrations.stream()
                .anyMatch(registration -> registration.getLectureItemCode().equals(lectureCode));

        if (alreadyRegistered) {
            return false; // 이미 신청한 강의인 경우
        }

        LectureItemEntity lectureItem = lectureItemRepository.findByLectureItemCode(lectureCode);

        if(lectureItem == null || lectureItem.getCurrentCapacity() >= lectureItem.getMaxCapacity()){
            return false;
        }

        // 신청자수 증가
        lectureItem.addRegistration();
        // 강의 수강자에 추가
        lectureItemRepository.save(lectureItem);
        // 신청 내역에 추가
        saveRegistration(userCode, lectureCode);

        return true;
    }

    // 신청 내역 저장
    private void saveRegistration(String userCode, String lectureCde) {
        RegistrationEntity entity = new RegistrationEntity(userCode, lectureCde, RegistrationStatus.APPROVAL, LocalDateTime.now());
        registrationRepository.save(entity);
    }

    // 생성자
    public RegistrationService(LectureService lectureService, LectureItemRepository lectureItemRepository, RegistrationRepository registrationRepository) {
        this.lectureService = lectureService;
        this.lectureItemRepository = lectureItemRepository;
        this.registrationRepository = registrationRepository;
    }
}

