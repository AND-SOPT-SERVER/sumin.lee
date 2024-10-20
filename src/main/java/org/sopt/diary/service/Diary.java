package org.sopt.diary.service;

public class Diary {
    private final long id;
    private final String title;  // name 대신 title 사용

    // 생성자
    public Diary(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {  // name 대신 title 사용
        return title;
    }
}
