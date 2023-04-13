package com.example.diplomawork.jwt;

import com.example.diplomawork.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String headerFromRequest = request.getHeader("auth-token");

        if (ObjectUtils.isEmpty(headerFromRequest) || !headerFromRequest.startsWith("Bearer ")) {
            log.warn("Jwt Token does not begin with Bearer String");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = headerFromRequest.split(" ")[1].trim();

        if (!jwtTokenProvider.validateToken(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(jwtToken, request);
        filterChain.doFilter(request, response);

    }

    private void setAuthenticationContext(String jwtToken, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(jwtToken);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, null);

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String jwtToken) {
        User userDetails = new User();
        userDetails.setEmail(jwtTokenProvider.getSubjectFromToken(jwtToken));
        return userDetails;
    }
}
