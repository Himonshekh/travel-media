package com.example.travelmedia.config;

import com.example.travelmedia.dto.PrivacyDto;
import com.example.travelmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("defaultUserDetailService")
    private UserDetailsService userDetailsService;

//    public SecurityConfig(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }


    @Override
    protected void configure(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .authorizeRequests()
                .antMatchers("/","/index","/home","/user/**").access("hasRole('ROLE_USER')")
                .antMatchers("/**").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/my-error-page")
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/home",true)
                    .failureUrl("/login?error=true")

                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .csrf()
//                  .ignoringAntMatchers("/h2-console/**")
                .disable()
//                  .and()
//               .headers()
//                  .frameOptions()
//                      .sameOrigin()
        ;
    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//        AuthenticationProvider
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder())
        ;
    }
    @Bean
    public PrivacyDto privacyDto(){
        return new PrivacyDto();
    }
}
