package hhplus.lecture.application.service;

import hhplus.lecture.domain.model.Lecture;
import hhplus.lecture.infrastructure.repository.LectureRepository;
import hhplus.lecture.infrastructure.persistence.LectureEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LectureService {

    private final LectureRepository lecturesRepository;

    // 특강 정보 조회
    public Lecture getLecture(String lectureCode) {
        LectureEntity entity = lecturesRepository.findByLectureCode(lectureCode);
        if(entity == null) {
            throw new NoSuchElementException("특강을 찾을 수 없습니다.");
        }

        return entity.toDomain();
    }

    // 모든 강의 조회
    public List<Lecture> getAllLectures() {
        List<LectureEntity> entities = lecturesRepository.findAll();
        return entities.stream()
                .map(LectureEntity::toDomain)  // LectureEntity를 Lecture로 변환
                .collect(Collectors.toList());
    }

    // 생성자
    public LectureService(LectureRepository lecturesRepository) {
        this.lecturesRepository = lecturesRepository;
    }
}
