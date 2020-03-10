package com.manhnv.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.manhnv.utils.CustomKeyGenerator;

@EnableCaching
@Configuration
public class CachingConfig {
	@Bean("customKeyGenerator")
	public KeyGenerator keyGenerator() {
		return new CustomKeyGenerator();
	}
}
