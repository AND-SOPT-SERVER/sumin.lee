package org.sopt.diary.user.api;

public class UserSignupRequest {
    private String username;
    private String password;
    private String nickname;

    // 기본 생성자
    public UserSignupRequest() {}

    // 매개변수를 받는 생성자
    public UserSignupRequest(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    // Getter 메서드들
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    // Setter 메서드들
    public void setUsername(String username) {
        this.username = username;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
