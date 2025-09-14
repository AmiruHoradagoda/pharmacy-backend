package com.project.pharmacy_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                // Auth endpoints
                .antMatchers("/api/v1/auth/**").permitAll()

                // Swagger UI endpoints
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/webjars/**").permitAll() // Permit all users to access Swagger UI
                .antMatchers(HttpHeaders.ALLOW).permitAll()

                // Public item endpoints
                .antMatchers(HttpMethod.GET, "/api/v1/item/item-list", "/api/v1/item/by-id/**").permitAll()

                // Admin only endpoints
                .antMatchers(HttpMethod.POST, "/api/v1/item/save").hasRole("Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/item/**").hasRole("Admin")
                .antMatchers(HttpMethod.DELETE, "/api/v1/item/**").hasRole("Admin")

                .antMatchers(HttpMethod.GET, "/api/v1/user/**").hasRole("Admin")

                .antMatchers( "/api/v1/dashboard/**").hasRole("Admin")

                // Other endpoints
                .antMatchers(HttpMethod.POST, "/api/v1/order/**").authenticated()
                .antMatchers(HttpMethod.PUT,"/api/v1/order/**").hasRole("Admin")


                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}