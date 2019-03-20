package com.example.genealogy.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {


    //region LANGUAGES
    /*
    //thay cai ben duoi dung cookie ==> cai nay dung sessoin
       @Bean
       public LocaleResolver localeResolver() {
           SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
           sessionLocaleResolver.setDefaultLocale(Locale.US);
           return sessionLocaleResolver;
       }*/
    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver()  {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setCookieDomain("myAppLocaleCookie");
        // 60 minutes
        resolver.setCookieMaxAge(60*60);
        return resolver;
    }
    @Bean(name = "messageSource")
    public MessageSource getMessageResource()  {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();

        // Read i18n/messages_xxx.properties file.
        // For example: i18n/messages_en.properties
        messageResource.setBasename("classpath:messages");
//        messageResource.setBasename("classpath:i18n/messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }


    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
    //endregion


//    https://stackoverflow.com/questions/23604540/spring-boot-how-to-properly-inject-javax-validation-validator
    @Bean
    public javax.validation.Validator localValidatorFactoryBean() {
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(getMessageResource());
        return localValidatorFactoryBean;
    }


    //region Password
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    //endregion

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(
//                "/webjars/**",
//                "/img/**",
//                "/css/**",
//                "/js/**")
//                .addResourceLocations(
//                        "classpath:/META-INF/resources/webjars/",
//                        "classpath:/static/img/",
//                        "classpath:/static/css/",
//                        "classpath:/static/js/");
    }
}