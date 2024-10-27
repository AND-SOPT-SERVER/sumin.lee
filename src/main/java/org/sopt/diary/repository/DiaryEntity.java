package org.sopt.diary.repository;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // title은 null일 수 없음
    private String title;

    @Column(nullable = false) // content도 null일 수 없음
    private String content;

    @Column
    private LocalDateTime createdAt;
    @Column // 카테고리 필드 추가
    private String category;


    // 기본 생성자 (JPA에서 사용)
    public DiaryEntity() {}

    // 필수값인 title과 content를 받는 생성자
    public DiaryEntity(String title, String content,String category) {
        if (title == null || content == null ) {
            throw new IllegalArgumentException("Title과 Content는 반드시 필요합니다.");
        }
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = LocalDateTime.now(); // 생성 시점의 현재 시간

    }

    // Getter
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
