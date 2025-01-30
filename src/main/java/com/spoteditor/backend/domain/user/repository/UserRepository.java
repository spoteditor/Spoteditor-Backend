package com.spoteditor.backend.domain.user.repository;

import com.spoteditor.backend.domain.user.entity.User;
import com.spoteditor.backend.domain.user.entity.value.OauthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthUserIdAndProvider(String oauthUserId, OauthProvider provider);
}
