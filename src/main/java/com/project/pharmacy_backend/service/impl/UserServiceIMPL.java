package com.project.pharmacy_backend.service.impl;

import com.project.pharmacy_backend.dto.UserDTO;
import com.project.pharmacy_backend.entity.Role;
import com.project.pharmacy_backend.entity.User;
import com.project.pharmacy_backend.repo.RoleRepo;
import com.project.pharmacy_backend.repo.UserRepo;
import com.project.pharmacy_backend.service.UserService;
import com.project.pharmacy_backend.util.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Transactional
    public void initCustomerRoleAndCustomer() {
        if (!roleRepo.existsById("Customer")) {
            Role customerRole = new Role();
            customerRole.setRoleName("Customer"); // Manually assigning the ID
            customerRole.setRoleDescription("Customer role");
            roleRepo.save(customerRole);
        }

        if (!userRepo.existsByEmail("miyuru@gmail.com")) {
            User customer = new User();
            customer.setFirstName("Miyuru");
            customer.setLastName("Dapsara");
            customer.setPassword(passwordEncoder.encode("customer@123"));
            customer.setPhoneNumber("0552232048");
            customer.setEmail("miyuru@gmail.com");

            // Fetch the existing Customer role
            Role customerRole = roleRepo.findById("Customer").orElseThrow(() -> new RuntimeException("Customer role not found"));

            Set<Role> customerRoles = new HashSet<>();
            customerRoles.add(customerRole);

            customer.setRole(customerRoles);
            userRepo.save(customer);
        }
    }
    @Transactional
    public void initAdminRoleAndAdmin() {
        if (!roleRepo.existsById("Admin")) {
            Role adminRole = new Role();
            adminRole.setRoleName("Admin");
            adminRole.setRoleDescription("Admin role");
            roleRepo.save(adminRole);
        }

        if (!userRepo.existsByEmail("amiru@gmail.com")) {
            User admin = new User();
            admin.setFirstName("Amiru");
            admin.setLastName("Horadagoda");
            admin.setPassword(passwordEncoder.encode("admin@123"));
            admin.setPhoneNumber("0717244872");
            admin.setEmail("amiru@gmail.com");

            // Fetch the existing Admin role
            Role adminRole = roleRepo.findById("Admin").orElseThrow(() -> new RuntimeException("Admin role not found"));

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            admin.setRole(adminRoles);
            userRepo.save(admin);
        }
    }


    @Override
    @Transactional
    public String registerUser(UserDTO userDTO) {
        System.out.println("User first name: " + userDTO.getFirstName());

        // Hash the password
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userMapper.UserDtoToUserEntity(userDTO);

        // Assign role
        assignRoleToUser(user, userDTO.getRoleName());

        userRepo.save(user);
        System.out.println("User email: " + user.getEmail());

        return userDTO.getFirstName() + ", You are successfully registered";
    }

    private void assignRoleToUser(User user, String roleName) {
        Role role = roleRepo.findById(roleName)
                .orElseThrow(() -> new RuntimeException(roleName + " role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
    }

}
