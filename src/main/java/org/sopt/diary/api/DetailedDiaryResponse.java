
package org.sopt.diary.api;

import java.time.LocalDateTime;

public class DetailedDiaryResponse {


    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;


    // 생성자: 필요한 값을 받아 필드를 초기화
    public DetailedDiaryResponse(long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getter for id
    public long getId() {
        return id;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Getter for content
    public String getContent() {
        return content;
    }

    // Getter for createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}
