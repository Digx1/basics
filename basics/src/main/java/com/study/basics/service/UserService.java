package com.study.basics.service;

import com.study.basics.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long id) throws Exception;
    List<UserDTO> getAllUsers() throws Exception;
    UserDTO updateUser(Long id,UserDTO updatedUser) throws Exception;
    void deleteUserById(Long id);
}
