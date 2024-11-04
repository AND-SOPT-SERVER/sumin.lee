package org.sopt.diary.user.api;

public class UserResponse {
    private  Long id;
    private String username;
    private  String nickname;


    public UserResponse(){}

    // 매개변수를 받는 생성자
    public UserResponse(Long id, String username, String nickname) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }

    // Getter 메서드들
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
