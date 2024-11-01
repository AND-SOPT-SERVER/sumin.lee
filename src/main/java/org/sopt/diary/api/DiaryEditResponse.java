package org.sopt.diary.api;


public class DiaryEditResponse {
    private Long id;
    private String title;
    private String content;

    public DiaryEditResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // Getter methods
    public Long getId() {
        return id;
    }


}