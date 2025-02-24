package com.study.basics.dto;

import com.study.basics.enums.UserSearchField;
import lombok.Data;

@Data
public class UserSearchRequest {
    private UserSearchField userSearchField;
    private String searchValue;
}
