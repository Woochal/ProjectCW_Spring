package com.thc.projectcd_spring.config;

import com.thc.projectcd_spring.domain.RoleType;
import com.thc.projectcd_spring.repository.RoleTypeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component  // 스프링 빈으로 등록
@RequiredArgsConstructor  // final 필드에 대한 생성자 자동 생성
public class DataInitializer implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final RoleTypeRepository roleTypeRepository;

    @Override
    public void run(String... args) {
        initializeRoles();
    }

    private void initializeRoles() {
        try {
            // 역할이 하나도 없을 때만 초기화
            if (roleTypeRepository.count() == 0) {
                logger.info("Initializing default roles...");

                roleTypeRepository.saveAll(Arrays.asList(
                        RoleType.of("USER_ROLE", "ROLE_USER"),  // 일반 사용자 역할
                        RoleType.of("ADMIN_ROLE", "ROLE_ADMIN") // 관리자 역할
                ));

                logger.info("Default roles have been initialized");
            } else {
                logger.info("Roles are already initialized");
            }
        } catch (Exception e) {
            logger.error("Failed to initialize roles: " + e.getMessage());
        }
    }
}