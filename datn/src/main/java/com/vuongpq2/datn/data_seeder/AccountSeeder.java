package com.vuongpq2.datn.data_seeder;

import com.vuongpq2.datn.model.RoleModel;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.repository.RoleRepository;
import com.vuongpq2.datn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        if (roleRepository.findByName("ADMIN") == null) {
            RoleModel roleModel = new RoleModel();
            roleModel.setName("ADMIN");
            roleRepository.save(roleModel);
        }

        if (roleRepository.findByName("USER") == null) {
            RoleModel roleModel = new RoleModel();
            roleModel.setName("USER");
            roleRepository.save(roleModel);
        }

        // Admin account
        if (userRepository.findByEmail("vuongadmin@gmail.com") == null) {
            UserModel admin = new UserModel();
            admin.setActive(1);
            admin.setName("PQV_admin");
            admin.setEmail("vuongadmin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            HashSet<RoleModel> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ADMIN"));
            roles.add(roleRepository.findByName("USER"));
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        // Member account
        if (userRepository.findByEmail("vuonguser@gmail.com") == null) {
            UserModel user = new UserModel();
            user.setName("PQV_user");
            user.setEmail("vuonguser@gmail.com");
            user.setPassword(passwordEncoder.encode("123456"));
            HashSet<RoleModel> roles = new HashSet<>();
            roles.add(roleRepository.findByName("USER"));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

}
