package br.com.hsi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public AppUserDetailsService userDetailService() {
		return new AppUserDetailsService();
	}
	
    @Bean
    ExceptionMappingAuthenticationFailureHandler exceptionMappingAuthenticationFailureHandler(){
        ExceptionMappingAuthenticationFailureHandler ex = new ExceptionMappingAuthenticationFailureHandler();
        Map<String, String> mappings = new HashMap<String, String>();
        mappings.put("org.springframework.secuirty.authentication.DisabledException", "/Acesso?disabled=true");
        mappings.put("org.springframework.security.authentication.CredentialsExpiredException", "/Acesso?expired=true");
        mappings.put("org.springframework.security.authentication.BadCredentialsException", "/Acesso.xhtml?invalid=true");
        mappings.put("org.springframework.security.authentication.LockedException",  "/Acesso.xhtml?locked=true");
        mappings.put(UsernameNotFoundException.class.getCanonicalName(), "/Acesso.xhtml?invalid=true");
        
        ex.setExceptionMappings(mappings);
        ex.setDefaultFailureUrl("/Acesso.xhtml?invalid=true");
        return ex;
    }


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsfLoginUrlAuthenticationEntryPoint entryPoint = new JsfLoginUrlAuthenticationEntryPoint();
		entryPoint.setLoginFormUrl("/Acesso.xhtml");
		entryPoint.setRedirectStrategy(new JsfRedirectStrategy());
		
		JsfAccessDeniedHandler deniedHandler = new JsfAccessDeniedHandler();
		deniedHandler.setLoginPath("/AcessoNegado.xhtml");
		deniedHandler.setContextRelative(true);
		
		
		http.csrf().disable()
			.headers().frameOptions().sameOrigin().and()			
		.authorizeRequests()
			.antMatchers("/Acesso.xhtml", "/RecuperaSenha.xhtml", "/Erro.xhtml",  "/resources/**" ).permitAll()
			.antMatchers("/Home.xhtml", "/AcessoNegado.xhtml", "/AlterarSenha.xhtml").authenticated()
			.antMatchers("/Sistemas/**").hasAnyRole("CLIENTES")
			.and()
			
		.formLogin()
			.permitAll()
			.loginPage("/Acesso.xhtml")
			.defaultSuccessUrl("/Home.xhtml")
			.failureHandler(exceptionMappingAuthenticationFailureHandler())
			.and()
		.httpBasic().disable()
			
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and()
			
		.exceptionHandling()
			.accessDeniedPage("/AcessoNegado.xhtml")
			.authenticationEntryPoint(entryPoint)
			.accessDeniedHandler(deniedHandler);
	}
}
