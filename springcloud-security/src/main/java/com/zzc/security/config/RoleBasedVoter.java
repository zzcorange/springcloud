package com.zzc.security.config;

import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author 张真诚
 * @Date 2019/9/19
 */
public class RoleBasedVoter implements AccessDecisionVoter<Object> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof org.springframework.security.access.SecurityConfig;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        System.out.println("决策是否可以访问："+object);
        System.out.println("决策是否可以访问authentication:"+ JSON.toJSONString(authentication));
        System.out.println("决策是否可以访问attributes:"+ JSON.toJSONString(attributes));
        if(attributes!=null&&attributes.size()>0){
            ConfigAttribute configAttribute = (ConfigAttribute)attributes.toArray()[0];
            System.out.println("属性类型为："+configAttribute.getClass().getName());
            System.out.println("属性内容为："+JSON.toJSONString(configAttribute));
            System.out.println("属性的attribute为："+configAttribute.getAttribute());
        }

        if(authentication == null||attributes.size()==0) {//如果没有权限配置，或者这个权限并没有权限要求，那么禁止访问
            return ACCESS_DENIED;
        }
        /**
         * 如果配置是空的，那么禁止访问
         */
        if(attributes.size()==1){
            ConfigAttribute configAttribute = (ConfigAttribute)attributes.toArray()[0];
            if(null==configAttribute.getAttribute())
                return ACCESS_DENIED;
        }
        int result = ACCESS_ABSTAIN;

        Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);

        for (ConfigAttribute attribute : attributes) {
            if(attribute.getAttribute()==null){
                continue;
            }
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;

                // Attempt to find a matching granted authority
                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }
        return result;
    }

    Collection<? extends GrantedAuthority> extractAuthorities(
            Authentication authentication) {
        return authentication.getAuthorities();
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }
}