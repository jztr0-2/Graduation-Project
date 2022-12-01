package com.poly.jztr.ecommerce.configuration.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJwt(request);
            token= token+"";
            System.out.println("Line 29 JwtTokenFilter");
            System.out.println(token);
            if (!token.equals("null")) {
               // System.out.println(jwtProvider.validateTokenLogin(token));
                if (jwtProvider.validateTokenLogin(token)) {
                    String username = jwtProvider.getUsernameFromToken(token);
                    Map<String,Object> role = jwtProvider.getClaimsSetFromToken(token).getClaims();
//                    System.out.println("Request: " + request.getRequestURI());
//                    if(request.getRequestURI().contains("admin")){
//                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                        response.getWriter().write("Unauthorized");
//                        return;
//                    };
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null,
                                    userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}