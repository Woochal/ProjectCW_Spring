package com.thc.projectcd_spring.repository;

import com.thc.projectcd_spring.domain.RefreshToken;
import com.thc.projectcd_spring.domain.Tbrefreshtoken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    List<RefreshToken> findByTbuserId(String tbuserId);
    Optional<RefreshToken> findByContent(String content);
}
