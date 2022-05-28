
package me.candybox.user.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Joe Grandja
 * @since 0.1.0
 */

@Slf4j
@EnableWebSecurity
public class DefaultSecurityConfig {

	// @formatter:off
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests ->
				{
					try {
						authorizeRequests.antMatchers("/api/**").permitAll()
						.and().csrf().ignoringAntMatchers("/api/**") 
						.and().headers().frameOptions().sameOrigin();
					} catch (Exception e) {
						log.error("Exception:{}",e);
					}
				}
			)
			.formLogin(withDefaults()).authorizeRequests();
		return http.build();
	}
	// @formatter:on
	// @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
}
