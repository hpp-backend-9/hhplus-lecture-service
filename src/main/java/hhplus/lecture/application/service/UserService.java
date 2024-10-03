package hhplus.lecture.application.service;

import hhplus.lecture.domain.LectureEntity;
import hhplus.lecture.domain.LectureItemEntity;
import hhplus.lecture.domain.RegistrationEntity;
import hhplus.lecture.domain.UserEntity;
import hhplus.lecture.infrastructure.repository.*;
import hhplus.lecture.interfaces.dto.lecture.LectureDto;
import hhplus.lecture.interfaces.dto.user.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final RegistrationRepository registrationRepository;
    private final LectureRepository lectureRepository;
    private final LectureItemRepository lectureItemRepository;
    private final InstructorRepository instructorRepository;

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
                    LectureItemEntity lectureItem = lectureItemRepository.findByLectureItemCode(registration.getLectureItemCode());
                    LectureEntity lectureEntity = lectureRepository.findByLectureCode(lectureItem.getLectureItemCode());
                    String instructorName = instructorRepository.findByInstructorCode(lectureEntity.getInstructorCode()).getInstructorName();
                    return LectureDto.fromEntity(lectureEntity, lectureItem, instructorName);
                }).collect(Collectors.toList());
        return new UserResponseDto(entity, registeredLectures);
    }

    // 생성자
    public UserService(UsersRepository usersRepository, RegistrationRepository registrationRepository, InstructorRepository instructorRepository, LectureRepository lectureRepository, LectureItemRepository lectureItemRepository) {
        this.usersRepository = usersRepository;
        this.registrationRepository = registrationRepository;
        this.instructorRepository = instructorRepository;
        this.lectureRepository = lectureRepository;
        this.lectureItemRepository = lectureItemRepository;
    }
}
