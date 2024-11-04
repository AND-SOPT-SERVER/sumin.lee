package org.sopt.diary.user.api;

public class UserLoginRequst {
    private String username;
    private String password;

    //기본생성자
    public  UserLoginRequst(){}


    //매개변수 받는 생성자

    public UserLoginRequst(String username,String password){
        this.username = username;
        this.password = password;
    }

    // Getter 메서드들
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setter 메서드들
    public void setUsername(String username) {
        this.username = username;
    }


}
