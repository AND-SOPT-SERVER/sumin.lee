package org.sopt.week1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    // 일기 저장소
    private final Map<Long, Diary> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    // 일기 저장
    void save(final Diary diary) {
        // ID를 자동 생성하고 일기를 저장
        final long id = numbering.incrementAndGet();
        Diary newDiary = new Diary(id, diary.getBody()); // 새로운 Diary 객체 생성
        storage.put(id, newDiary); // 저장
    }

    // 모든 일기 조회
    List<Diary> findAll() {
        return new ArrayList<>(storage.values()); // 저장된 모든 일기를 반환
    }

    // 특정 일기 삭제 (추가)
    void delete(long id) {
        storage.remove(id); // ID로 일기 삭제
    }

    // 특정 일기 수정 (추가)
    void update(long id, String body) {
        if (storage.containsKey(id)) {
            storage.put(id, new Diary(id, body)); // 새로운 내용으로 수정
        } else {
            System.out.println("해당 ID의 일기가 없습니다.");
        }
    }
}
