package com.thc.projectcd_spring.repository;

import com.thc.projectcd_spring.domain.TbchickenComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbchickenCommentRepository extends JpaRepository<TbchickenComment, String> {
    TbchickenComment findByUserId(String username);

//    // 최초 조회 시 조인이 같이 하기위해 쓰는 어노테이션
//    // 꼭 조인할때만 쓸것!
//    // 로그인 기능 모두 구현하고 쓸것!! 나중에 확인합시다!!
//    @EntityGraph(attributePaths = {"tbuserRoleType.roleType"})
//    // 특정 엔티티를 조회할 때, 연관된 엔티티를 함께 로딩하기 위해 @EntityGraph를 사용
//    Optional<Tbuser> findEntityGraphRoleTypeById(String id);
}