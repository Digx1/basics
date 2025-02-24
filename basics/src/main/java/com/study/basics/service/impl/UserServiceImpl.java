package com.study.basics.service.impl;
import com.study.basics.dto.CompanyDTO;
import com.study.basics.dto.UserDTO;
import com.study.basics.dto.UserSearchRequest;
import com.study.basics.entity.Company;
import com.study.basics.entity.User;
import com.study.basics.mapper.UserMapper;
import com.study.basics.repositeory.UserRepository;
import com.study.basics.service.CompanyService;
import com.study.basics.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static com.study.basics.enums.UserSearchField.EMAIL;
import static com.study.basics.enums.UserSearchField.NAME;
import static com.study.basics.enums.UserSearchField.ROLE;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final EntityManager entityManager;

    public UserServiceImpl(UserRepository userRepository, CompanyService companyService, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.companyService = companyService;
        this.entityManager = entityManager;
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
    public UserDTO getUserById(Long id) throws RuntimeException {
        User user = null;
        CompanyDTO companyDTO = null;
        try {
            user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        } catch (Exception exception) {
            log.error("user not found");
            throw new RuntimeException("user not found");
        }

        if (Objects.isNull(user)) {
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

    @Override
    public List<UserDTO> searchUsers(UserSearchRequest userSearchRequest) {
        List<User> users;

        switch (userSearchRequest.getUserSearchField()) {
            case NAME -> {
                users = userRepository.findUsersByName(userSearchRequest.getSearchValue());
            return getUserDtoList(users);
            }
            case EMAIL -> {
                users = userRepository.findUsersByEmail(userSearchRequest.getSearchValue());
                return getUserDtoList(users);

            }

            case ROLE -> {
                users = userRepository.findUsersByRole(userSearchRequest.getSearchValue());
                return getUserDtoList(users);

            }
            case COMPANY -> {
                users = userRepository.findUsersByCompany_Id(Integer.parseInt(userSearchRequest.getSearchValue()));
                 return getUserDtoList(users);
            }
            default -> {
                throw new RuntimeException("invalid search !!");
            }

        }
    }

    @Override
    public List<UserDTO> searchUsers(List<UserSearchRequest> userSearchRequests) {
        String baseQuery = "SELECT * FROM users WHERE user_name = ";

        baseQuery = baseQuery + "'"+userSearchRequests.stream()
                .filter(userSearchRequest -> NAME.equals(userSearchRequest.getUserSearchField()))
                .map(UserSearchRequest::getSearchValue)
                .findFirst().orElse("") + "'";


        for(UserSearchRequest userSearchRequest:userSearchRequests) {
           if (ROLE.equals(userSearchRequest.getUserSearchField())) {
               baseQuery = baseQuery + " AND user_role = " + "'"+userSearchRequest.getSearchValue() +"'";
           }

           if (EMAIL.equals(userSearchRequest.getUserSearchField())) {
               baseQuery = baseQuery + " AND user_email = " + "'"+userSearchRequest.getSearchValue() +"'";
           }
        }
        List<User> resultList = entityManager.createNativeQuery(baseQuery, User.class).getResultList();
        return getUserDtoList(resultList);
    }

    public List<UserDTO> getUserDtoList(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = UserMapper.mapUserToUserDTO(user);
            Company company = companyService.getCompanyById(userDTO.getCompanyId());
            CompanyDTO companyDTO = companyService.mapToCompanyDTO(company);
            userDTOs.add(UserMapper.mapUserToUserDTOWithCompany(user, companyDTO));

        }

        return userDTOs;

    }
}
