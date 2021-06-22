package code.config;

import code.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    /*
     * authentic password encoding
     * */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    /*
     * decentralization
     * permission*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();// khong cho phep goi cac trang web khac duoi main

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/", "login", "logout","/register").permitAll(); //no authentication required

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/changePass","/userInfo")
                .access( "hasAnyRole('role_member','role_admin')");

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/deleteUser")
                .access("hasRole('role_admin')");

        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");// access denied

        //cấu hình đăng nhập
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/login_backend")
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")

                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccess");

    }
}
