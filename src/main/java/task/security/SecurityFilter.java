package task.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import task.service.CustomUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenCreator creator;

    @Autowired
    private CustomUserDetailsService detailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws SecurityException, ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (StringUtils.hasText(token) && creator.validateToken(token)) {
            String username = creator.getNameFromToken(token);
            UserDetails details = detailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    details,null, details.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        } else return null;
    }
}
