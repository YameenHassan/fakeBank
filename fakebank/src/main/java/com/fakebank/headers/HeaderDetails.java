package com.fakebank.headers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeaderDetails {
    private String userUuid;
    private String correlationId;
    private String clientId;
    private String clientSecret;
    private String httpMethod;
}
