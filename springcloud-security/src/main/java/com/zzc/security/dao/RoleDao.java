package com.zzc.security.dao;

import com.zzc.security.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Mapper
@Component
public interface RoleDao {
    List<Role> getRole(String username);
    void insertRole(Map map);
}
