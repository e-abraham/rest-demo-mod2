package com.example.restdemo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        //all requests protected using Basic Auth except for antMatchers
        httpSecurity
                .authorizeRequests()
                .antMatchers("/rest-demo/authors").permitAll()
                .anyRequest()
                .authenticated().and()
                .httpBasic();

        // ** Trying another way **
//        httpSecurity.authorizeRequests()
//                //these routes won't require authentication, all others will
//                .antMatchers("/", "/rest-demo", "/rest-demo/hello", "/rest-demo/hello/name/*").permitAll().anyRequest().authenticated();

    }


    @Override
    public void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        //in memory authorized user hardcoded, get error no upgradeEncoding method?
        // no longer get error no specified password encoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        authManagerBuilder.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser("user").password(passwordEncoder.encode("password")).roles("USER");

        //Trying another way
//        authManagerBuilder.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user").password(passwordEncoder().encode("password")).authorities("ROLE_USER");
    }

//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
