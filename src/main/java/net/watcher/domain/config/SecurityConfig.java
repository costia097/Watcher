package net.watcher.domain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SecurityConfig config functionality try secure application
 *
 * @author Kostia
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * .csrf().disable()
     *
     * @param http id of user
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/logOut").permitAll()
                .antMatchers("/signUp").permitAll()
                .antMatchers("/confirm/*").permitAll()
                .antMatchers("/allLoginsAndEmails").permitAll()
                .anyRequest().authenticated();
    }

}
