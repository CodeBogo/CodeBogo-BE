package com.example.codebogo.user.service;

import com.example.codebogo.user.dto.response.UserStatusResponse;
import com.example.codebogo.user.entity.User;
import com.example.codebogo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final String DEMO_NICKNAME = "카리나";

    private final UserRepository userRepository;

    public UserStatusResponse getUserStatus() {
        User user = userRepository.findByNickname(DEMO_NICKNAME)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return UserStatusResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .point(user.getPoint())
                .streak(user.getStreak())
                .lastSolvedDate(user.getLastSolvedDate())
                .build();
    }
}
