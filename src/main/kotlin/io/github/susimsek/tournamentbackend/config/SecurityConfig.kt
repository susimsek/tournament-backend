package io.github.susimsek.tournamentbackend.config

import io.github.susimsek.tournamentbackend.security.jwt.JWTConfigurer
import io.github.susimsek.tournamentbackend.security.jwt.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter
import org.springframework.web.filter.CorsFilter


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val corsFilter: CorsFilter
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    override fun configure(web: WebSecurity?) {
        web!!.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/playground", "/vendor/playground/**")
            .antMatchers("/subscriptions")
    }


    @Throws(Exception::class)
    public override fun configure(http: HttpSecurity) {
        http
            .csrf()
            .disable()
            .httpBasic()
            .disable()
            .logout()
            .disable()
            .addFilterBefore(corsFilter, RequestHeaderAuthenticationFilter::class.java)
            .headers()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
        .and()
            .frameOptions()
            .deny()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/graphql").permitAll()
            .anyRequest().authenticated()
        .and()
            .apply(securityConfigurerAdapter())
    }

    private fun securityConfigurerAdapter() = JWTConfigurer(tokenProvider)
}
