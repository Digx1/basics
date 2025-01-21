package com.study.basics.controller;

import com.study.basics.dto.UserDTO;
import com.study.basics.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {
    private  UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> CreateUser(UserDTO userDTO) {
     UserDTO savedUser = userService.createUser(userDTO);
     return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
   @GetMapping("{id}")
   public ResponseEntity<UserDTO> GetUserById(Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);

   }
   @GetMapping
   public ResponseEntity<List<UserDTO>> GetAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
   }
   @PutMapping("{id}")
   public ResponseEntity<UserDTO> UpdateUser(Long id, UserDTO userDTO) {
        UserDTO user = userService.updateUser(id,userDTO);
        return ResponseEntity.ok(user);
   }

   public ResponseEntity<String> DeleteUserById(Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("user deleted");
   }

}
