
package me.candybox.user.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

import me.candybox.user.oauth.jose.Jwks;
import me.candybox.user.oauth.store.JdbcRegisteredClientService;
import me.candybox.user.oauth.store.RedisOAuth2AuthorizationService;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		return http.formLogin(Customizer.withDefaults()).build();
	}

	// @formatter:off
	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
		// RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
		// 		.clientId("messaging-client")
		// 		.clientSecret("csecret")
		// 		.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
		// 		.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
		// 		.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
		// 		.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
		// 		.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
		// 		.redirectUri("http://127.0.0.1:8003/login/oauth2/code/messaging-client-oidc")
		// 		.redirectUri("http://127.0.0.1:8003/authorized")
		// 		.redirectUri("https://baidu.com")
		// 		// .scope(OidcScopes.OPENID)
		// 		.scope("message.read")
		// 		.scope("message.write")
		// 		.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
		// 		.tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(2)).build())	
		// 		.build();

		// Save registered client in db as if in-memory
		JdbcRegisteredClientService registeredClientRepository = new JdbcRegisteredClientService(jdbcTemplate);
		// registeredClientRepository.save(registeredClient);

		return registeredClientRepository;
	}
	// @formatter:on
    /**
     * 保存授权信息
     */
	@Bean
	public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
		return new RedisOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
	}

	@Bean
	public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		RSAKey rsaKey = Jwks.generateRsa();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}


	@Bean
	public ProviderSettings providerSettings() {
		return ProviderSettings.builder().issuer("http://127.0.0.1:8012").build();
	}



}
