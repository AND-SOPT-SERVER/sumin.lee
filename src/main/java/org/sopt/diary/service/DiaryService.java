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
        validateDiaryInterval();
       // 제목과 내용의 유효성 검사는 DiaryRequest의 매서드 이용, 중복성 검사는 서비스단에서 처리
       if (!diaryRequest.isValid()) {
           throw new IllegalArgumentException("제목은 30자, 내용은 100자를 초과할 수 없습니다.");
       }
        validateDuplicateTitle(diaryRequest.getTitle());


       //ifPresent 메서드를 사용하여, Optional 값이 존재할 경우 람다식으로 지정한 예외를 던지는 방식으로 수정.
       diaryRepository.findByTitle(diaryRequest.getTitle()).ifPresent(diary -> { //diary는 Optional 안의 값(존재할 경우의 DiaryEntity)을 가리킴 // 값이 존재할 때만 IllegalArgumentException이 발생
           throw new IllegalArgumentException("중복된 제목의 일기가 이미 존재합니다.");
       });


       // DiaryEntity 생성 후 저장

       DiaryEntity diaryEntity = new DiaryEntity(diaryRequest.getTitle(), diaryRequest.getContent(),diaryRequest.getCategory());
       diaryRepository.save(diaryEntity);
       lastDiaryTime = LocalDateTime.now();
       // 저장된 DiaryEntity의 ID를 반환
       return diaryEntity.getId();
   }


    //원래 createDiary내부에서 처리 되는 로직이었지만, 책임 분리하기 위해 함수 생성
    private void validateDiaryInterval() {
        if (lastDiaryTime != null && lastDiaryTime.plusMinutes(5).isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("5분 내에 일기를 작성할 수 없습니다.");
        }
    }
    //원래 createDiary내부에서 처리 되는 로직이었지만, 책임 분리하기 위해 함수 생성
    private void validateDuplicateTitle(String title) {
        diaryRepository.findByTitle(title).ifPresent(diary -> { //수정된 코드
            throw new IllegalArgumentException("중복된 제목의 일기가 이미 존재합니다.");
        });
    }
// 제목 중복 확인(이전 코드)
//       Optional<DiaryEntity> existingDiary = diaryRepository.findByTitle(diaryRequest.getTitle());
//       if (existingDiary.isPresent()) {
//           throw new IllegalArgumentException("중복된 제목의 일기가 이미 존재합니다.");
//       }

   public void updateDiary(Long diaryId, DiaryRequest diaryRequest){
        Optional <DiaryEntity> optionalDiaryEntity = diaryRepository.findById(diaryId);
        if (!optionalDiaryEntity.isPresent()){
            throw new IllegalArgumentException("해당 ID의 일기를 찾을 수 없습니다.");
        }
       DiaryEntity diary = optionalDiaryEntity.get();

        //제목이 존재하고 30자를 넘지 않으면 수정
       // 제목과 내용 업데이트 전 유효성 검사
       updateTitleIfValid(diary, diaryRequest.getTitle());
       updateContentIfValid(diary, diaryRequest.getContent());

       diaryRepository.save(diary);

   }

    //원래 updateDiary내부에서 처리 되는 로직이었지만, 책임 분리하기 위해 함수 생성
   private  void updateTitleIfValid(DiaryEntity diary, String title){
       if (title != null && title.length() <= 30) {
           diary.setTitle(title);
       }
   }
    //원래 updateDiary내부에서 처리 되는 로직이었지만, 책임 분리하기 위해 함수 생성
   private void updateContentIfValid(DiaryEntity diary, String content){
        if (content !=null && content.length()<=100){
            diary.setContent(content);
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
//public void deleteDiary(Long diaryId){
//        Optional <DiaryEntity> optionalDiaryEntity = diaryRepository.findById(diaryId);
//
//        if (!optionalDiaryEntity.isPresent()){
//            throw  new IllegalArgumentException("삭제할 일기가 존재하지 않습니다");
//        }
//        // 존재하는 일기를 삭제
//        DiaryEntity diary = optionalDiaryEntity.get();
//        diaryRepository.delete(diary);
//
//
//}

    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId); //deleteById 메서드는 ID를 통해 엔티티를 바로 삭제
    }

    }

