package org.sopt.diary.service;

import org.sopt.diary.api.DiaryRequest;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

   public Long createDiary(DiaryRequest diaryRequest){
       // 제목과 내용의 유효성 검사
       if (!diaryRequest.isValid()) {
           throw new IllegalArgumentException("제목은 30자, 내용은 100자를 초과할 수 없습니다.");
       }
       // DiaryEntity 생성 후 저장

       DiaryEntity diaryEntity = new DiaryEntity(diaryRequest.getTitle(), diaryRequest.getContent());
       diaryRepository.save(diaryEntity);
       // 저장된 DiaryEntity의 ID를 반환
       return diaryEntity.getId();
   }

    public List<Diary> getList() {
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAll();
        final List<Diary> diaryList = new ArrayList<>();
        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(new Diary(diaryEntity.getId(), diaryEntity.getTitle()));
        }
        return diaryList;
    }
}
