package com.client.manager.ws.configuration;

import com.client.manager.entities.role.RoleDefinedValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceId);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        setAccessDeniedHandler(http);

        freeH2Resources(http);

        secureUserResource(http);
        secureCustomerResource(http);
        secureCompanyResource(http);
    }

    public void freeH2Resources(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**")
                .permitAll();
        http.headers().frameOptions().disable();
    }

    public void setAccessDeniedHandler(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

    public void secureUserResource(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/{username}")
                .access(SECURED_READ_SCOPE);
    }

    public void secureCustomerResource(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/customer")
                .access(SECURED_READ_SCOPE +
                        " and hasAnyRole(" +
                        "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'," +
                        "'" + RoleDefinedValue.ROLE_USER.name() + "'" +
                        ")"
                )
                .antMatchers(HttpMethod.GET, "/customer/{customerId}")
                .access(SECURED_READ_SCOPE +
                        " and hasAnyRole(" +
                        "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'," +
                        "'" + RoleDefinedValue.ROLE_USER.name() + "'" +
                        ")"
                )
                .antMatchers(HttpMethod.DELETE, "/customer/{customerId}")
                .access(SECURED_READ_SCOPE +
                        " and " + SECURED_WRITE_SCOPE +
                        " and hasAnyRole(" +
                        "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                        ")"
                )
                .antMatchers(HttpMethod.POST, "/customer")
                .access(SECURED_READ_SCOPE +
                        " and " + SECURED_WRITE_SCOPE +
                        " and hasAnyRole(" +
                        "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                        ")"
                )
                .antMatchers(HttpMethod.PUT, "/customer")
                .access(SECURED_READ_SCOPE +
                        " and " + SECURED_WRITE_SCOPE +
                        " and hasAnyRole(" +
                        "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                        ")"
                );
    }

    public void secureCompanyResource(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/company")
                .access(SECURED_READ_SCOPE +
                        " and hasAnyRole(" +
                        "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'," +
                        "'" + RoleDefinedValue.ROLE_USER.name() + "'" +
                        ")"
                )
                .antMatchers(HttpMethod.GET, "/company/{companyId}")
                .access(SECURED_READ_SCOPE +
                        " and hasAnyRole(" +
                        "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                        ")"
                )
                .antMatchers(HttpMethod.DELETE, "/company/{companyId}")
                .access(SECURED_READ_SCOPE +
                        " and " + SECURED_WRITE_SCOPE +
                        " and hasAnyRole(" +
                        "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                        ")"
                )
                .antMatchers(HttpMethod.POST, "/company")
                .access(SECURED_READ_SCOPE +
                        " and " + SECURED_WRITE_SCOPE +
                        " and hasAnyRole(" +
                        "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                        ")"
                )
                .antMatchers(HttpMethod.PUT, "/company")
                .access(SECURED_READ_SCOPE +
                        " and " + SECURED_WRITE_SCOPE +
                        " and hasAnyRole(" +
                        "'" + RoleDefinedValue.ROLE_ADMIN.name() + "'" +
                        ")"
                );
    }
}