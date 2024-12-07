package com.thc.projectcd_spring.repository;

import com.thc.projectcd_spring.domain.RoleType;
import com.thc.projectcd_spring.domain.Tbuser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleTypeRepository extends JpaRepository<RoleType, String> {
    Optional<RoleType> findByTypeName(String typeName);
}
