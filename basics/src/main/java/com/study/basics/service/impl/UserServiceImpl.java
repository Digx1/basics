package com.study.basics.service.impl;

import com.study.basics.dto.UserDTO;
import com.study.basics.entity.User;
import com.study.basics.mapper.UserMapper;
import com.study.basics.repositeory.UserRepository;
import com.study.basics.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.mapUserDTOToUser(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.mapUserToUserDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return UserMapper.mapUserToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<UserDTO> allUserDTOs = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            UserDTO userDTO = UserMapper.mapUserToUserDTO(allUsers.get(i));
            allUserDTOs.add(userDTO);
        }
        return allUserDTOs;
    }

    @Override
    public UserDTO updateUser(UserDTO updatedUser) {
        Optional<User> optionalUser = userRepository.findById(updatedUser.getId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            User updatedUserObj = userRepository.save(user);
            return UserMapper.mapUserToUserDTO(updatedUserObj);
        }

        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}
