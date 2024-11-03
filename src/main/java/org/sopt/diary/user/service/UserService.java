package org.sopt.diary.user.service;


import org.sopt.diary.user.api.UserLoginRequst;
import org.sopt.diary.user.api.UserSignupRequest;
import org.sopt.diary.user.repository.UserEntity;
import org.sopt.diary.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Transactional
    public UserEntity registerUser(UserSignupRequest signupRequest){ //회원가입
        checkDuplicateNickname(signupRequest.getNickname());
        UserEntity newUser = new UserEntity(
                signupRequest.getUsername(),signupRequest.getPassword(),signupRequest.getNickname()
        );
        return userRepository.save(newUser);
    }

    public UserEntity loginUser(UserLoginRequst userLoginRequest){
        // 사용자 조회
        return validateUserCredentials(userLoginRequest.getUsername(), userLoginRequest.getPassword());

    }



    //회원 가입 시,  nickname의 중복성을 체크하는 매서드
    public void checkDuplicateNickname(String nickname){
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("닉네임이 이미 존재합니다");
        }
    }

    //로그인 검증 매서드
    private UserEntity validateUserCredentials(String username,String password){
        // 사용자 조회
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 틀렸어요");
        }
        return user;
    }
}
