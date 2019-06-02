package com.vuongpq2.datn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//@ComponentScan
//@EnableJpaRepositories(basePackages = {"com.leemin.genealogy"})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

//    @Autowired
//    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private SimpleAuthenticationSuccessHandler successHandler;



    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
             .csrf().disable()
             .authorizeRequests()
            .antMatchers("/", "/about","/register","/test/**","/facebook/**","/registration","/admin/*","/api/**","/image/**").permitAll()
            .antMatchers("/css/**","/js/**","/fonts/**").permitAll()
            .antMatchers("/account/**","/error/**","/fragments/**","/avatar/**").hasAnyRole("ADMIN","USER","USER_FB")
            .antMatchers("/admin/**").hasAnyRole("ADMIN")
            .antMatchers("/genealogy/**").hasAnyRole("USER","USER_FB")
            .antMatchers("/login/**","/login1/**").permitAll()//Basically I'm allowing parameters for login so locale can be added and read.
            .anyRequest().authenticated().and().formLogin().successHandler(successHandler).loginPage("/login").permitAll()
//            .and().logout().permitAll()
            .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
            .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
//            .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }

    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // create two users, admin and user
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)  {
        try {
            DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserDetailsService> authenticationManagerBuilderUserDetailsServiceDaoAuthenticationConfigurer = auth.userDetailsService(userDetailsService);
            authenticationManagerBuilderUserDetailsServiceDaoAuthenticationConfigurer.passwordEncoder(bCryptPasswordEncoder());
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("throws Exception Invalid Username/password");
        }
//
//        auth.inMemoryAuthentication()
//
//            .withUser("user").password(" ")
//            .roles("USER")
//            .and()
//            .withUser("admin").password(" ")
//            .roles("ADMIN");
//        auth.inMemoryAuthentication()
//            .withUser("user")
//            .password("password")
//            .roles("USER")
//            .and()
//            .withUser("admin")
//            .password("password")
//            .roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring()
           .antMatchers(
                   "/webjars/**",
                   "/css/**",
                   "/fonts/**",
                   "/libs/**",
                   "/js/**",
                   "/static/dist/**",
                   "/img/**",
                   "/plugins/**",
                   "/treant/**",
                   "/headshots/**",
                   "/fragments/**"
                       );
    }
}