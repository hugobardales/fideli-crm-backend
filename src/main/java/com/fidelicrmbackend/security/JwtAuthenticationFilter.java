package com.fidelicrmbackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// "OncePerRequestFilter" Es una clase base (abstracta) que se utiliza para crear filtros de seguridad o de procesamiento que garantizan una
// única ejecución por cada solicitud HTTP.

/**
 * Filter that executes once per request to validate JWT tokens.
 * <p>
 * If a valid token is found in the "Authorization" header,
 * the user is authenticated and set in the SecurityContext.
 * </p>
 *
 * @author Hugo Bardales
 * @since 1.0.0
 */
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

}
