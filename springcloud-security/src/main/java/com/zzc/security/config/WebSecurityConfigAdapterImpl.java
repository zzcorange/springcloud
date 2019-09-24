package com.zzc.security.config;

import com.zzc.security.entity.Role;
import com.zzc.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author 张真诚
 * @Date 2019/9/4
 */
@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigAdapterImpl extends WebSecurityConfigurerAdapter {
    @Autowired
    SessionRegistry sessionRegistry;
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
          user.setAuthorities(Arrays.asList(Role.builder().authority("ROLE_USER").build(),Role.builder().authority("USER").build()));
          user.setPassword("$2a$04$Z3KJccDmN8b1BtAcPEH4i.TFv/COOv6ab3mWgBJwxT1sVfQ9LB0Gq");
          user.setUsername(s);

          return user;

        }
    };
}

    @Override
    public void configure(WebSecurity web) throws Exception {
        //配置静态文件不需要认证
//        web.ignoring().antMatchers("/img/**","/img/*","/jsp/**");

    }
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new RoleBasedVoter()
                ,
                new WebExpressionVoter(),
                 new RoleVoter(),

                new AuthenticatedVoter()
 );
        return new UnanimousBased(decisionVoters);
    }
    protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests().antMatchers("/**","/**/*.jpg","**test**","/**/*.jsp").permitAll().and().csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/login","/authentication/form","/test/**").permitAll()

                .anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager())
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authentication/form")
                .successForwardUrl("/user/getAll")

                .permitAll()
                .and()
//                .csrf().disable()
                .logout()
                .logoutUrl("/my/logout")
                .logoutSuccessUrl("/my/index")
//                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 自定义FilterInvocationSecurityMetadataSource
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(
                            O fsi) {
                        fsi.setSecurityMetadataSource(mySecurityMetadataSource(fsi.getSecurityMetadataSource()));
                        return fsi;
                    }
                })
                .and()
                .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry);
        http.httpBasic();
//                .and()
//                .apply(securityConfigurerAdapter());

//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(cookieNamesToClear)  ;
        ;
    }
    @Bean
    public AppFilterInvocationSecurityMetadataSource mySecurityMetadataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
        AppFilterInvocationSecurityMetadataSource securityMetadataSource = new AppFilterInvocationSecurityMetadataSource(filterInvocationSecurityMetadataSource);

        return securityMetadataSource;
    }
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
