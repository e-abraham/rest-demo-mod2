package com.example.restdemo.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //OAuth uses audience & issuer
    @Value("${auth0.audience}")
    private String audience;

    @Value("${auth0.issuer}")
    private String issuer;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        //all requests protected using OAuth
        httpSecurity.authorizeRequests()
                .anyRequest()
                .authenticated()
                // ** IMPORTANT! do not use the line below in production apps!! **
                .and().csrf().disable()
                .cors()
                .and().oauth2ResourceServer().jwt();

        //all requests protected using Basic Auth except for antMatchers
//        httpSecurity
//                .authorizeRequests()
//                .antMatchers("/rest-demo/authors").permitAll()
//                .anyRequest()
//                .authenticated().and()
//                .httpBasic();

        // ** Trying another way Basic Auth **
//        httpSecurity.authorizeRequests()
//                //these routes won't require authentication, all others will
//                .antMatchers("/", "/rest-demo", "/rest-demo/hello", "/rest-demo/hello/name/*").permitAll().anyRequest().authenticated();
    }

    /**
     * Required to enable CORS - NOT suitable for production code!
     *
     * @return CorsConfigurationSource cors configuration
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        // ** IMPORTANT! do not use the line below in production apps!! **
        // ** Specify specific origins instead
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
                JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }


//    @Override
//    public void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        //in memory authorized user hardcoded basic auth
        // get error no upgradeEncoding method? no longer get error no specified password encoder
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        authManagerBuilder.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser("user").password(passwordEncoder.encode("password")).roles("USER");

        //Trying another way
//        authManagerBuilder.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user").password(passwordEncoder().encode("password")).authorities("ROLE_USER");
//    }

//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
