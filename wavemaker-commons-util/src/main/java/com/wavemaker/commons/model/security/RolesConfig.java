package com.wavemaker.commons.model.security;

import java.util.Map;
import java.util.Objects;

/**
 * @author Uday Shankar
 */
public class RolesConfig {

    private Map<String, RoleConfig> roleMap;

    public Map<String, RoleConfig> getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(Map<String, RoleConfig> roleMap) {
        this.roleMap = roleMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesConfig that = (RolesConfig) o;
        return Objects.equals(roleMap, that.roleMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleMap);
    }

}
