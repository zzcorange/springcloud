package com.zzc.security.config;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Collection;

/**
 * @Author 张真诚
 * @Date 2019/9/26
 * 重写一个webExpressionVoter
 */
public class MyWebExpressionVoter implements AccessDecisionVoter<FilterInvocation> {
    private WebExpressionVoter webExpressionVoter = new WebExpressionVoter();

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return webExpressionVoter.supports(configAttribute);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return webExpressionVoter.supports(aClass);
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation o, Collection<ConfigAttribute> collection) {
        /**
         * 如果配置是空的，那么禁止访问
         */
        if(collection.size()==1){
            ConfigAttribute configAttribute = (ConfigAttribute)collection.toArray()[0];
            if(null==configAttribute.getAttribute())
                return ACCESS_DENIED;
        }
       return webExpressionVoter.vote(authentication,o,collection);
    }
}
