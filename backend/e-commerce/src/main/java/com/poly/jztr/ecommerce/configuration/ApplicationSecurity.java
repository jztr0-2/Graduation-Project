package com.poly.jztr.ecommerce.configuration;

import com.poly.jztr.ecommerce.configuration.jwt.JwtEntryPoint;
import com.poly.jztr.ecommerce.configuration.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurity{
    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests().//antMatchers("api/v1/public/").permitAll().
//                antMatchers("api/v1/users").hasAuthority("ROLE_USER")
//                //   .antMatchers("api/v1/admin/").hasAuthority("ROLE_ADMIN")
//                .and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);;
        http.authorizeRequests()
                .antMatchers("/api/v1/users/**").hasAuthority("USER")
                .antMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                .antMatchers("/api/v1/public/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
