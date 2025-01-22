package com.study.basics.service.impl;

import com.study.basics.dto.UserDTO;
import com.study.basics.entity.User;
import com.study.basics.mapper.UserMapper;
import com.study.basics.repositeory.UserRepository;
import com.study.basics.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public abstract class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapUserDTOToUser(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.mapUserToUserDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserMapper userMapper = new UserMapper();
        User user = userRepository.findById(id).orElse(null);
        List<User> users = new ArrayList<User>();
        return userMapper.mapUserToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        UserMapper userMapper = new UserMapper();
        List<User> allUsers = userRepository.findAll();
        List<UserDTO> allUserDTOs = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            UserDTO userDTO = userMapper.mapUserToUserDTO(allUsers.get(i));
            allUserDTOs.add(userDTO);
        }
        return allUserDTOs;
    }

    @Override
    public UserDTO updateUser(UserDTO updatedUser) {
        UserMapper userMapper = new UserMapper();
        Optional<User> optionalUser = userRepository.findById(updatedUser.getId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            User updatedUserObj = userRepository.save(user);
            return userMapper.mapUserToUserDTO(updatedUserObj);
        }

        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}
