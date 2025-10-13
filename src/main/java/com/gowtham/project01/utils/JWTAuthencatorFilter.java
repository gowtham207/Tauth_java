// package com.gowtham.project01.utils;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.gowtham.project01.models.UserModel;
// import com.gowtham.project01.repo.UserRepo;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class JWTAuthencatorFilter extends OncePerRequestFilter {

//     @Autowired
//     private JWTUtils jwtUtils;

//     @Autowired
//     private UserRepo userRepo;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//             FilterChain filterChain) throws ServletException, java.io.IOException {
//         String authHeader = request.getHeader("Authorization");

//         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             response.getWriter().write("Missing or invalid Authorization header");
//             return;
//         }

//         String token = authHeader.substring(7);
//         String userId = jwtUtils.GetUserIdFromToken(token);

//         if (userId == null) {
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             response.getWriter().write("Invalid token");
//             return;
//         }

//         if (jwtUtils.IsTokenValid(token)) {

//             UserModel user = userRepo.findByUserId(java.util.UUID.fromString(userId));
//             if (user == null) {
//                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                 response.getWriter().write("User not found");
//                 return;
//             }

//             filterChain.doFilter(request, response);
//         } else {
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             response.getWriter().write("Invalid token");
//             return;
//         }

//     }

// }
