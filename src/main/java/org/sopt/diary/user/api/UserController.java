package org.sopt.diary.user.api;


import org.sopt.diary.user.repository.UserEntity;
import org.sopt.diary.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //회원가입 API

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserSignupRequest signupRequest){
        UserEntity registeredUser = userService.registerUser(signupRequest);
        return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> loginUser(@RequestBody UserLoginRequst loginRequst){
        UserEntity user = userService.loginUser(loginRequst);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
