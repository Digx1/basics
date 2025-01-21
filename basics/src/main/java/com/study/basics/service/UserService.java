package com.study.basics.service;

import com.study.basics.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id,UserDTO updatedUser);
    void deleteUserById(Long id);
}
