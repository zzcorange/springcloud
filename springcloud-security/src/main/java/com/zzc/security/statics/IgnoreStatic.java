package com.zzc.security.statics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author 张真诚
 * @Date 2019/10/16
 */
public class IgnoreStatic {
    /**
     * 无需验证权限的配置
     * 但这个忽略的请求需要经过后台，所以不能直接无视
     */
    public static List<String> ignoreList = Arrays.asList("/**","/login*","/error*","/**/*.jpg","/logout","/ignore/**");
}
