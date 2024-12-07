package com.thc.projectcd_spring.security;

import com.thc.projectcd_spring.domain.Tbuser;
import com.thc.projectcd_spring.exceptions.NoMatchingDataException;
import com.thc.projectcd_spring.repository.TbuserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final TbuserRepository tbuserRepository;

    public PrincipalDetailsService(TbuserRepository tbuserRepository) {
        this.tbuserRepository = tbuserRepository;
    }

    /**
     *  principalDetails 생성을 위한 함수.
     *  username으로 tbuser 조회, principalDetails 생성
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("디비에 확인 "+username);
        // findByUserId 가 아니라 findByUserIdAndUserPw여야 하는거 아닌가
        Tbuser tbuser = tbuserRepository.findByUsername(username);
        if(tbuser == null) {
            throw new NoMatchingDataException("username : " + username);
        }
        System.out.println("찾은 유저 ID: "+tbuser.getUsername());
        System.out.println("찾은 유저 PW: "+tbuser.getPassword());
        return new PrincipalDetails(tbuser);
    }

}
