package com.zzc.security.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author 张真诚
 * @Date 2019/9/17
 */
@Data
@Builder
public class Role implements GrantedAuthority {
    private String authority;
}
