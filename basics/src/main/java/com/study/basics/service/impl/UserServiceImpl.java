package com.study.basics.service.impl;

import com.study.basics.dto.CompanyDTO;
import com.study.basics.dto.UserDTO;
import com.study.basics.entity.Company;
import com.study.basics.entity.User;
import com.study.basics.mapper.UserMapper;
import com.study.basics.repositeory.UserRepository;
import com.study.basics.service.CompanyService;
import com.study.basics.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyService companyService;

    public UserServiceImpl(UserRepository userRepository, CompanyService companyService) {
        this.userRepository = userRepository;
        this.companyService = companyService;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.mapUserDTOToUser(userDTO);
        Company company = null;
        try {
            company = companyService.getCompanyById(userDTO.getCompanyId());
        } catch (Exception exception) {
            log.error("Company not found");
        }

        if (Objects.isNull(company)) {
            return null;
        }
        user.setCompany(company);
        User savedUser = userRepository.save(user);
        return UserMapper.mapUserToUserDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) throws Exception {
        User user = null;
        CompanyDTO companyDTO = null;
        try {
            user = userRepository.findById(id).orElse(null);
        } catch (Exception exception) {
            log.error("user not found");
        }

        if(Objects.isNull(user)){
         return null;
        }

        UserDTO userDTO = UserMapper.mapUserToUserDTO(Objects.requireNonNull(user));
        Company company = companyService.getCompanyById(userDTO.getCompanyId());
        companyDTO = companyService.mapToCompanyDTO(company);
        return UserMapper.mapUserToUserDTOWithCompany(user, companyDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() throws Exception {
        List<User> allUsers = userRepository.findAll();
        List<UserDTO> allUserDTOs = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            UserDTO userDTO = UserMapper.mapUserToUserDTO(user);
            Company company = companyService.getCompanyById(userDTO.getCompanyId());
            CompanyDTO companyDTO = companyService.mapToCompanyDTO(company);

            allUserDTOs.add(UserMapper.mapUserToUserDTOWithCompany(user, companyDTO));
        }
        return allUserDTOs;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO updatedUser) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            user.setCompany(companyService.getCompanyById(updatedUser.getCompanyId()));
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
