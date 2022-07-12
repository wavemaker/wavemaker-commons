package com.wavemaker.commons.util;

import java.io.BufferedWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class PatternMatchingReplaceWriter extends FilterWriter {

    private String prefix;
    private String suffix;

    private StringBuilder sb = new StringBuilder();

    private String value;

    private boolean prefixFound = false;

    private boolean suffixFound = false;

    public PatternMatchingReplaceWriter(Writer out, String prefix, String suffix, String value) {
        super(new BufferedWriter(out));
        this.prefix = prefix;
        this.suffix = suffix;
        this.value = value;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        int index = off;
        while (index < len) {
            if (sb.length() != 0 && !prefixFound) {
                super.write(sb.toString().toCharArray(), 0, sb.length());
                sb.delete(0, sb.length());
            } else {
                if (!prefixFound) {
                    int count = 0;
                    StringBuilder tempSb = new StringBuilder();
                    while (true) {
                        char ch;
                        if (index >= len) {
                            break;
                        } else {
                            ch = cbuf[index++];
                        }
                        tempSb.append(ch);
                        if (prefix.charAt(count) == ch) {
                            count++;
                        } else {
                            sb.append(tempSb);
                            break;
                        }
                        if (tempSb.length() == prefix.length()) {
                            prefixFound = true;
                            sb.append(prefix);
                            break;
                        }
                    }
                }
                if (prefixFound) {
                    int count = 0;
                    StringBuilder tempSb = new StringBuilder();
                    while (true) {
                        char ch;
                        if (index >= len) {
                            break;
                        } else {
                            ch = cbuf[index++];
                        }
                        tempSb.append(ch);
                        if (suffix.charAt(count) == ch) {
                            count++;
                            if (suffix.length() == count) {
                                suffixFound = true;
                                prefixFound = false;
                                String key = tempSb.substring(0, tempSb.length() - suffix.length());
                                sb.delete(0, sb.length());
                                if (value != null) {
                                    sb.append(value);
                                } else {
                                    sb.append(prefix + key + suffix);
                                }
                                break;
                            }
                        } else {
                            count = 0;
                        }
                    }
                    if (!suffixFound) {
                        sb.append(tempSb);
                    }
                    suffixFound = false;
                }
            }
        }

    }
}
