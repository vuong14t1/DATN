package com.example.genealogy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.List;

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

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/about","/register","/test/**","/facebook/**","/registration","/admin/*","/api/**","/image/**").permitAll()
                .antMatchers("/css/**","/js/**","/fonts/**").permitAll()
                .antMatchers("/account/**","/error/**","/fragments/**","/avatar/**").hasAnyRole("ADMIN","MEMBER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/genealogy/**").hasRole("MEMBER")
                .antMatchers("/login/**").permitAll()//Basically I'm allowing parameters for login so locale can be added and read.
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated().and().formLogin().successHandler(successHandler).loginPage("/login").permitAll()
                .usernameParameter("email")//
                .passwordParameter("password")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)  {
        try {
            DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserDetailsService> authenticationManagerBuilderUserDetailsServiceDaoAuthenticationConfigurer = auth.userDetailsService(userDetailsService);
            authenticationManagerBuilderUserDetailsServiceDaoAuthenticationConfigurer.passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            System.out.println("throws Exception Invalid Username/password");
        }
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
                        "/dist/**",
                        "/img/**",
                        "/plugins/**",
                        "/treant/**",
                        "/headshots/**",
                        "/fragments/**"
                );
    }
}
