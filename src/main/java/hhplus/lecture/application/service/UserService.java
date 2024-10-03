package hhplus.lecture.application.service;

import hhplus.lecture.domain.model.Lecture;
import hhplus.lecture.infrastructure.persistence.RegistrationEntity;
import hhplus.lecture.infrastructure.persistence.UserEntity;
import hhplus.lecture.infrastructure.repository.RegistrationRepository;
import hhplus.lecture.infrastructure.repository.UsersRepository;
import hhplus.lecture.interfaces.dto.lecture.LectureDto;
import hhplus.lecture.interfaces.dto.user.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private RegistrationRepository registrationRepository;
    private LectureService lectureService;

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
                    Lecture lecture = lectureService.getLecture(registration.getLectureItemCode());
                    return new LectureDto(lecture.getLectureCode(), lecture.getLectureName(), lecture.getInstructorName());
                }).collect(Collectors.toList());

        return new UserResponseDto(entity, registeredLectures);
    }

    // 생성자
    public UserService(UsersRepository usersRepository, RegistrationRepository registrationRepository, LectureService lectureService) {
        this.usersRepository = usersRepository;
        this.registrationRepository = registrationRepository;
        this.lectureService = lectureService;
    }
}
