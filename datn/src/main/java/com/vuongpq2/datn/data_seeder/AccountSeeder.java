package com.vuongpq2.datn.data_seeder;

import com.vuongpq2.datn.model.NodeMemberModel;
import com.vuongpq2.datn.model.RoleModel;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.repository.NodeMemberRepository;
import com.vuongpq2.datn.repository.RoleRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.NodeMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class AccountSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NodeMemberService nodeMemberService;

    @Autowired
    private NodeMemberRepository nodeMemberRepository;

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
/*        List<NodeMemberModel> nodeMemberModels = nodeMemberService.findAll();
        for(NodeMemberModel  nodeMemberModel: nodeMemberModels) {
            nodeMemberModel.setLifeIndex(nodeMemberModel.getLifeIndex() + 1);
            nodeMemberRepository.save(nodeMemberModel);
        }*/
        // Admin account
        for(int i = 0; i < 3; i++) {
            if (userRepository.findByEmail("vuongadmin" + i + "@gmail.com") == null) {
                UserModel admin = new UserModel();
                admin.setActive(1);
                admin.setName("PQV_admin" + i);
                admin.setEmail("vuongadmin" + i + "@gmail.com");
                admin.setAddress("sdfsfa");
                admin.setPassword(passwordEncoder.encode("123456"));
                HashSet<RoleModel> roles = new HashSet<>();
                roles.add(roleRepository.findByName("ADMIN"));
                roles.add(roleRepository.findByName("USER"));
                admin.setRoles(roles);
                userRepository.save(admin);
            }
        }

        for(int i = 0; i< 10; i++) {
            // Member account
            if (userRepository.findByEmail("vuonguser" + i + "@gmail.com") == null) {
                UserModel user = new UserModel();
                user.setName("PQV_user");
                user.setEmail("vuonguser" + i + "@gmail.com");
                user.setPassword(passwordEncoder.encode("123456"));
                HashSet<RoleModel> roles = new HashSet<>();
                roles.add(roleRepository.findByName("USER"));
                user.setRoles(roles);
                userRepository.save(user);
            }
        }
    }

}
