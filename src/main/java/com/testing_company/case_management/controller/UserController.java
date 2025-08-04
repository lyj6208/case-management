package com.testing_company.case_management.controller;

import com.testing_company.case_management.dto.TeamResponseDTO;
import com.testing_company.case_management.dto.UserResponseDTO;
import com.testing_company.case_management.model.User;
import com.testing_company.case_management.service.UserService;
import com.testing_company.case_management.util.LogUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
@AllArgsConstructor

public class UserController {
    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        LogUtils.logRequest(log,this,"建立User：{}",user);
        User createdUser=userService.createUser(user);
        LogUtils.logResponse(log,this,"建立User：{}",user);
        return ResponseEntity.ok(createdUser);
    }
    @GetMapping("/findById/{userId}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long userId){
        LogUtils.logRequest(log,this,"尋找User_ID：{}",userId);
        UserResponseDTO userResponseDTO=userService.findUserById(userId);
        LogUtils.logResponse(log,this,"尋找User_ID：{}",userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }
    @GetMapping("/findAll")
    public ResponseEntity<Page<UserResponseDTO>> findAllUsers(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "asc")String sortDir
    ){
        LogUtils.logRequest(log,this,"尋找所有User");
        Sort sort=sortDir.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<UserResponseDTO>foundUsers=userService.findAllUsers(pageable);
        LogUtils.logResponse(log,this,"尋找所有User");
        return ResponseEntity.status(HttpStatus.OK).body(foundUsers);
    }
    @PostMapping("/update/{userId}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable Long userId, @RequestBody User inputtedUser){
        LogUtils.logRequest(log,this,"更新User_ID：{}",userId);
        UserResponseDTO updatedUser=userService.updateUserById(userId, inputtedUser);
        LogUtils.logResponse(log,this,"更新User_ID：{}",userId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
