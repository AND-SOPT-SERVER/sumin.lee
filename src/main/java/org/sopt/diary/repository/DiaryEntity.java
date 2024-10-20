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

    @Column
    private LocalDateTime updatedAt;

    // 기본 생성자 (JPA에서 사용)
    public DiaryEntity() {}

    // 필수값인 title과 content를 받는 생성자
    public DiaryEntity(String title, String content) {
        if (title == null || content == null) {
            throw new IllegalArgumentException("Title과 Content는 반드시 필요합니다.");
        }
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now(); // 생성 시점의 현재 시간
        this.updatedAt = LocalDateTime.now(); // 생성 시점의 현재 시간
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // 업데이트할 때 수정 시간을 갱신하는 메서드 (필요시 사용)
    public void updateContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now(); // 수정 시점의 현재 시간으로 갱신
    }
}
