package org.sopt.week1;

import org.sopt.week1.DiaryRepository;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    // 모든 일기 가져오기
    public List<Diary> getAllDiaries() {
        return diaryRepository.findAll(); // 모든 일기 목록 반환
    }

    // 새로운 일기 추가
    public void addDiary(String body) {
        Diary diary = new Diary(null, body);  // ID는 null로 설정 (자동 생성)
        diaryRepository.save(diary); // 저장소에 일기 저장
    }

    // 특정 일기 삭제
    public void deleteDiary(long id) {
        diaryRepository.delete(id); // 특정 ID의 일기 삭제
    }

    // 특정 일기 수정
    public void updateDiary(long id, String body) {
        diaryRepository.update(id, body); // ID와 새로운 내용으로 일기 수정
    }
}
