package org.sopt.diary.api;


public class DiaryResponse {
    private Long id;
    private String title;

    public DiaryResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    // Getter methods
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}