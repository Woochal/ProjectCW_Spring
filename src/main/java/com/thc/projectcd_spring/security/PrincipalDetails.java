package com.thc.projectcd_spring.security;

import com.thc.projectcd_spring.domain.Tbuser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
// Spring Security의 UserDetails 인터페이스를 구현
public class PrincipalDetails implements UserDetails {

    private final Tbuser tbuser;

    public PrincipalDetails(Tbuser tbuser) {
        this.tbuser = tbuser;
    }

    public Tbuser getTbuser() {
        return tbuser;
    }

    @Override
    public String getPassword() {
        return tbuser.getPassword();
    }

    @Override
    public String getUsername() {
        return tbuser.getUsername();
    }

    // 계정 만료 여부 (true면 만료되지 않음)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    // 사용자의 권한을 반환하는 메서드
    // <? extends GrantedAuthority>는 Java의 제네릭 문법에서 사용되는 와일드카드 타입. GrantedAuthority 또는 그 하위 타입의 객체를 포함할 수 있다. 예를 들어, GrantedAuthority를 extend하여 구현하는 여러 클래스가 있을 경우, 이 표현을 사용하면 이러한 모든 클래스의 객체를 포함
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        tbuser.getRoleList().forEach(tbuserRoleType->{
            authorities.add(()->tbuserRoleType.getRoleType().getTypeName());
        });
        return authorities;
    }
}
