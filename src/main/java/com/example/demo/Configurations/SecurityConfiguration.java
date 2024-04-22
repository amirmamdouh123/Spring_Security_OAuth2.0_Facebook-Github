package com.example.demo.Configurations;

//import com.example.demo.Filters.AuthFilter;
import com.example.demo.CustomAuthenticationEntry.CustomAuthenticationEntryPoint;
import com.example.demo.Filters.AuthFilter;
import com.example.demo.Filters.TXFilter;
//import com.example.demo.Filters.UserPasswordFilter;
import com.example.demo.Services.UserService;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.SupplierJwtDecoder;
//import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import javax.servlet.Filter;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    UserService userService;

    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

//    @Autowired
//    DataSource dataSource;


//    @Bean
//    public UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
//        UsernamePasswordAuthenticationFilter authenticationFilter = new UsernamePasswordAuthenticationFilter();
//        authenticationFilter.setAuthenticationManager(authenticationManager());
//        return authenticationFilter;
//    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user= User.withUsername("amir").password(passwordEncoder().encode("1234")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user);
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf((csrf) -> csrf.disable())
//                .authorizeRequests((request)-> request
//                        .requestMatchers("/", "/login**", "/error**") // Allow access to home, login, and error pages
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated())
//                .oauth2Login((a)->a.defaultSuccessUrl("/home")
//                .failureUrl("/login-error"));
////                .exceptionHandling((exception)-> exception.authenticationEntryPoint(customAuthenticationEntryPoint))
////                .httpBasic(Customizer.withDefaults())
////                .logout(Customizer.withDefaults()); // Redirect to login page after logout;)
//        return http.build();
//    }



    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/","/loginJWT/**", "/login**", "/error**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .clientRegistrationRepository(clientRegistrationRepository())
                                .defaultSuccessUrl("/home")
                                .failureUrl("/login-error")
                );
        return http.build();
    }



    @Bean
    public ProviderManager providerManager(){
//        List<AuthenticationProvider> providers = Arrays.
//                asList( dao() );
        return new ProviderManager( daoAuthenticate() );
    }

    @Bean
    public AuthenticationProvider daoAuthenticate(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }





//    @Bean
//    public ClientRegistration facebookClientRegistration() {
//        return ClientRegistration.withRegistrationId("facebook")
//                .clientId("facebook-client-id")
//                .clientSecret("facebook-client-secret")
//                .redirectUri("{baseUrl}/login/oauth2/code/facebook")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .userInfoUri("https://graph.facebook.com/me")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .clientName("Facebook")
//                .build();
//    }

    @Bean
    public ClientRegistration githubClientRegistration() {
        return ClientRegistration.withRegistrationId("github")
                .clientId("347d9096694e93c39e99")
                .clientSecret("7bbd61545cf0b5433154ea5305a7debe1369bfe3")
                .redirectUri("{baseUrl}/login/oauth2/code/github")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("https://github.com/login/oauth/authorize") // Add authorizationUri
                .tokenUri("https://github.com/login/oauth/access_token") // Add tokenUri
                .userInfoUri("https://api.github.com/user")
                .userNameAttributeName("id")
                .clientName("GitHub")
                .build();
    }


    @Bean
    public ClientRegistration facebookClientRegistration() {
        return ClientRegistration.withRegistrationId("facebook")
                .clientId("358195056672072")
                .clientSecret("4ec70c68ad12a6effa5641eafab6ca2e")
                .redirectUri("{baseUrl}/login/oauth2/code/facebook")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("https://www.facebook.com/v13.0/dialog/oauth")
                .tokenUri("https://graph.facebook.com/v13.0/oauth/access_token") // Add tokenUri
                .userInfoUri("https://graph.facebook.com/me")
                .userNameAttributeName("id")
                .clientName("Facebook")
                .build();
    }

    @Bean
    public InMemoryClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository( githubClientRegistration(),facebookClientRegistration());
    }


}









