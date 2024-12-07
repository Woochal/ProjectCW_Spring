package com.thc.projectcd_spring.repository;

import com.thc.projectcd_spring.domain.Tbemail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository 를 extend함을 통해 파라미터에 대한(여기서는 TestUser) CRUD 작업을 처리하는 메서드를 제공받는다.
@Repository
public interface TbemailRepository extends JpaRepository<Tbemail, String> {
    Tbemail findByUsername(String userId);
    Tbemail findByUsernameAndNumber(String userId, String number);
}
