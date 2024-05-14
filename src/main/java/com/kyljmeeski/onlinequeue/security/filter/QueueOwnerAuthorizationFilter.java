package com.kyljmeeski.onlinequeue.security.filter;

import com.kyljmeeski.onlinequeue.entity.User;
import com.kyljmeeski.onlinequeue.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class QueueOwnerAuthorizationFilter extends OncePerRequestFilter {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final UserRepository userRepository;
    private final String URI_PATTERN = "/queues/{id}/next";

    public QueueOwnerAuthorizationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        long queueId = Long.parseLong(
                pathMatcher.extractUriTemplateVariables(URI_PATTERN, request.getServletPath()).get("id")
        );
        Optional<User> optionalUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        if (optionalUser.isEmpty()) {
            response.setStatus(HttpStatus.FOUND.value());
            return;
        } else if (!userRepository.checkIfUserHasSuchQueue(optionalUser.get().getId(), queueId)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("You can't access this queue");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !pathMatcher.match(URI_PATTERN, request.getServletPath());
    }
}
