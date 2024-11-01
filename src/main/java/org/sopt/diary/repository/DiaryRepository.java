package org.sopt.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    List<DiaryEntity> findTop10ByOrderByCreatedAtDesc();
    // 제목을 기준으로 DiaryEntity를 찾는 메서드
    Optional<DiaryEntity> findByTitle(String title);
}
