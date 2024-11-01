package org.sopt.diary.api;

public class SimpleDiaryResponse {
    private Long id;
    private String content;

    public SimpleDiaryResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getDiaryId() {
        return id;
    }

    public String getMessage() {
        return content;
    }
}
