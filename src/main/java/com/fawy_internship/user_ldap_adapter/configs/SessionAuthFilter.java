//package com.fawy_internship.user_ldap_adapter.configs;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//public class SessionAuthFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        // Skip filter for login endpoint
//        if (request.getRequestURI().startsWith("/auth/login")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String sessionId = request.getHeader("X-Session-ID");
//        HttpSession session = null;
//
//        if (sessionId != null) {
//            session = request.getSession(false);
//            if (session != null && sessionId.equals(session.getId())) {
//                // Get the existing authentication from session
//                SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
//                if (context != null) {
//                    SecurityContextHolder.setContext(context);
//                }
//            }
//        }
//
//        try {
//            filterChain.doFilter(request, response);
//        } finally {
//            // Clear context if not authenticated
//            if (session == null || session.getAttribute("SPRING_SECURITY_CONTEXT") == null) {
//                SecurityContextHolder.clearContext();
//            }
//        }
//    }
//}