package com.fakebank.exceptions;

import com.fakebank.dto.ResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ApiError {

    private String correlationId;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd hh:mm:ss"
    )
    private LocalDateTime timestamp;
    private ResponseDto responseDto;
    @JsonIgnore
    private HttpStatus status;
    @JsonIgnore
    private String message;
    @JsonIgnore
    private String debugMessage;

    private ApiError() {
        this.timestamp = LocalDateTime.now();
        this.correlationId = MDC.get("correlationId");
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
        Map<String, String> statusSetMap = new HashMap();
        statusSetMap.put("errorCode", String.valueOf(status.value()));
        statusSetMap.put("moreInfo", this.getMessage());
        statusSetMap.put("details", this.getDebugMessage());
        this.responseDto = ResponseDto.error(statusSetMap);
    }


    public String getCorrelationId() {
        return this.correlationId;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDebugMessage() {
        return this.debugMessage;
    }

    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd hh:mm:ss"
    )
    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @JsonIgnore
    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    @JsonIgnore
    public void setMessage(final String message) {
        this.message = message;
    }

    @JsonIgnore
    public void setDebugMessage(final String debugMessage) {
        this.debugMessage = debugMessage;
    }

}
