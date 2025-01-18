package com.study.basics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class User{
    @Id()
    private Long id;
    @Column(name="user_name")
    private String name;
    @Column(name="user_email")
    private String email;
    @Column(name="user_role")
    private String role;
}
