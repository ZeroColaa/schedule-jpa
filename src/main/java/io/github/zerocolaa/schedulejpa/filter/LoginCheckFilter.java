package io.github.zerocolaa.schedulejpa.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {


    private static final String[] WHITE_LIST = {
            "/", "/authors/signup", "/authors/login"
    };

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        log.info("필터 적용 URI = {}", requestURI);


        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute("LOGIN_USER_ID") == null) {
                log.warn("비로그인 사용자 요청 - URI: {}", requestURI);
                httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인 필요");
                return;
            }

            log.info("로그인된 사용자 - ID: {}", session.getAttribute("LOGIN_USER_ID"));
        }

        chain.doFilter(request, response);
    }


    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
