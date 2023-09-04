package com.example.test.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.Base64Utils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class LoggingFilter implements Filter {

    private List<String> excludeList() {
        return List.of(
                "application/javascript",
                "text/html");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            long startTime = System.currentTimeMillis();
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String traceId = httpRequest.getHeader("traceId");
            if (traceId != null) {
                MDC.put("traceId", traceId);
            }
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpRequest);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResponse);
            log.info("[REQUEST] URL = {}, method = {}, headers = {}, param = {}, time = {}",
                    requestWrapper.getRequestURI(),
                    requestWrapper.getMethod(),
                    getRequestHeaders(requestWrapper),
                    getRequestBody(requestWrapper),
                    startTime
            );
            chain.doFilter(request, responseWrapper); // controller
            // 필터 체인 후, response logging
            log.info("[RESPONSE] headers = {}, status = {}, body = {}, elapsedTime = {}",
                    getResponseHeaders(responseWrapper),
                    responseWrapper.getStatus(),
                    getResponseBody(responseWrapper),
                    System.currentTimeMillis() - startTime
            );
            MDC.clear();
        }
    }

    private Map<String, Object> getRequestHeaders(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    private Map<String, Object> getRequestBody(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            params.put(paramName, request.getParameter(paramName));
        }
        return params;
    }

    private Map<String, Object> getResponseHeaders(HttpServletResponse response) {
        Map<String, Object> headers = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        headerNames.forEach(headerName -> {
            headers.put(headerName, response.getHeader(headerName));
        });
        headers.put("Content-Type", response.getContentType());
        return headers;
    }

    private String getResponseBody(HttpServletResponse response) throws IOException {
        String payload = null;
        // resultMessage 는 커스텀한 http header 값이며, 기본적으로 header 값에는 utf-8이 지원이 안되므로, base64 로 인코딩하여 주입해줌.
        // todo 추후에는 다른 필터를 새로 생성하여 넣어줘도 될 것으로 보임.
        encodeResultMessage(response);
        payload = getPayload(response);
        if (isLoggingExcludeContent(response)) payload = response.getContentType();
        payload = payload != null ? payload : "-";
        return payload;
    }

    private boolean isLoggingExcludeContent(HttpServletResponse response) {
        return false;
//        return excludeList()
//                .stream()
//                .anyMatch(a -> response.getContentType().contains(a));
    }

    private static String getPayload(HttpServletResponse response) throws IOException {
        String payload = null;
        ContentCachingResponseWrapper w = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        byte[] buf = w.getContentAsByteArray();
        if (buf.length > 0) {
            payload = new String(buf, 0, buf.length, w.getCharacterEncoding());
            w.copyBodyToResponse();
        }
        return payload;
    }

    private void encodeResultMessage(HttpServletResponse response) {
        if (response.getHeader("resultMessage") != null) {
            response.setHeader("resultMessage", Base64Utils.encodeToString(response.getHeader("resultMessage").getBytes(StandardCharsets.UTF_8)));
        }
    }
}
