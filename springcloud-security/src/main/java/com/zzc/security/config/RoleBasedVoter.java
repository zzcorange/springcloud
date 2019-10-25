package com.zzc.security.config;

import com.alibaba.fastjson.JSON;
import com.zzc.security.dao.UrlDao;
import com.zzc.security.entity.Url;
import com.zzc.security.statics.IgnoreStatic;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * @Author 张真诚
 * @Date 2019/9/19
 */
public class RoleBasedVoter implements AccessDecisionVoter<Object> {
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    public RoleBasedVoter(UrlDao urlDao){
        List<Url> urlList = urlDao.queryAll();

        if(urlList==null||urlList.size()==0){
            System.out.println("权限配置为空，请检查");
            System.exit(0);
        }
        urlList.forEach((url)->{
            urlRoleMap.put(url.getUrl(),url.getAuth());
        });
        System.out.println("装载权限配置："+JSON.toJSONString(urlRoleMap));
    }
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof org.springframework.security.access.SecurityConfig;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes1) {
        Collection<ConfigAttribute> attributes = getAttributes(object);
        if(object instanceof FilterInvocation){//如果在无需鉴权列表中，那么允许这个链接的访问。通常是登录页面，错误页面
            String url = ((FilterInvocation)object).getRequestUrl();
            for(String match:IgnoreStatic.ignoreList){
                if(antPathMatcher.match(match,url))
                    return ACCESS_GRANTED;
            }
        }
        if(authentication == null||attributes==null||attributes.size()==0) {//如果没有权限配置，或者这个权限并没有权限要求，那么禁止访问
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

    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        ArrayList<String> tempValue = new ArrayList<>();
        for(Map.Entry<String,String> entry:urlRoleMap.entrySet()){
            if(antPathMatcher.match(entry.getKey(),url)){

                tempValue.add(entry.getValue());

            }
        }
        //  返回代码定义的默认配置
        if(tempValue.size()>0){
            System.out.println(url+"拉取的权限数据："+JSON.toJSONString(tempValue.toArray( new String[tempValue.size()])));
            return SecurityConfig.createList(tempValue.toArray( new String[tempValue.size()]));
        }
        System.out.println("来拉取信息了"+url);
        System.out.println(JSON.toJSONString(urlRoleMap));
        return null;
    }
    // 这里的需要从DB加载
    public  static   Map<String,String> urlRoleMap = new HashMap<String,String>();
}