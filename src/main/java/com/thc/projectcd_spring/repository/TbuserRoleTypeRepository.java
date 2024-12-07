package com.thc.projectcd_spring.repository;

import com.thc.projectcd_spring.domain.RoleType;
import com.thc.projectcd_spring.domain.TbuserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbuserRoleTypeRepository extends JpaRepository<TbuserRoleType, Long> {
    List<TbuserRoleType> findByTbuserId(String tbuserId);

}
