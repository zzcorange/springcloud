package com.zzc.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author 张真诚
 * @Date 2019/9/16
 */
@Data
public class User implements UserDetails {
    private String sex;
    private String phone;
    private int age;
    private String password;
    private String username;
    private boolean accountNonExpired =true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private List<Role> authorities;





}
