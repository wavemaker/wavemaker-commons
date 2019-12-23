package com.wavemaker.commons.util;

/**
 * @author Uday Shankar
 */

public class Version implements Comparable<Version> {

    private String version;

    public final String get() {
        return this.version;
    }

    public Version(String version) {
        if (version == null) {
            throw new IllegalArgumentException("Version can not be null");
        }
        if (!version.matches("[0-9]+(\\.[0-9]+)*")) {
            throw new IllegalArgumentException("Invalid version format, " + version);
        }
        this.version = version;
    }

    @Override
    public int compareTo(Version that) {
        if (that == null) {
            return 1;
        }
        String[] thisParts = this.get().split("\\.");
        String[] thatParts = that.get().split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for(int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
            if (thisPart < thatPart) {
                return -1;
            }
            if (thisPart > thatPart) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int hashCode() {
        return get().hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        return this.compareTo((Version) that) == 0;
    }

    @Override
    public String toString() {
        return "Version{" +
                "version='" + version + '\'' +
                '}';
    }

    public static boolean lessThan(String v1, String v2) {
        return new Version(v1).compareTo(new Version(v2)) < 0;
    }

    public static boolean lessThanOrEqualTo(String v1, String v2) {
        return new Version(v1).compareTo(new Version(v2)) <= 0;
    }

    public static boolean greaterThan(String v1, String v2) {
        return new Version(v1).compareTo(new Version(v2)) > 0;
    }

    public static boolean greaterThanOrEqualTo(String v1, String v2) {
        return new Version(v1).compareTo(new Version(v2)) >= 0;
    }

}