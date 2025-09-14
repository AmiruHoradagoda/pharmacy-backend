package com.project.pharmacy_backend.service.impl;

import com.project.pharmacy_backend.dto.UserDTO;
import com.project.pharmacy_backend.dto.response.RoleDto;
import com.project.pharmacy_backend.dto.response.UserGetResponseDto;
import com.project.pharmacy_backend.dto.response.pagination.UserPaginationResponseDto;
import com.project.pharmacy_backend.entity.Role;
import com.project.pharmacy_backend.entity.User;
import com.project.pharmacy_backend.repo.RoleRepo;
import com.project.pharmacy_backend.repo.UserRepo;
import com.project.pharmacy_backend.service.UserService;
import com.project.pharmacy_backend.util.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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


    @Override
    public UserPaginationResponseDto getAllUsers(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // Fixed: Use the correct repository method
        Page<User> userPage = userRepo.findAllUsers(pageable);

        // Convert User entities to UserGetResponseDto
        List<UserGetResponseDto> userGetResponseDtoList = new ArrayList<>();

        for (User user : userPage.getContent()) {
            // Handle Set<Role> - get the first role or create a combined role representation
            RoleDto roleDto = null;

            if (user.getRole() != null && !user.getRole().isEmpty()) {
                // Option 1: Get the first role from the set
                Role firstRole = user.getRole().iterator().next();
                roleDto = RoleDto.builder()
                        .roleName(firstRole.getRoleName())
                        .roleDescription(firstRole.getRoleDescription())
                        .build();
            }

            // Create UserGetResponseDto
            UserGetResponseDto userGetResponseDto = UserGetResponseDto.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber())
                    .role(roleDto)
                    .build();

            userGetResponseDtoList.add(userGetResponseDto);
        }

        // Create and return UserPaginationResponseDto with complete pagination info
        return UserPaginationResponseDto.builder()
                .dataList(userGetResponseDtoList)
                .dataCount(userPage.getTotalElements())
                .build();
    }

    @Override
    public UserGetResponseDto getUserById(long userId) {
        // Find user by ID, throw exception if not found
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));

        // Handle Set<Role> - get the first role or create a combined role representation
        RoleDto roleDto = null;

        if (user.getRole() != null && !user.getRole().isEmpty()) {
            // Get the first role from the set
            Role firstRole = user.getRole().iterator().next();
            roleDto = RoleDto.builder()
                    .roleName(firstRole.getRoleName())
                    .roleDescription(firstRole.getRoleDescription())
                    .build();
        }

        // Create and return UserGetResponseDto
        return UserGetResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(roleDto)
                .build();
    }
}
