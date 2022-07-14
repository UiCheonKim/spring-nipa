package dz.nft.nipa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import dz.nft.nipa.security.AuthProvider;
import dz.nft.nipa.security.handlers.AuthFailureHandler;
import dz.nft.nipa.security.handlers.AuthSuccessHandler;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AuthProvider authProvider;

	@Autowired
	AuthSuccessHandler authSuccessHandler;

	@Autowired
	AuthFailureHandler authFailureHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic().disable();
		http.csrf().disable();
		
		// 페이지 접근 권한설정!!
		http.authorizeRequests().antMatchers("/resources/**").permitAll() /* 인증이 필요없는 정적 데이터 */
			// .antMatchers("/main/**", "/admin/login/**", "/error/**", "/block/**", "/tran/**", "/terms").permitAll()
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/**", "/admin/login/**").permitAll();
			// .antMatchers("/user").hasRole("USER")
			// .antMatchers("/manager").hasRole("MANAGER")
			// .antMatchers("/admin").hasRole("ADMIN")
			// .antMatchers("/**").permitAll();
		
		// http. 대신에 .and로 연결도 가능함
		http.formLogin().loginPage("/admin/login") // controller mapping
			.loginProcessingUrl("/admin/loginProc") // 해당 url 호출시 spring secrity에 내장되어있는 로그인 프로세스 실행 - 현 프로젝트에서는 커스텀한 AuthProvider의 Authentication 실행
			.usernameParameter("userId") // id 파라미터값 커스텀 / 디폴트값 = username
			.passwordParameter("userPw") // pw 파라미터 값 커스텀 / 디폴트값 = password
			// .defaultSuccessUrl("/admin/input") // 로그인 성공 후 리다이렉트 url - 현 프로젝트에서는 successHandler에서 커스텀함
			.failureHandler(authFailureHandler)
			.successHandler(authSuccessHandler).permitAll();
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/admin/logoutProc")) // logout - 해당 경로로 요청이 들어오는 경우
			.logoutSuccessUrl("/admin/login") // 이 경로로 리다이렉트하고
			.invalidateHttpSession(true) // 세션 초기화
			.deleteCookies("JSESSIONID")
			.permitAll();
		
		http.sessionManagement()
			.maximumSessions(1)
			.maxSessionsPreventsLogin(false) // 이후에 로그인 한 사람이 로그인 (이전계정 자동 로그아웃)
			.expiredUrl("/admin/login")
			.sessionRegistry(sessionRegistry());
		
		http.exceptionHandling().accessDeniedPage("/error");
	}
	
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> HttpSessionEventPublisher(){
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}

	@Override
	// js, css, image 설정은 보안 설정의 영향 밖에 있도록 만들어주는 설정.
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
			.antMatchers("/resources/**");
	}

}
