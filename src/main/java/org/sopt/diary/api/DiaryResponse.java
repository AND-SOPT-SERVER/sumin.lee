package org.sopt.diary.api;

import java.time.LocalDateTime;
//
//public class DiaryResponse {
//
//    private long id;
//    private String title;
//    private String content;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    // 생성자: 필요한 값을 받아 필드를 초기화
//    public DiaryResponse(long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }
//
//    // Getter for id
//    public long getId() {
//        return id;
//    }
//
//    // Getter for title
//    public String getTitle() {
//        return title;
//    }
//
//    // Getter for content
//    public String getContent() {
//        return content;
//    }
//
//    // Getter for createdAt
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    // Getter for updatedAt
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//}
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