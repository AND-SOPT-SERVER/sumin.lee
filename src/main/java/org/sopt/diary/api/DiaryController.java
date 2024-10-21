package org.sopt.diary.api;


import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
public class DiaryController { //클라이언트와 요청을 주고받는 역할
    private final DiaryService diaryService;


    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    //일기 작성 기능
    @PostMapping("/luckybicky/diaries")
    public ResponseEntity<SimpleDiaryResponse> writeDiary(@RequestBody DiaryRequest diaryRequest) {
        Long diaryId = diaryService.createDiary(diaryRequest);
        // 응답으로 diaryId와 성공 메시지를 포함한 DiaryResponse 객체 반환
        SimpleDiaryResponse response = new SimpleDiaryResponse(diaryId, "일기가 성공적으로 작성되었습니다."); //diaryId**와 성공 메시지를 사용하여 SimpleDiaryResponse 객체를 생성
        // 응답 반환
        return ResponseEntity.ok(response);
    }

    @GetMapping("/luckybicky/diaries")
    public ResponseEntity <List<DiaryResponse>> getDiaries(){
        List <DiaryResponse> diaries = diaryService.getDiaryList();
                return ResponseEntity.ok(diaries);

    }

    @GetMapping("/luckybicky/diaries/{diaryId}")
    public ResponseEntity<DetailedDiaryResponse> getDiaryDetail(@PathVariable Long diaryId){
        DetailedDiaryResponse detailedDiaryResponse = diaryService.getDiaryDetail(diaryId);
        return ResponseEntity.ok(detailedDiaryResponse);
    }

    @PatchMapping("/luckybicky/diaries/{diaryId}")
    public ResponseEntity<String> updateDiary(@PathVariable Long diaryId, @RequestBody DiaryRequest diaryRequest){
        diaryService.updateDiary(diaryId,diaryRequest);
        return ResponseEntity.ok("일기가 수정되었습니다");
    }

    @DeleteMapping("/luckybicky/diaries/{diaryId}")
    public ResponseEntity<String> deleteDiary(@PathVariable Long diaryId){
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.ok("일기가 삭제되었습니다");
    }
}
