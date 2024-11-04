package org.sopt.diary.user.repository;

import jakarta.persistence.*;
import org.sopt.diary.repository.DiaryEntity;
import java.util.List;

@Entity
@Table(name="user_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<DiaryEntity> diaries;

    // 기본 생성자
    public UserEntity() {
    }
    // 모든 필드를 받는 생성자 추가
    public UserEntity(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
    // Getter 메서드들
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public List<DiaryEntity> getDiaries() {
        return diaries;
    }
}


