package com.wavemaker.commons.auth.oauth2;

public class OAuth2Pkce {
    private boolean enabled;
    private String challengeMethod;

    public OAuth2Pkce(boolean enabled, String challengeMethod) {
        this.enabled = enabled;
        this.challengeMethod = challengeMethod;
    }

    OAuth2Pkce() {
        this.enabled = false;
        this.challengeMethod = "";
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getChallengeMethod() {
        return challengeMethod;
    }

    public void setChallengeMethod(String challengeMethod) {
        this.challengeMethod = challengeMethod;
    }
}
