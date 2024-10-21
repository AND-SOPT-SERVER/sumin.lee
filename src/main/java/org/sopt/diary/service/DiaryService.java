package org.sopt.diary.service;

import org.sopt.diary.api.DetailedDiaryResponse;
import org.sopt.diary.api.DiaryListResponse;
import org.sopt.diary.api.DiaryRequest;
import org.sopt.diary.api.DiaryResponse;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

public List<DiaryResponse>getDiaryList(){
    // 모든 일기 데이터를 가져옴 (정렬 없이)
    List <DiaryEntity> allDiaries = diaryRepository.findAll(); //allDiaries: 데이터베이스에서 가져온 일기 정보 (클라이언트에게 전달할 때는 DiaryResponse)

    // 일기의 생성일 기준으로 내림차순 정렬

    allDiaries.sort((d1,d2)-> d2.getCreatedAt().compareTo(d1.getCreatedAt()));

    //최신 10개만 추출
    List<DiaryResponse> diaryResponses = new ArrayList<>();
    for(int i=0; i< Math.min(10,allDiaries.size());i++){
        DiaryEntity diary = allDiaries.get(i);
        diaryResponses.add(new DiaryResponse(diary.getId(),diary.getTitle())); //클라이언트에게 전달하기 위해 diary를 DiaryResponse로,,,
    }
        return diaryResponses;
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

