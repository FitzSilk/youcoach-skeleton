package com.switchfully.youcoach.security;

import com.switchfully.youcoach.api.topics.TopicController;
import com.switchfully.youcoach.api.users.UserController;
import com.switchfully.youcoach.security.authentication.jwt.JwtAuthenticationFilter;
import com.switchfully.youcoach.security.authentication.jwt.JwtAuthorizationFilter;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import com.switchfully.youcoach.security.authorization.RoleToFeatureMapper;
import com.switchfully.youcoach.service.users.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private String jwtSecret;
    private RoleToFeatureMapper roleToFeatureMapper;
    private SecuredUserService securedUserService;
    private UserService userService;

    public SecurityConfig(UserService userService, SecuredUserService securedUserService, PasswordEncoder passwordEncoder, @Value("${jwt.secret}") String jwtSecret, RoleToFeatureMapper roleToFeatureMapper) {
        this.securedUserService = securedUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtSecret = jwtSecret;
        this.roleToFeatureMapper = roleToFeatureMapper;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(UserController.USER_RESOURCE_PATH + "/**").permitAll()
                .antMatchers(TopicController.TOPIC_RESOURCE_PATH + "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtSecret, userService))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtSecret, roleToFeatureMapper))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("https://eastern-youcoach.herokuapp.com");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securedUserService).passwordEncoder(passwordEncoder);
    }


}
