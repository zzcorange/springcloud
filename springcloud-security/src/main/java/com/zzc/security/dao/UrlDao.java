package com.zzc.security.dao;

import com.zzc.security.entity.Url;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
public interface UrlDao {
    List<Url> queryAll();
}
