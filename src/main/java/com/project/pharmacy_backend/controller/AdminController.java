package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.dto.UserDTO;
import com.project.pharmacy_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("api/v1/admin")
@CrossOrigin
public class AdminController extends UserController<UserDTO>{
    @Autowired
    private UserService adminService;

    @PostConstruct
    public void initAdminRoleAndAdmin(){
        adminService.initAdminRoleAndAdmin();
    }

    @Override
    protected String saveEntity(UserDTO adminDTO) {
        return adminService.saveUser(adminDTO);
    }
}
