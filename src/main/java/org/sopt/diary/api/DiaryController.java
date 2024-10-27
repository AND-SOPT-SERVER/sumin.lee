package org.sopt.diary.api;

import org.sopt.diary.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 일기 작성 기능
    @PostMapping("/luckybicky/diaries")
    public ResponseEntity<SimpleDiaryResponse> writeDiary(@RequestBody DiaryRequest diaryRequest) {
        try {
            Long diaryId = diaryService.createDiary(diaryRequest);
            SimpleDiaryResponse response = new SimpleDiaryResponse(diaryId, "일기가 성공적으로 작성되었습니다.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            if (e.getMessage().contains("5분 내에 일기를 작성할 수 없습니다.")) {
                status = HttpStatus.TOO_MANY_REQUESTS; // 429 상태 코드
            } else if (e.getMessage().contains("중복된 제목의 일기가 이미 존재합니다.")) {
                status = HttpStatus.CONFLICT; // 409 상태 코드
            }
            return ResponseEntity.status(status).body(new SimpleDiaryResponse(null, e.getMessage()));
        }
    }

    // 일기 목록 조회
    @GetMapping("/luckybicky/diaries")
    public ResponseEntity<List<DiaryResponse>> getDiaries() {
        try {
            List<DiaryResponse> diaries = diaryService.getDiaryList();
            return ResponseEntity.ok(diaries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 카테고리별 일기 조회
    @GetMapping("/luckybicky/diaries/category/{category}")
    public ResponseEntity<List<DiaryResponse>> getDiariesByCategory(@PathVariable String category) {
        try {
            List<DiaryResponse> diariesByCategory = diaryService.getDiariesByCategory(category);
            if (diariesByCategory.isEmpty()) {
                throw new IllegalArgumentException("해당 카테고리의 일기를 찾을 수 없습니다.");
            }
            return ResponseEntity.ok(diariesByCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 상태 코드
        }
    }

    // 특정 일기 상세 조회
    @GetMapping("/luckybicky/diaries/{diaryId}")
    public ResponseEntity<DetailedDiaryResponse> getDiaryDetail(@PathVariable Long diaryId) {
        try {
            DetailedDiaryResponse detailedDiaryResponse = diaryService.getDiaryDetail(diaryId);
            return ResponseEntity.ok(detailedDiaryResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 상태 코드
        }
    }

    // 일기 수정
    @PatchMapping("/luckybicky/diaries/{diaryId}")
    public ResponseEntity<String> updateDiary(@PathVariable Long diaryId, @RequestBody DiaryRequest diaryRequest) {
        try {
            diaryService.updateDiary(diaryId, diaryRequest);
            return ResponseEntity.ok("일기가 수정되었습니다");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400 상태 코드
        }
    }

    // 일기 삭제
    @DeleteMapping("/luckybicky/diaries/{diaryId}")
    public ResponseEntity<String> deleteDiary(@PathVariable Long diaryId) {
        try {
            diaryService.deleteDiary(diaryId);
            return ResponseEntity.ok("일기가 삭제되었습니다");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 상태 코드
        }
    }
}
