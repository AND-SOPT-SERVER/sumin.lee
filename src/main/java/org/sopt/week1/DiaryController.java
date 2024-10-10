package org.sopt.week1;

import java.util.List;

public class DiaryController {
    private Status status = Status.READY;
    private final DiaryService diaryService = new DiaryService();

    Status getStatus() {
        return status;
    }

    void boot() {
        this.status = Status.RUNNING;
    }

    void finish() {
        this.status = Status.FINISHED;
    }

    // 모든 일기 가져오기
    final String getList() {
        List<Diary> diaryList = diaryService.getAllDiaries();
        if (diaryList.isEmpty()) {
            return "일기가 없습니다.";
        }

        StringBuilder sb = new StringBuilder();
        for (Diary diary : diaryList) {
            sb.append("ID: ").append(diary.getId()).append(", 내용: ").append(diary.getBody()).append("\n");
        }
        return sb.toString();
    }

    // 새로운 일기 추가하기
    final void post(final String body) {
        diaryService.addDiary(body); // 새로운 일기 추가
    }

    // 특정 일기 삭제
    final void delete(final String id) {
        diaryService.deleteDiary(Long.parseLong(id)); // ID로 일기 삭제
    }

    // 특정 일기 수정
    final void patch(final String id, final String body) {
        diaryService.updateDiary(Long.parseLong(id), body); // ID와 새로운 내용으로 일기 수정
    }

    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }
}
