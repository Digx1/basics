package com.study.basics.service.impl;

import com.study.basics.dto.UserDTO;
import com.study.basics.entity.User;
import com.study.basics.mapper.UserMapper;
import com.study.basics.repositeory.UserRepositeory;
import com.study.basics.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public abstract class UserServiceImpl implements UserService {

    private UserRepositeory userRepositeory;
    // UserMapper userMapper = new UserMapper();
    //private UserMapper userMapper;
    @Override

    public UserDTO createUser(UserDTO userDTO) {
        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapUserDTOToUser(userDTO);
        User savedUser = userRepositeory.save(user);
        return userMapper.mapUserToUserDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserMapper userMapper = new UserMapper();
        User user = userRepositeory.findById(id).orElse(null);
        List<User> users = new ArrayList<User>();
        return userMapper.mapUserToUserDTO(user) ;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        UserMapper userMapper = new UserMapper();
        List<User> allUsers = userRepositeory.findAll();
        List<UserDTO>allUserDTOs=new ArrayList<>();
        for(int i = 0; i< allUsers.size(); i++){
           UserDTO userDTO = userMapper.mapUserToUserDTO(allUsers.get(i));
           allUserDTOs.add(userDTO);
        }
        return allUserDTOs;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO updatedUser) {
        UserMapper userMapper = new UserMapper();
        Optional<User> optionalUser = userRepositeory.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            User updatedUserObj = userRepositeory.save(user);
            return userMapper.mapUserToUserDTO(updatedUserObj);
        }

        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepositeory.deleteById(id);
    }


}
