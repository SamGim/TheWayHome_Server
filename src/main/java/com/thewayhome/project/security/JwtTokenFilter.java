package com.thewayhome.project.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JwtTokenFilter extends AbstractAuthenticationProcessingFilter {
    final String AUTH_HEADER_NAME = "Authorization";
    final String BEARER_NAME = "Bearer";

    final Map<String, List<HttpMethod>> EXCLUDED_PATHS = new HashMap<>();

//    @Cacheable(value = "accessTokenCache", key = "#token")
//    public String getAccessTokenFromCache(String token) {
//        // 캐시에서 토큰 값을 가져오는 로직
//        Cache cache = cacheManager.getCache("accessTokenCache");
//        if (cache != null) {
//            Cache.ValueWrapper valueWrapper = cache.get(token);
//            if (valueWrapper != null) {
//                return valueWrapper.get().toString();
//            }
//        }
//        return null; // 캐시에 값이 없으면 null 반환
//    }


    public JwtTokenFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/**"));
        setAuthenticationManager(authenticationManager);
        // "/device/**" 경로에서 HTTP GET 메소드를 제외
//        EXCLUDED_PATHS.put("/swagger-ui/", List.of(HttpMethod.GET));
//        EXCLUDED_PATHS.put("/webjars/", List.of(HttpMethod.GET));
        EXCLUDED_PATHS.put("/api-docs", List.of(HttpMethod.GET));
        EXCLUDED_PATHS.put("/swagger-ui/", List.of(HttpMethod.GET));
        EXCLUDED_PATHS.put("/register", List.of(HttpMethod.POST));
//        EXCLUDED_PATHS.put("/v2/api-docs", List.of(HttpMethod.GET));
    }



    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getRequestURI().substring(request.getContextPath().length());
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        // 경로와 HTTP 메소드가 일치하는 경우 필터링을 제외
        for (String key : EXCLUDED_PATHS.keySet()) {
            if (!path.contains(key))
                continue;
            List<HttpMethod> values = EXCLUDED_PATHS.get(key);
            if (!values.contains(method))
                continue;
            chain.doFilter(req, res);
            return;
        }

        super.doFilter(req, res, chain);
    }

    // public JwtTokenFilter(AuthenticationManager authenticationManager) {
    // super(new OrRequestMatcher(
    // new AntPathRequestMatcher("/customer/**"),
    // new AntPathRequestMatcher("/company/**"),
    // new AntPathRequestMatcher("/device/**", "GET"),
    // new AntPathRequestMatcher("/hqUser", "GET")
    // ));
    // setAuthenticationManager(authenticationManager);
    //
    // }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        System.out.println("enter JwtTokenFilter.attemptAuthentication");
        String token = extractToken(request);
//        String cachedToken = getAccessTokenFromCache(token); // 캐시에서 토큰 가져오기
//        token = cachedToken != null ? cachedToken : token; // 캐시에 값이 있으면 캐시 값으로 토큰 설정
        if (StringUtils.hasText(token) ) {
            try{
                return this.getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
            } catch (Exception e){
                log.error("e = {}", e.getMessage());
//                throw new AuthenticationException(e.getMessage()) {
//                };

                // status-line
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                // response header
                response.setContentType("text/plain");
                response.setCharacterEncoding("utf-8");

                PrintWriter writer = response.getWriter();
                writer.println(e.getMessage());

                return null;
            }

        }
        throw new AuthenticationCredentialsNotFoundException("No JWT token found in request headers");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("enter JwtTokenFilter.successfulAuthentication");
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER_NAME);
        if (authHeader == null || authHeader.isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Auth header does not exists");
        }
        String[] authHeaderValue = authHeader.split(" ");
        if (authHeaderValue.length != 2) {
            throw new AuthenticationCredentialsNotFoundException("invalid auth header, require token");
        }
        if (!BEARER_NAME.equals(authHeaderValue[0])) {
            throw new BadCredentialsException("invalid auth header, require bearer");
        }
        return authHeaderValue[1];
    }
}