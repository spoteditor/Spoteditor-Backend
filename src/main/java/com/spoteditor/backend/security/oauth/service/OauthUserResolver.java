package com.spoteditor.backend.security.oauth.service;

import com.spoteditor.backend.domain.user.entity.User;
import com.spoteditor.backend.domain.user.repository.UserRepository;
import com.spoteditor.backend.security.oauth.dto.OauthAttributes;
import com.spoteditor.backend.security.oauth.dto.OauthAttributesUserMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthUserResolver {

    private final UserRepository userRepository;
    private final OauthAttributesUserMapper oauthAttributesUserMapper;

    public Long resolveUserId(OauthAttributes attributes) {
        return userRepository.findByOauthUserIdAndProvider(attributes.getOauthUserId(), attributes.getProvider())
                .orElseGet(() -> createUser(attributes))
                .getId();
    }

    private User createUser(OauthAttributes attributes) {
        User user = oauthAttributesUserMapper.toEntity(attributes);

        return userRepository.save(user);
    }
}
