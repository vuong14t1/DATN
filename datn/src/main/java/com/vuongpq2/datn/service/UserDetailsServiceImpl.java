package com.vuongpq2.datn.service;

import com.vuongpq2.datn.model.RoleModel;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email);
        System.out.println("email " + email);
        if (user == null)
            throw new UsernameNotFoundException("username/email  " + email
                    + " not found");
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (RoleModel role : user.getRoles()){
            System.out.println("role " + role.getName());
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
