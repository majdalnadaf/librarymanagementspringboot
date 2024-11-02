package com.librarymanagement.librarymanagement.infrastrcture.Security;


import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final int indexOfToken = 7;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
    ) throws ServletException, IOException
    {





       final String authHeader = request.getHeader("Authorization");
       final String token;
       final String userEmail;

       if(authHeader == null || !authHeader.startsWith("Bearer"))
       {
           filterChain.doFilter(request,response);
           return;
       }

       token = authHeader.substring(indexOfToken);
       userEmail = jwtService.extractEmail(token);
       if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
       {
           UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
           if(jwtService.isValidToken(token,userDetails))
           {
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                       userDetails,
                       null,
                       userDetails.getAuthorities()
               );

               authToken.setDetails(
                       new WebAuthenticationDetailsSource().buildDetails(request)
               );

               SecurityContextHolder.getContext().setAuthentication(authToken);



           }
       }
       filterChain.doFilter(request,response);


    }
}
