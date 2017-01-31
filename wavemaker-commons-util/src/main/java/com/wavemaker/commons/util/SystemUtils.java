/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.util;

/**
 * @author Simon Toens
 */
public abstract class SystemUtils {

    private static final byte[] KEY = { 12, 7, 28, 127, 97, 69, 77, 122, 11 };

    private static final String ENCRYPTED_PREFIX = "wm_-+";

    private static final String ENCRYPTED_SUFFIX = "==wm-_";

    private SystemUtils() {
    }

    public static String encrypt(String s) {
        s = ENCRYPTED_PREFIX + s + ENCRYPTED_SUFFIX;
        StringBuilder rtn = new StringBuilder(s.length());
        byte[] bytes = s.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            rtn.append(encrypt(bytes[i], KEY[i % KEY.length]));
        }
        return rtn.toString();
    }

    public static String encryptIfNotEncrypted(String s) {
        return isEncrypted(s) ? s : encrypt(s);
    }

    public static String decrypt(String s) {
        return decrypt(s, true);
    }

    public static String decryptIfEncrypted(String s) {
        return isEncrypted(s) ? decrypt(s) : s;
    }

    private static String decrypt(String s, boolean removeMarkers) {
        byte[] rtn = new byte[s.length() / 2];
        int j = 0;
        for (int i = 0; i < s.length(); i += 2) {
            String hex = s.substring(i, i + 2);
            rtn[j] = (byte) (Integer.parseInt(hex, 16) ^ KEY[j % KEY.length]);
            j++;
        }
        String d = new String(rtn);
        if (removeMarkers && hasMarkers(d)) {
            return d.substring(ENCRYPTED_PREFIX.length(), d.length() - ENCRYPTED_SUFFIX.length());
        }
        return d;
    }

    public static boolean isEncrypted(String s) {
        if (s == null) {
            return false;
        }
        try {
            String d = decrypt(s, false);
            return hasMarkers(d);
        } catch (RuntimeException ex) {
            return false;
        }
    }

    private static boolean hasMarkers(String s) {
        return s.startsWith(ENCRYPTED_PREFIX) && s.endsWith(ENCRYPTED_SUFFIX);
    }

    private static String encrypt(byte b, byte key) {
        b = (byte) (b ^ key);
        String rtn = Integer.toHexString(b);
        if (rtn.length() == 1) {
            rtn = "0" + rtn;
        }
        return rtn;
    }

    /**
     * Converts byte array to long, assuming the bytes are unsigned.
     */
    public static long getUnsignedValue(byte[] bytes) {

        long rtn = 0;
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            boolean isSignBitSet = (b & 128) == 128;
            if (isSignBitSet) {
                // unset sign bit before 'or'ing with rtn
                b = (byte) (b & 127);
            }
            rtn |= b;
            if (isSignBitSet) {
                // add 'sign' bit as regular bit
                rtn |= 128;
            }

            if (i < bytes.length - 1) {
                rtn <<= 8; // 8 because unsigned
            }
        }

        return rtn;
    }

    public static Throwable getRootException(Throwable th) {

        while (th.getCause() != null) {
            th = th.getCause();
        }

        return th;
    }

    /**
     * Get the native line separator.
     * 
     * @return The property line.separator as a String.
     */
    public static String getLineBreak() {
        return org.apache.commons.lang3.SystemUtils.LINE_SEPARATOR;
    }

}