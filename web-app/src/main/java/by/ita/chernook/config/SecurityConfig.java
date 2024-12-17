package by.ita.chernook.config;

import by.ita.chernook.dto.enums.UserRoleEnum;
import by.ita.chernook.model.CustomUserDetails;
import by.ita.chernook.model.Customer;
import by.ita.chernook.model.User;
import by.ita.chernook.service.CartService;
import by.ita.chernook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final CartService cartService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/cart").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/customer/**").hasAuthority("CUSTOMER")
                //.anyRequest().authenticated() // все остальные запросы требуют аутентификации
            .and()
            .formLogin() // настройка формы входа
                .loginPage("/login")
                .permitAll()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                        boolean isAdmin = authentication.getAuthorities().stream()
                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

                        if (!isAdmin) {
                            UUID cartUuid = userService.findCustomerById(userDetails.getCustomerUuid()).getCart().getUuid();

                            UUID tempCartUuid = (UUID) request.getSession().getAttribute("cart");
                            if (tempCartUuid != null) {
                                cartService.transfer(cartUuid, tempCartUuid);
                            }
                            request.getSession().removeAttribute("discountedTotalPrice");
                            request.getSession().removeAttribute("coupon");
                            request.getSession().setAttribute("cart", cartUuid);
                            response.sendRedirect("/");

                        } else {
                            response.sendRedirect("/admin/");
                        }

                    }
                })
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userService.findUserByLogin(username);
                if (user == null) {
                    throw new UsernameNotFoundException("User  not found");
                }

                Customer customer = null;
                if (user.getUserRoleEnum() == UserRoleEnum.CUSTOMER) {
                    customer = userService.findCustomerByUserId(user.getUuid());
                }

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getUserRoleEnum().toString());

                return new CustomUserDetails(
                        user.getLogin(),
                        user.getPassword(),
                        customer != null ? customer.getUuid() : null,
                        Collections.singletonList(authority)
                );
            }
        }).passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}