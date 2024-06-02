package com.project.pharmacy_backend.controller;

import com.project.pharmacy_backend.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public abstract class UserController<T> {
    protected abstract String saveEntity(T dto);

    @PostMapping("/save")
    public ResponseEntity<StandardResponse> save(@RequestBody T dto) {
        String message = saveEntity(dto);
        return new ResponseEntity<>(new StandardResponse(201, "Entity Saved", message), HttpStatus.CREATED);
    }
}
