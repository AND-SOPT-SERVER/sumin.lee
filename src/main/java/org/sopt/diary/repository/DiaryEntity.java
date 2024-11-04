package org.sopt.diary.repository;

import jakarta.persistence.*;
import org.sopt.diary.user.repository.UserEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private UserEntity user;

    // 기본 생성자 (JPA에서 사용)
    public DiaryEntity() {
        this.createdAt = LocalDateTime.now(); // 기본 생성 시점 설정
    }

    // UserEntity가 포함된 생성자
    public DiaryEntity(String title, String content, String category, UserEntity user) {
        if (title == null || content == null) {
            throw new IllegalArgumentException("Title과 Content는 반드시 필요합니다.");
        }
        this.title = title;
        this.content = content;
        this.category = category;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    // UserEntity 없이 사용할 수 있는 생성자 추가
    public DiaryEntity(String title, String content, String category) {
        if (title == null || content == null) {
            throw new IllegalArgumentException("Title과 Content는 반드시 필요합니다.");
        }
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }

    // Getter 메서드들
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

    public String getCategory() {
        return category;
    }

    public UserEntity getUser() {
        return user;
    }

    // Setter 메서드들
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
