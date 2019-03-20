package com.example.genealogy;

//import com.example.genealogy.service.StorageService;
import com.example.genealogy.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import java.util.concurrent.Executor;

import javax.annotation.Resource;
import java.util.Locale;

@SpringBootApplication
@EnableAsync
//@ComponentScan
public class GenealogyApplication extends AsyncConfigurerSupport implements CommandLineRunner {

	@Resource
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(GenealogyApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        //localeResolver.setDefaultLocale(Locale.US);
		localeResolver.setDefaultLocale(Locale.forLanguageTag("vi-VI"));
		return localeResolver;
	}

	//TODO EMAIL 4. bat bat dong bo o main.java
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("send-mailer-");
		executor.initialize();
		return executor;
	}

	@Override
	public void run(String... arg) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}
}
