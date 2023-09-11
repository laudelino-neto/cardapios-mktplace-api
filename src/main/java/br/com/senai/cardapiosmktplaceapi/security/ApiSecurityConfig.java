package br.com.senai.cardapiosmktplaceapi.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.senai.cardapiosmktplaceapi.service.impl.CredencialDeAcessoServiceImpl;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig {

	@Autowired
	private FiltroDeAutenticacaoJwt filtroDeAutenticacao;
	
	@Autowired
	private CredencialDeAcessoServiceImpl service;
	

	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
    public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(service);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
	private UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource() {
	    CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.applyPermitDefaultValues(); 
	    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
	    corsConfiguration.setAllowedMethods(Arrays.asList("*"));
	    corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
	    corsConfiguration.setExposedHeaders(Arrays.asList("*"));
	    UrlBasedCorsConfigurationSource ccs = new UrlBasedCorsConfigurationSource();
	    ccs.registerCorsConfiguration("/**", corsConfiguration);
	    return ccs;
	}
	
	@Bean	
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http		
		.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((request) -> 
				request
					.requestMatchers("/auth/**")
						.permitAll()
					.requestMatchers(HttpMethod.POST, "/cardapios/**")					
						.hasAnyAuthority("LOJISTA")
					.requestMatchers(HttpMethod.PUT, "/cardapios/**")					
						.hasAnyAuthority("LOJISTA")
					.requestMatchers(HttpMethod.PATCH, "/cardapios/**")					
						.hasAnyAuthority("LOJISTA")	
					.requestMatchers("/cardapios/**")
						.hasAnyAuthority("CLIENTE", "LOJISTA")
					.requestMatchers("/categorias/**")
						.hasAnyAuthority("LOJISTA")	
				.anyRequest().authenticated())
			.sessionManagement(manager -> 
				manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider()).addFilterBefore(
                    filtroDeAutenticacao, UsernamePasswordAuthenticationFilter.class)
			.cors(c -> urlBasedCorsConfigurationSource());
	    return http.build();
	}
	
	
}
