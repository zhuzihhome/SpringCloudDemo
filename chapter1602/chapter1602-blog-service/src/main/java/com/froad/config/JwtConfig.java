package com.froad.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

/**
 * @author ZHUZIHUI
 * @date 2018年10月29日
 */
@Configuration
public class JwtConfig {

	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Bean
	@Qualifier("tokenStore")
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter);
	}

	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		ClassPathResource resource = new ClassPathResource("public.cert");
		String publicKey = "";
		try {
			publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		converter.setVerifierKey(publicKey);
		return converter;
	}

}
