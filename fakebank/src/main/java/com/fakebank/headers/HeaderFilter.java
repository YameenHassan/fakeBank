package com.fakebank.headers;

import com.fakebank.exceptions.ApiError;
import com.fakebank.utils.Constants;
import com.fakebank.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import java.io.IOException;

import static com.fakebank.utils.Utils.isNotNullOrEmpty;
import static com.fakebank.utils.Utils.isNullOrEmpty;
import static org.springframework.http.HttpStatus.*;

@Component
@Slf4j
public class HeaderFilter extends OncePerRequestFilter {

    @Value("${env}")
    private String ENV;

    @Value("${spring.application.name}")
    private String SPRING_APPLICATION_NAME;

    private static final String USER_UUID = "user-uuid";
    public static final String S37_CORRELATION_ID = "s37-correlation-id";

    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            HeaderDetails headerDetails = HeaderDetails.builder()
                    .userUuid(isNotNullOrEmpty(request.getHeader(USER_UUID)) ? request.getHeader(USER_UUID) : "")
                    .clientId(isNotNullOrEmpty(request.getHeader(Constants.CLIENT_ID)) ? request.getHeader(Constants.CLIENT_ID) : "")
                    .clientSecret(isNotNullOrEmpty(request.getHeader(Constants.CLIENT_SECRET)) ? request.getHeader(Constants.CLIENT_SECRET) : "")
                    .correlationId(isNotNullOrEmpty(request.getHeader(S37_CORRELATION_ID))? request.getHeader(S37_CORRELATION_ID) : Utils.generateUniqueCorrelationId(ENV, SPRING_APPLICATION_NAME))
                    .httpMethod(request.getMethod())
                    .build();
            log.debug("HeaderDetails: " + headerDetails);
            HeaderDetailsStorage.setHeaderDetails(headerDetails);
            MDC.put(CORRELATION_ID_LOG_VAR_NAME, headerDetails.getCorrelationId());
            filterChain.doFilter(request, response);
            HeaderDetailsStorage.clear();
            MDC.remove(CORRELATION_ID_LOG_VAR_NAME);
        }
}