package com.example.genealogy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SimpleAuthenticationSuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/about","/register","/test/**","/facebook/**","/registration","/admin/*","/api/**","/image/**").permitAll()
                .antMatchers("/css/**","/js/**","/fonts/**").permitAll()
                .antMatchers("/account/**","/error/**","/fragments/**","/avatar/**").hasAnyRole("ADMIN","MEMBER","USER_FB")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/genealogy/**").hasAnyRole("MEMBER","USER_FB")
                .antMatchers("/login/**","/login1/**").permitAll()//Basically I'm allowing parameters for login so locale can be added and read.
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated().and().formLogin().successHandler(successHandler).loginPage("/login").permitAll()
//            .and().logout().permitAll()
                .usernameParameter("email")//
                .passwordParameter("password")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        ;
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/admin")
//                .failureUrl("/login?error")
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/403");
    }
}
