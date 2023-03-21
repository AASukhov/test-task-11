package task.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import task.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class AuthConfiguration {

    private EntryPoint entryPoint;
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthConfiguration (EntryPoint entryPoint, CustomUserDetailsService userDetailsService) {
        this.entryPoint = entryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager manager (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilter filter(){
        return new SecurityFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/register","/login")
                .permitAll()
                .anyRequest()
                .authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
