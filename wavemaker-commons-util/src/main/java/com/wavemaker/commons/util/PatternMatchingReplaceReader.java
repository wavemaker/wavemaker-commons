/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wavemaker.commons.util;

import java.io.BufferedReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

public class PatternMatchingReplaceReader extends FilterReader {

    private String prefix;
    private String suffix;

    private StringBuilder sb = new StringBuilder();

    private Function<String, String> function;

    public PatternMatchingReplaceReader(Reader in, String prefix, String suffix, Function<String, String> function) {
        super(new BufferedReader(in));
        this.prefix = prefix;
        this.suffix = suffix;
        this.function = function;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int count = 0;
        boolean eof = false;
        while (count < len) {
            int read = read();
            if (read == -1) {
                eof = true;
                break;
            }
            cbuf[off + count++] = (char) read;
        }
        if (count == 0 && eof) {
            return -1;
        }
        return count;
    }

    @Override
    public int read(char cbuf[]) throws IOException {
        return read(cbuf, 0, cbuf.length);
    }

    @Override
    public int read() throws IOException {
        while (true) {
            if (sb.length() != 0) {
                char ch = sb.charAt(0);
                sb = sb.deleteCharAt(0);
                return (ch == Character.MAX_VALUE) ? -1 : ch;
            } else {
                boolean prefixFound = false;
                {
                    int count = 0;
                    StringBuilder tempSb = new StringBuilder();
                    while (true) {
                        char ch = (char) readNextChar();
                        tempSb.append(ch);
                        if (prefix.charAt(count) == ch) {
                            count++;
                        } else {
                            sb.append(tempSb);
                            break;
                        }
                        if (tempSb.length() == prefix.length()) {
                            prefixFound = true;
                            break;
                        }
                    }
                }
                if (prefixFound) {
                    int count = 0;
                    StringBuilder tempSb = new StringBuilder();
                    while (true) {
                        char ch = (char) readNextChar();
                        tempSb.append(ch);
                        if (suffix.charAt(count) == ch) {
                            count++;
                            if (suffix.length() == count) {
                                String key = tempSb.substring(0, tempSb.length() - suffix.length());
                                String val = function.apply(key);
                                if (val != null) {
                                    sb.append(val);
                                } else {
                                    sb.append(prefix + key + suffix);
                                }
                                break;
                            }
                        } else if (!isValidChar(ch)) {
                            sb.append(prefix).append(tempSb.toString());
                            break;
                        } else {
                            count = 0;
                        }
                    }
                }
            }
        }
    }

    private boolean isValidChar(int ch) {
        return Character.isLetter(ch) || Character.isDigit(ch) || ch == '.' || ch == '-' || ch == '_';
    }

    private int readNextChar() throws IOException {
        int read = super.read();
        return read;
    }
}
