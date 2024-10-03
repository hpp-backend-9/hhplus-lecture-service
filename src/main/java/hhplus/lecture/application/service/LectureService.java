package hhplus.lecture.application.service;

import hhplus.lecture.domain.model.Lecture;
import hhplus.lecture.infrastructure.persistence.LectureItemEntity;
import hhplus.lecture.infrastructure.repository.InstructorRepository;
import hhplus.lecture.infrastructure.repository.LectureItemRepository;
import hhplus.lecture.infrastructure.repository.LectureRepository;
import hhplus.lecture.infrastructure.persistence.LectureEntity;
import hhplus.lecture.interfaces.dto.lecture.LectureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LectureService {

    private final LectureRepository lecturesRepository;
    private final LectureItemRepository lectureItemRepository;
    private final InstructorRepository instructorRepository;

    // 특강 정보 조회
    public Lecture getLecture(String lectureCode) {
        LectureEntity entity = lecturesRepository.findByLectureCode(lectureCode);
        if(entity == null) {
            throw new NoSuchElementException("특강을 찾을 수 없습니다.");
        }

        return entity.toDomain();
    }

    // 모든 특강 조회
    public List<LectureDto> getAllLectures() {
        List<LectureEntity> entities = lecturesRepository.findAll();
        return entities.stream()
                .map(entity -> {
                    LectureItemEntity lectureItem = lectureItemRepository.findByLectureItemCode(entity.getLectureCode());
                    String instructorName = instructorRepository.findByInstructorCode(entity.getInstructorCode()).getInstructorName();
                    return LectureDto.fromEntity(entity, lectureItem, instructorName); // 강사명을 추가
                })
                .collect(Collectors.toList());
    }

    // 생성자
    public LectureService(LectureRepository lecturesRepository, LectureItemRepository lectureItemRepository, InstructorRepository instructorRepository) {
        this.lecturesRepository = lecturesRepository;
        this.lectureItemRepository = lectureItemRepository;
        this.instructorRepository = instructorRepository;
    }
}
