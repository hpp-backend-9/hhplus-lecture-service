package hhplus.lecture.application.service;

import hhplus.lecture.infrastructure.persistence.LectureEntity;
import hhplus.lecture.infrastructure.persistence.LectureItemEntity;
import hhplus.lecture.infrastructure.persistence.RegistrationEntity;
import hhplus.lecture.infrastructure.persistence.UserEntity;
import hhplus.lecture.infrastructure.repository.*;
import hhplus.lecture.interfaces.dto.lecture.LectureDto;
import hhplus.lecture.interfaces.dto.user.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private RegistrationRepository registrationRepository;
    private LectureRepository lectureRepository;
    private LectureItemRepository lectureItemRepository;
    private LectureService lectureService;
    private InstructorRepository instructorRepository;

    // 사용자 조회 (사용자 상세정보 + 강의 신청 내역)
    public UserResponseDto getUserAndRegisteredLectures(String userCode) {
        UserEntity entity = usersRepository.findByUserCode(userCode);
        if (entity == null) {
            throw new NoSuchElementException("사용자를 찾을 수 없습니다.");
        }

        // 강의 신청 내역이 존재하는지도 확인
        List<RegistrationEntity> registrations = registrationRepository.findByUserCode(userCode);
        // 강의 내역
        List<LectureDto> registeredLectures = registrations.stream()
                .map(registration -> {
                    // lectureItemCode를 통해 LectureItemEntity를 가져옵니다.
                    LectureItemEntity lectureItem = lectureItemRepository.findByLectureItemCode(registration.getLectureItemCode());
                    // LectureEntity를 통해 LectureDto 생성
                    LectureEntity lectureEntity = lectureRepository.findByLectureCode(lectureItem.getLectureItemCode());
                    String instructorName = instructorRepository.findByInstructorCode(lectureEntity.getInstructorCode()).getInstructorName();
                    return LectureDto.fromEntity(lectureEntity, lectureItem, instructorName);
                }).collect(Collectors.toList());
        return new UserResponseDto(entity, registeredLectures);
    }

    // 생성자
    public UserService(UsersRepository usersRepository, RegistrationRepository registrationRepository, LectureService lectureService, InstructorRepository instructorRepository) {
        this.usersRepository = usersRepository;
        this.registrationRepository = registrationRepository;
        this.lectureService = lectureService;
        this.instructorRepository = instructorRepository;
    }
}
