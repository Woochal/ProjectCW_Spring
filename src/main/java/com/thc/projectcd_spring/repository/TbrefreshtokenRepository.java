package com.thc.projectcd_spring.repository;

import com.thc.projectcd_spring.domain.Tbrefreshtoken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbrefreshtokenRepository extends JpaRepository<Tbrefreshtoken, String> {
    Tbrefreshtoken findByTbuserId(String tbuserId);
}

