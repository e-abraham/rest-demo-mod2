package com.example.restdemo.configs;

import com.example.restdemo.services.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
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
        //all requests protected using Basic Auth
        httpSecurity.authorizeRequests().anyRequest().authenticated().and().httpBasic();

//        httpSecurity.authorizeRequests().antMatchers("/rest-demo/authors").permitAll();


        // ** Trying another way **
//        httpSecurity.authorizeRequests()
//                //these routes won't require authentication, all others will
//                .antMatchers("/", "/rest-demo", "/rest-demo/hello", "/rest-demo/hello/name/*").permitAll().anyRequest().authenticated();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        //in memory authorized user hardcoded, error no specified password encoder?
//        authManagerBuilder.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password")).authorities("ROLE_USER");

        //Trying another way
        authManagerBuilder.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user").password(passwordEncoder().encode("password")).authorities("ROLE_USER");
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
