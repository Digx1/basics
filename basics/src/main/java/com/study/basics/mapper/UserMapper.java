package com.study.basics.mapper;

import com.study.basics.dto.UserDTO;
import com.study.basics.entity.User;

public class UserMapper {


    public User mapUserDTOToUser(UserDTO userDTO) {

        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getRole()
        );

    }

    public UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
