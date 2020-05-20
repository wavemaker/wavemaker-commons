package com.wavemaker.commons.auth.oauth2;

public enum OAuth2Flow {
    AUTHORIZATION_CODE("accessCode"),
    IMPLICIT("implicit");

    private String value;

    OAuth2Flow(String value) {
        this.value = value;
    }

    public static OAuth2Flow fromValue(String flow) {
        if (flow != null) {
            for (OAuth2Flow oAuthFlow : values()) {
                if (flow.equals(oAuthFlow.toString()) || flow.equals(oAuthFlow.value)) {
                    return oAuthFlow;
                }
            }
        }
        return OAuth2Flow.valueOf(flow);
    }
}
