package com.study.basics.mapper;

import com.study.basics.dto.CompanyDTO;
import com.study.basics.dto.UserDTO;
import com.study.basics.entity.User;

import java.util.Objects;

public final class UserMapper {


    public static User mapUserDTOToUser(UserDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getRole()
        );

    }

    public static UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                Objects.nonNull(user.getCompany()) ? user.getCompany().getId() : null,
                null
        );
    }
    public static UserDTO mapUserToUserDTOWithCompany(User user, CompanyDTO companyDTO) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                Objects.nonNull(user.getCompany()) ? user.getCompany().getId() : null,
                companyDTO
        );
    }
}
