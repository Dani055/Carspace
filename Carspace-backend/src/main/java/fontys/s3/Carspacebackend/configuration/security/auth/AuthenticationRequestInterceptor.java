package fontys.s3.Carspacebackend.configuration.security.auth;

import fontys.s3.Carspacebackend.business.jwt.IAccessTokenHelper;
import fontys.s3.Carspacebackend.domain.AccessToken;
import fontys.s3.Carspacebackend.exception.BadTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//It will look for a JWT token inside an authentication bearer HTTP header.
// If one is found then it will be verified, decoded and the authenticated user will be configured into Spring Security context.
@Component
public class AuthenticationRequestInterceptor extends OncePerRequestFilter {
    private final static String SPRING_SECURITY_ROLE_PREFIX = "ROLE_";

    @Autowired
    private IAccessTokenHelper accessTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String reqToken = requestTokenHeader.substring(7);

        try {
            AccessToken accessToken = accessTokenHelper.decode(reqToken);
            setupSpringSecurityContext(accessToken);
            chain.doFilter(request, response);
        } catch (BadTokenException e) {
            logger.error("Error validating access token", e);
            sendAuthenticationError(response, e);
        }
    }

    private void sendAuthenticationError(HttpServletResponse response, BadTokenException e) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        response.flushBuffer();
    }

    private void setupSpringSecurityContext(AccessToken accessToken) {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(SPRING_SECURITY_ROLE_PREFIX + accessToken.getRole()));
        UserDetails userDetails = new User(accessToken.getUsername(), "",
                roles);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(accessToken);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
