package com.wavemaker.studio.common.model;

/**
 * Created by Arjun Sahasranam on 29/6/15.
 */
public class RoleConfig {
    private String landingPage = null;

    public RoleConfig() {
    }

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }

    @Override
    public String toString() {
        return landingPage;
    }
}
