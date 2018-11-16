package pl.aw.iventz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/event/*").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/event/addComment/*").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().permitAll()
                .and().csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("userEmail")
                .passwordParameter("userPassword")
                .loginProcessingUrl("/processing-login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?status=err")
                .and()
                .logout()
                .logoutSuccessUrl("/");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("SELECT u.email, u.password, 1 FROM user u WHERE u.email = ?")
                .authoritiesByUsernameQuery("SELECT u.EMAIL, ur.ROLE_NAME FROM USER u JOIN USERS_WITH_ROLES uwr ON u.id=uwr.USER_ID JOIN USER_ROLE ur ON uwr.USER_ROLES_ID=ur.ID WHERE u.email = ?")
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
