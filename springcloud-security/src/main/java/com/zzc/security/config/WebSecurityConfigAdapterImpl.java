package com.zzc.security.config;

import com.zzc.security.dao.RoleDao;
import com.zzc.security.dao.UrlDao;
import com.zzc.security.entity.Role;
import com.zzc.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author 张真诚
 * @Date 2019/9/4
 */
@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Component
public class WebSecurityConfigAdapterImpl extends WebSecurityConfigurerAdapter {
    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired(required = false)
    private UrlDao urlDao;
    @Autowired(required = false)
    private RoleDao roleDao;
    public static Map<String,User> loginUser = new HashMap<String,User>();
@Bean
public BCryptPasswordEncoder passwordEncoder() { //密码加密
    return new BCryptPasswordEncoder(4);
}
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
@Bean
public UserDetailsService userDetailsService()  {
//    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//    manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
//    return manager;
    return new UserDetailsService() {
        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
          User user = new User();
          List<Role> list = roleDao.getRole(s);
          if(list==null||list.size()==0)
              throw new UsernameNotFoundException(s+"的权限配置不存在，请检查");
          user.setAuthorities(list);
          user.setPassword("$2a$04$Z3KJccDmN8b1BtAcPEH4i.TFv/COOv6ab3mWgBJwxT1sVfQ9LB0Gq");
          user.setUsername(s);

          return user;

        }
    };
}

    @Override
    public void configure(WebSecurity web) throws Exception {
        //配置静态文件不需要认证
        web.ignoring().antMatchers("/**/*.jpg","/**/*.ico","/**/*.img","/**.jpeg","/**/*.js","/**.css");

    }
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new RoleBasedVoter(urlDao),
                new MyWebExpressionVoter(),
//                 new RoleVoter(),
                new AuthenticatedVoter()
 );
        return new AffirmativeBased(decisionVoters);
    }
    protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests().antMatchers("/**","/**/*.jpg","**test**","/**/*.jsp").permitAll().and().csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/**","/**/*.jpg","/login",
                        "/authentication/form","/test/**",
                        "/error.html","/error.html?**",
                        "/logout","/ignore/**","/oauth/authorize*").permitAll()

                .anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager())
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authentication/form")
                .successHandler(new MyAuthenticationSuccessHandler())
                .failureHandler(new MyAuthenticationFailureHandler())
                .permitAll()

                .and()
//                .csrf().disable()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?message=%E7%99%BB%E5%87%BA%E6%88%90%E5%8A%9F")
//                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
//                .and()
//                .authorizeRequests()
//                // 自定义FilterInvocationSecurityMetadataSource
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(
//                            O fsi) {
//                        fsi.setSecurityMetadataSource(mySecurityMetadataSource(fsi.getSecurityMetadataSource()));
//                        return fsi;
//                    }
//                })
                .and()
                .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/error?accept=sessionExpired").sessionRegistry(sessionRegistry)
                .and().sessionAuthenticationErrorUrl("/error.html")
//                .and()
//                .oauth2ResourceServer().authenticationEntryPoint()

// .and().enableSessionUrlRewriting()
        ;
        http.exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());
        http.httpBasic();
//                .and()
//                .apply(securityConfigurerAdapter());

//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(cookieNamesToClear)  ;
        ;
    }
//    @Bean
//    public AppFilterInvocationSecurityMetadataSource mySecurityMetadataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
//        AppFilterInvocationSecurityMetadataSource securityMetadataSource = new AppFilterInvocationSecurityMetadataSource(filterInvocationSecurityMetadataSource,urlDao);
//
//        return securityMetadataSource;
//    }
    @Bean
    public SessionRegistry getSessionRegistry(){
        SessionRegistry sessionRegistry=new SessionRegistryImpl();
        return sessionRegistry;
    }
    public void addRole(String role){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //  生成当前的所有授权
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        // 添加 ROLE_VIP 授权
        updatedAuthorities.add(new SimpleGrantedAuthority(role));
        // 生成新的认证信息
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        // 重置认证信息
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
    public void addAuthorityToResource(String url,String role){

    }
    public void addUserRole(String user,String role){

    }
    public void getAllUser(){

    }
}
