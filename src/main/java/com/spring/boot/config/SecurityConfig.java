package com.spring.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.spring.boot")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AuthenticationEntryPoint restAuthenticationEntryPoint =
            (request, response, authException) -> response
                    .sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

    SimpleUrlAuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
        response.getOutputStream().print("You shall not pass!");
        response.setStatus(403);
    };

    @Autowired
    RestAuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/resources/**","/images/**","/styles/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .mvcMatchers("/persons/**").hasRole("ADMIN")
                .mvcMatchers("/**").hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .httpBasic()
                .and()
                .logout();
    }

    /*@Bean
    public CsrfTokenRepository repo() {
        HttpSessionCsrfTokenRepository repo = new
                HttpSessionCsrfTokenRepository();
        repo.setParameterName("_csrf");
        repo.setHeaderName("X-CSRF-TOKEN");
        return repo;
    }*/

    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        UserDetails john = User.withUsername("john").password(encoder()
                .encode("doe")).roles("USER").build();
        UserDetails jane = User.withUsername("jane").password(encoder()
                .encode("doe")).roles("USER", "ADMIN").build();
        UserDetails admin = User.withUsername("admin").password(encoder()
                .encode("admin")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(john,jane,admin);
    }
    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
