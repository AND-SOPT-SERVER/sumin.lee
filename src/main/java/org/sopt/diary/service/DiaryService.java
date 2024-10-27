package org.sopt.diary.service;

import org.sopt.diary.api.DetailedDiaryResponse;
import org.sopt.diary.api.DiaryListResponse;
import org.sopt.diary.api.DiaryRequest;
import org.sopt.diary.api.DiaryResponse;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private LocalDateTime lastDiaryTime = null;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

   public Long createDiary(DiaryRequest diaryRequest){

        //5분 이후에 작성된건지 유효성 검사
       if (lastDiaryTime != null && lastDiaryTime.plusMinutes(5).isAfter(LocalDateTime.now())) {
           throw new IllegalArgumentException("5분 내에 일기를 작성할 수 없습니다.");
       }

       // 제목과 내용의 유효성 검사는 DiaryRequest의 매서드 이용, 중복성 검사는 서비스단에서 처리
       if (!diaryRequest.isValid()) {
           throw new IllegalArgumentException("제목은 30자, 내용은 100자를 초과할 수 없습니다.");
       }

       // 제목 중복 확인
       Optional<DiaryEntity> existingDiary = diaryRepository.findByTitle(diaryRequest.getTitle());
       if (existingDiary.isPresent()) {
           throw new IllegalArgumentException("중복된 제목의 일기가 이미 존재합니다.");
       }


       // DiaryEntity 생성 후 저장

       DiaryEntity diaryEntity = new DiaryEntity(diaryRequest.getTitle(), diaryRequest.getContent(),diaryRequest.getCategory());
       diaryRepository.save(diaryEntity);
       lastDiaryTime = LocalDateTime.now();
       // 저장된 DiaryEntity의 ID를 반환
       return diaryEntity.getId();
   }

   public void updateDiary(Long diaryId, DiaryRequest diaryRequest){
        Optional <DiaryEntity> optionalDiaryEntity = diaryRepository.findById(diaryId);
        if (!optionalDiaryEntity.isPresent()){
            throw new IllegalArgumentException("해당 ID의 일기를 찾을 수 없습니다.");
        }
       DiaryEntity diary = optionalDiaryEntity.get();

        //제목이 존재하고 30자를 넘지 않으면 수정
       if (diaryRequest.getTitle() !=null && diaryRequest.getTitle().length() <=30){
           diary.setTitle(diaryRequest.getTitle());
           diaryRepository.save(diary);
       }

       // 내용이 존재하고 100자를 넘지 않으면 수정
       if (diaryRequest.getContent() !=null && diaryRequest.getContent().length() <=100){
           diary.setContent(diaryRequest.getContent());
           diaryRepository.save(diary);
       }

   }

    public List<DiaryResponse> getDiaryList() {
        List<DiaryEntity> allDiaries = diaryRepository.findAll();
        // 글자 수가 많은 순서로 정렬 후 상위 10개 추출
        allDiaries.sort((d1, d2) -> Integer.compare(d2.getContent().length(), d1.getContent().length()));
        List<DiaryResponse> diaryResponses = new ArrayList<>();
        for (int i = 0; i < Math.min(10, allDiaries.size()); i++) {
            DiaryEntity diary = allDiaries.get(i);
            diaryResponses.add(new DiaryResponse(diary.getId(), diary.getTitle()));
        }
        return diaryResponses;
    }
    // 카테고리별 조회 메서드 추가
    public List<DiaryResponse> getDiariesByCategory(String category) {
        List<DiaryEntity> allDiaries = diaryRepository.findAll();
        List<DiaryResponse> filteredDiaries = new ArrayList<>();
        for (DiaryEntity diary : allDiaries) {
            if (diary.getCategory().equals(category)) {
                filteredDiaries.add(new DiaryResponse(diary.getId(), diary.getTitle()));
            }
        }
        return filteredDiaries;
    }
    public List<Diary> getList() {
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAll();
        final List<Diary> diaryList = new ArrayList<>();
        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(new Diary(diaryEntity.getId(), diaryEntity.getTitle()));
        }
        return diaryList;
    }


    public DetailedDiaryResponse getDiaryDetail(Long diaryId) {

        //DiaryEntity조회
        Optional<DiaryEntity> optionalDiaryEntity = diaryRepository.findById(diaryId);

        if (!optionalDiaryEntity.isPresent()) {
            throw new IllegalArgumentException("해당 ID의 일기를 찾을 수 없습니다.");
        }

        DiaryEntity diaryEntity = optionalDiaryEntity.get();

        // DiaryEntity -> DetailedDiaryResponse 변환 후 반환해야함 (클라이언트가 받는 응답 형식으로)
        return new DetailedDiaryResponse(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                diaryEntity.getContent(),
                diaryEntity.getCreatedAt()
        );
    }
public void deleteDiary(Long diaryId){
        Optional <DiaryEntity> optionalDiaryEntity = diaryRepository.findById(diaryId);

        if (!optionalDiaryEntity.isPresent()){
            throw  new IllegalArgumentException("삭제할 일기가 존재하지 않습니다");
        }
        // 존재하는 일기를 삭제
        DiaryEntity diary = optionalDiaryEntity.get();
        diaryRepository.delete(diary);


}

    }

