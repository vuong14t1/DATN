package com.example.genealogy.data_seeder;

import com.example.genealogy.model.RoleModel;
import com.example.genealogy.model.UserModel;
import com.example.genealogy.repository.RoleRepository;
import com.example.genealogy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;

@Component
public class AccountSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        System.out.println("vao day seeder");
        // Roles
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            RoleModel roleModel = new RoleModel();
            roleModel.setName("ROLE_ADMIN");
            roleRepository.save(roleModel);
        }

        if (roleRepository.findByName("ROLE_MEMBER") == null) {
            RoleModel roleModel = new RoleModel();
            roleModel.setName("ROLE_MEMBER");
            roleRepository.save(roleModel);
        }

        // Admin account
        if (userRepository.findByEmail("admin2@gmail.com") == null) {
            UserModel admin = new UserModel();
            admin.setActive(1);
            admin.setName("PQV_admin");
            admin.setEmail("admin2@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            HashSet<RoleModel> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
            roles.add(roleRepository.findByName("ROLE_MEMBER"));
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        // Member account
        if (userRepository.findByEmail("member1@gmail.com") == null) {
            UserModel user = new UserModel();
            user.setName("PQV_user");
            user.setEmail("member1@gmail.com");
            user.setPassword(passwordEncoder.encode("123456"));
            HashSet<RoleModel> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_MEMBER"));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

}
