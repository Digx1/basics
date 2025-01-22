package com.study.basics.service;

import com.study.basics.dto.UserDTO;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(UserDTO updatedUser);
    void deleteUserById(Long id);
}
