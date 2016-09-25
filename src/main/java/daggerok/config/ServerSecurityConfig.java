package daggerok.config;

import daggerok.admin.userdetails.AdminUserDetailsService;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@EnableWebSecurity
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.ldap.url}")
    String url;

    @Value("${app.ldap.referral}")
    String referral;

    @Value("${app.ldap.user-name}")
    String userName;

    @Value("${app.ldap.password}")
    String password;

    @Value("${app.ldap.user-filter}")
    String userFilter;

    @Value("${app.ldap.group-filter}")
    String groupFilter;

    @Value("${management.context-path}")
    String contextPath;

    @Autowired
    AdminUserDetailsService adminUserDetailsService;

    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers("/", "/app.js", "/app.css").permitAll()
                .antMatchers("/admin**").hasAuthority("ADMIN")
                .anyRequest().fullyAuthenticated()
            .and()
                .formLogin()
            .and()
                .csrf().disable();
        // @formatter:on
    }

    @Override
    public UserDetailsService userDetailsServiceBean() {
        return adminUserDetailsService;
    }

    @Override
    @Autowired
    @SneakyThrows
    protected void configure(AuthenticationManagerBuilder auth) {

        val contextSource = new DefaultSpringSecurityContextSource(url);

        contextSource.setReferral(referral);
        contextSource.setUserDn(userName);
        contextSource.setPassword(password);
        contextSource.afterPropertiesSet();

        // @formatter:off
        auth // in memory hash map:
            .inMemoryAuthentication()
                .withUser("admin")
                    .password("admin")
                    .roles("USER", "ADMIN")
                    .authorities(AuthorityUtils.createAuthorityList("USER", "ADMIN"))
                    .and()
                .and()
            // mongo db:
            .userDetailsService(userDetailsServiceBean())
                .passwordEncoder(new BCryptPasswordEncoder())
                .and()
            // ldap:
            .ldapAuthentication()
                    .groupSearchBase(groupFilter)
                    .userSearchFilter(userFilter)
                    .contextSource(contextSource);
        // @formatter:on
    }
}
