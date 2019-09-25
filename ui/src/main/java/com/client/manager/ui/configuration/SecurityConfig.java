package com.client.manager.ui.configuration;

import com.client.manager.entities.role.RoleDefinedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private ResourceOwnerPasswordResourceDetails oauth2UserPasswordResource;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new SCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        setAccessDeniedHandler(http);
        configureLogin(http);
        configureLogout(http);

        secureIndexResource(http);
        secureCustomerResource(http);
        secureCompanyResource(http);
    }

    public void setAccessDeniedHandler(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandlerImpl());
    }

    public void configureLogin(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .successHandler(authenticationSuccessHandler())
                .loginPage("/login")
                .permitAll();
    }

    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) -> {
            oauth2UserPasswordResource.setUsername(httpServletRequest.getParameter("username"));
            oauth2UserPasswordResource.setPassword(httpServletRequest.getParameter("password"));
            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "");
        };
    }

    public void configureLogout(HttpSecurity http) throws Exception {
        http
                .logout()
                .permitAll();
    }

    public void secureIndexResource(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/index")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'," +
                                "'" + RoleDefinedValue.ROLE_USER.name() + "'" +
                                ")"
                );
    }

    public void secureCustomerResource(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/customer/list")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'," +
                                "'" + RoleDefinedValue.ROLE_USER.name() + "'" +
                                ")"
                )
                .antMatchers(HttpMethod.GET, "/customer/edit/{customerId}")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                                ")"
                )
                .antMatchers(HttpMethod.DELETE, "/customer/delete/{customerId}")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                                ")"
                )
                .antMatchers(HttpMethod.POST, "/customer/update")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                                ")"
                )
                .antMatchers(HttpMethod.PUT, "/customer/save")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                                ")"
                )
                .antMatchers(HttpMethod.GET, "/customer/new")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                                ")"
                );
    }

    public void secureCompanyResource(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/company/list")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'," +
                                "'" + RoleDefinedValue.ROLE_USER.name() + "'" +
                                ")"
                )
                .antMatchers(HttpMethod.GET, "/company/edit/{companyId}")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                                ")"
                )
                .antMatchers(HttpMethod.DELETE, "/customer/delete/{companyId}")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                                ")"
                )
                .antMatchers(HttpMethod.POST, "/company/update")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                                ")"
                )
                .antMatchers(HttpMethod.PUT, "/company/save")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                                ")"
                )
                .antMatchers(HttpMethod.GET, "/company/new")
                .access(
                        "hasAnyRole(" +
                                "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                                ")"
                );
    }
}