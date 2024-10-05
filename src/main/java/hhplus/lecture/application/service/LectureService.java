package hhplus.lecture.application.service;

import hhplus.lecture.domain.LectureItemEntity;
import hhplus.lecture.infrastructure.repository.InstructorRepository;
import hhplus.lecture.infrastructure.repository.LectureItemRepository;
import hhplus.lecture.infrastructure.repository.LectureRepository;
import hhplus.lecture.domain.LectureEntity;
import hhplus.lecture.interfaces.dto.lecture.LectureDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureItemRepository lectureItemRepository;
    private final InstructorRepository instructorRepository;

    // 특강 정보 조회
    public LectureEntity getLecture(String lectureCode) {
        LectureEntity entity = lectureRepository.findByLectureCode(lectureCode);
        if(entity == null) {
            throw new NoSuchElementException("특강을 찾을 수 없습니다.");
        }

        return entity;
    }

    // 모든 특강 조회
    public List<LectureDto> getAllLectures() {
        List<LectureEntity> entities = lectureRepository.findAll();
        return entities.stream()
                .map(entity -> {
                    LectureItemEntity lectureItem = lectureItemRepository.findByLectureItemCode(entity.getLectureCode());
                    String instructorName = instructorRepository.findByInstructorCode(entity.getInstructorCode()).getInstructorName();
                    return LectureDto.fromEntity(entity, lectureItem, instructorName); // 강사명을 추가
                })
                .collect(Collectors.toList());
    }

    // 신청 가능한 강의 조회
    public List<LectureDto> getAvailableLecturesByDate(LocalDate targetDate) {

        // 해당 날짜의 강의 조회
        List<LectureItemEntity> lectureItems = lectureItemRepository.findAllByLectureDate(targetDate);

        return lectureItems.stream()
                // 정원이 차지 않은 강의 조회
                .filter(item -> item.getCurrentCapacity() < item.getMaxCapacity())
                .map(item ->{
                    LectureEntity lectureEntity = lectureRepository.findByLectureCode(item.getLectureItemCode());
                    String instructorName = instructorRepository.findByInstructorCode(lectureEntity.getInstructorCode()).getInstructorName();
                    return LectureDto.fromEntity(lectureEntity, item, instructorName);
                }).collect(Collectors.toList());
    }

    // 생성자
    public LectureService(LectureRepository lectureRepository, LectureItemRepository lectureItemRepository, InstructorRepository instructorRepository) {
        this.lectureRepository = lectureRepository;
        this.lectureItemRepository = lectureItemRepository;
        this.instructorRepository = instructorRepository;
    }
}
