/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;

/**
 * @author Simon Toens
 */
public abstract class StringUtils {

    public static final String JAVA_SRC_EXT = ".java";

    private static final Collection<String> JAVA_KEYWORDS = new HashSet<>(50);
    private static final Collection<String> HQL_KEYWORDS = new HashSet<>(50);
    private static final Pattern NON_JAVA_IDENTIFIER = Pattern.compile("\\W");

    static {
        JAVA_KEYWORDS.add("abstract");
        JAVA_KEYWORDS.add("assert");
        JAVA_KEYWORDS.add("boolean");
        JAVA_KEYWORDS.add("break");
        JAVA_KEYWORDS.add("byte");
        JAVA_KEYWORDS.add("case");
        JAVA_KEYWORDS.add("catch");
        JAVA_KEYWORDS.add("char");
        JAVA_KEYWORDS.add("class");
        JAVA_KEYWORDS.add("const");
        JAVA_KEYWORDS.add("continue");
        JAVA_KEYWORDS.add("default");
        JAVA_KEYWORDS.add("do");
        JAVA_KEYWORDS.add("double");
        JAVA_KEYWORDS.add("else");
        JAVA_KEYWORDS.add("enum");
        JAVA_KEYWORDS.add("extends");
        JAVA_KEYWORDS.add("final");
        JAVA_KEYWORDS.add("finally");
        JAVA_KEYWORDS.add("float");
        JAVA_KEYWORDS.add("for");
        JAVA_KEYWORDS.add("goto");
        JAVA_KEYWORDS.add("if");
        JAVA_KEYWORDS.add("implements");
        JAVA_KEYWORDS.add("import");
        JAVA_KEYWORDS.add("instanceof");
        JAVA_KEYWORDS.add("int");
        JAVA_KEYWORDS.add("interface");
        JAVA_KEYWORDS.add("long");
        JAVA_KEYWORDS.add("native");
        JAVA_KEYWORDS.add("new");
        JAVA_KEYWORDS.add("package");
        JAVA_KEYWORDS.add("private");
        JAVA_KEYWORDS.add("protected");
        JAVA_KEYWORDS.add("public");
        JAVA_KEYWORDS.add("return");
        JAVA_KEYWORDS.add("short");
        JAVA_KEYWORDS.add("static");
        JAVA_KEYWORDS.add("strictfp");
        JAVA_KEYWORDS.add("super");
        JAVA_KEYWORDS.add("switch");
        JAVA_KEYWORDS.add("synchronized");
        JAVA_KEYWORDS.add("this");
        JAVA_KEYWORDS.add("throw");
        JAVA_KEYWORDS.add("throws");
        JAVA_KEYWORDS.add("transient");
        JAVA_KEYWORDS.add("try");
        JAVA_KEYWORDS.add("void");
        JAVA_KEYWORDS.add("volatile");
        JAVA_KEYWORDS.add("while");

        HQL_KEYWORDS.add("all");
        HQL_KEYWORDS.add("any");
        HQL_KEYWORDS.add("and");
        HQL_KEYWORDS.add("as");
        HQL_KEYWORDS.add("asc");
        HQL_KEYWORDS.add("avg");
        HQL_KEYWORDS.add("between");
        HQL_KEYWORDS.add("class");
        HQL_KEYWORDS.add("count");
        HQL_KEYWORDS.add("delete");
        HQL_KEYWORDS.add("desc");
        HQL_KEYWORDS.add("distinct");
        HQL_KEYWORDS.add("elements");
        HQL_KEYWORDS.add("escape");
        HQL_KEYWORDS.add("exists");
        HQL_KEYWORDS.add("false");
        HQL_KEYWORDS.add("fetch");
        HQL_KEYWORDS.add("from");
        HQL_KEYWORDS.add("full");
        HQL_KEYWORDS.add("group");
        HQL_KEYWORDS.add("having");
        HQL_KEYWORDS.add("in");
        HQL_KEYWORDS.add("indices");
        HQL_KEYWORDS.add("inner");
        HQL_KEYWORDS.add("insert");
        HQL_KEYWORDS.add("into");
        HQL_KEYWORDS.add("is");
        HQL_KEYWORDS.add("join");
        HQL_KEYWORDS.add("left");
        HQL_KEYWORDS.add("like");
        HQL_KEYWORDS.add("max");
        HQL_KEYWORDS.add("min");
        HQL_KEYWORDS.add("new");
        HQL_KEYWORDS.add("not");
        HQL_KEYWORDS.add("null");
        HQL_KEYWORDS.add("nulls");
        HQL_KEYWORDS.add("object");
        HQL_KEYWORDS.add("or");
        HQL_KEYWORDS.add("order");
        HQL_KEYWORDS.add("outer");
        HQL_KEYWORDS.add("properties");
        HQL_KEYWORDS.add("right");
        HQL_KEYWORDS.add("select");
        HQL_KEYWORDS.add("set");
        HQL_KEYWORDS.add("some");
        HQL_KEYWORDS.add("sum");
        HQL_KEYWORDS.add("true");
        HQL_KEYWORDS.add("update");
        HQL_KEYWORDS.add("versioned");
        HQL_KEYWORDS.add("where");
    }

    private StringUtils() {
    }

    public static boolean isBlankOrEquals(String o1, String o2) {
        return ((o1 == null || o1.equals("")) && (o2 == null || o2.equals(""))) || Objects.equals(o1, o2);
    }

    public static String toString(Throwable th) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        th.printStackTrace(pw);
        String rtn = sw.toString();
        pw.close();
        return rtn;
    }

    /**
     * Converts the given snake case string to Java Field name.
     *
     * eg:
     * User -> user
     * User_id -> userId
     * user_id -> userId
     * USER_ID -> userId
     *
     * @param inputString string to convert
     * @return java field identifier
     */
    public static String toFieldName(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return inputString;
        }

        StringBuilder result = new StringBuilder();
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toLowerCase(firstChar);
        result.append(firstCharToUpperCase);
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (currentChar != '_') {
                char previousChar = inputString.charAt(i - 1);
                if (previousChar == '_') {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result.append(currentCharToUpperCase);
                } else {
                    char currentCharToLowerCase = Character.toLowerCase(currentChar);
                    result.append(currentCharToLowerCase);
                }
            }
        }
        return result.toString();
    }


    public static boolean hasUpperCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static void validatePackageName(String packageName) {
        if (org.apache.commons.lang3.StringUtils.isBlank(packageName)) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.invalid.packageName"));
        }
        for (String token : packageName.split("\\.")) {
            validateJavaIdentifier(token, false);
        }
    }

    public static void validateJavaIdentifier(String s, boolean allowHqlKeyWords) {
        if (org.apache.commons.lang3.StringUtils.isBlank(s)) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.invalid.identifier"));
        }
        if (!allowHqlKeyWords && HQL_KEYWORDS.contains(s.toLowerCase())) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.identifier.hql"), s);
        }
        if (JAVA_KEYWORDS.contains(s) || StringUtils.isInJavaLangPackage(org.apache.commons.lang3.StringUtils.capitalize(s))) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.identifier.javaPackage"), s);
        }
        if (NON_JAVA_IDENTIFIER.matcher(s).find() || !Character.isJavaIdentifierStart(s.charAt(0))) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.identifier.notJavaIdentifier"), s);
        }
    }

    public static String toJavaIdentifier(String s) {
        return toJavaIdentifier(s, '_');
    }

    public static String toJavaIdentifier(String s, char replacementChar) {
        return toJavaIdentifier(s, String.valueOf(replacementChar), replacementChar);
    }

    public static String toJavaIdentifier(String s, CharSequence prefixReplacementChar, char replacementChar) {
        return toJavaIdentifier(s, prefixReplacementChar, replacementChar, true);
    }

    public static String toJavaIdentifier(
            String s, CharSequence prefixReplacementChar, char replacementChar, boolean checkKeyword) {

        if (org.apache.commons.lang3.StringUtils.isBlank(s)) {
            throw new IllegalArgumentException("input cannot be null or empty");
        }

        String unquoted = unquote(s);
        if (unquoted != null && !unquoted.isEmpty()) {
            s = unquoted;
        }

        // although '$' is ok, it causes issues with type generation
        // because of inner class confusion
        s = s.replace("$", "");

        if (s.isEmpty()) {
            s = "" + replacementChar;
        }

        StringBuilder rtn = new StringBuilder();

        if ((checkKeyword && (JAVA_KEYWORDS.contains(s.toLowerCase()) || HQL_KEYWORDS.contains(s.toLowerCase()))) ||
                !Character.isJavaIdentifierStart(s.charAt(0))) {
            rtn.append(prefixReplacementChar);
        }

        if (s.length() == 1) {
            if (rtn.length() > 0) {
                return rtn.toString();
            } else {
                return s;
            }
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isJavaIdentifierPart(c)) {
                c = replacementChar;
            }
            rtn.append(c);
        }

        return rtn.toString();
    }

    public static boolean isJavaKeyword(final String s) {
        return JAVA_KEYWORDS.contains(s.toLowerCase());
    }

    public static List<String> getItemsStartingWith(Collection<String> items, String prefix, boolean removePrefix) {
        List<String> rtn = new ArrayList<>();
        for (String s : items) {
            if (s.startsWith(prefix)) {
                if (removePrefix) {
                    s = s.substring(prefix.length());
                }
                rtn.add(s);
            }
        }
        return rtn;
    }

    public static String getFormattedDate() {
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return f.format(new Date());
    }

    /**
     * Given a fully qualified class name a.b.c, returns a Tuple.Two instance with the package and the class name: (a.b,
     * c).
     */
    public static Pair<String, String> splitPackageAndClass(String s) {
        int i = s.lastIndexOf('.');
        if (i == -1) {
            return ImmutablePair.of("", s);
        }

        if (i == 0 && s.length() == 1) {
            throw new IllegalArgumentException("Cannot handle \".\"");
        }

        return ImmutablePair.of(s.substring(0, i), s.substring(i + 1));
    }

    /**
     * split by ',', except if within {},[] or quotes.
     */
    public static List<String> split(String s) {
        Collection<Character> sep = new HashSet<>(1);
        sep.add(',');
        return split(s, sep);
    }

    /**
     * split by sep, except if within {},[] or quotes.
     */
    public static List<String> split(String s, Collection<Character> sep) {

        List<String> rtn = new ArrayList<>();

        boolean inDoubleQuotes = false;
        boolean inSingleQuotes = false;
        int bracesDepth = 0;
        int bracketsDepth = 0;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (c == '{') {
                bracesDepth++;
            } else if (c == '}') {
                bracesDepth--;
            } else if (c == '[') {
                bracketsDepth++;
            } else if (c == ']') {
                bracketsDepth--;
            } else if (c == '\'') {
                if (inSingleQuotes) {
                    inSingleQuotes = false;
                } else {
                    inSingleQuotes = true;
                }
            } else if (c == '"') {
                if (inDoubleQuotes) {
                    inDoubleQuotes = false;
                } else {
                    inDoubleQuotes = true;
                }
            }

            boolean add = true;
            if (sep.contains(c) && !inDoubleQuotes && !inSingleQuotes && bracesDepth == 0 && bracketsDepth == 0) {
                add = false;
                rtn.add(sb.toString().trim());
                sb.delete(0, sb.length());
            }

            if (add) {
                sb.append(c);
            }

        }

        if (sb.length() > 0) {
            rtn.add(sb.toString().trim());
        }

        return rtn;

    }

    public static String getUniqueName(String name, String names) {
        Collection<String> c = new HashSet<>(1);
        c.add(names);
        return getUniqueName(name, c);
    }

    public static String getUniqueName(String name, Collection<String> names) {

        if (!names.contains(name)) {
            return name;
        }

        for (int i = 2; i < Integer.MAX_VALUE; i++) {
            String newName = name + i;
            if (!names.contains(newName)) {
                return newName;
            }
        }

        throw new AssertionError("Cannot get unique name for " + name);
    }

    public static String classNameToClassFilePath(String className) {
        return className.replace(".", "/") + ".class";
    }

    public static String packageToSrcFilePath(String packageName) {
        return packageName.replace(".", "/");
    }

    public static String classNameToSrcFilePath(String className) {
        return className.replace(".", "/") + JAVA_SRC_EXT;
    }

    public static String getPackage(String className) {
        return fromLastOccurrence(className, ".", -1);
    }

    public static String getClassName(String className) {
        return fromLastOccurrence(className, ".", 1);
    }

    public static boolean isFullyQualified(String className) {
        return className.indexOf('.') != -1;
    }

    public static String fq(String packageName, String className) {
        String prefix = (packageName == null || packageName.isEmpty()) ? "" : packageName + ".";
        return prefix + className;
    }

    public static String upperCaseFirstLetter(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String lowerCaseFirstLetter(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    public static String removeIfEndsWith(String s, String end) {
        if (s.endsWith(end)) {
            return s.substring(0, s.length() - end.length());
        }
        return s;
    }

    public static String removeIfStartsWith(String s, String start) {
        if (s.startsWith(start)) {
            return s.substring(start.length());
        }
        return s;
    }

    public static String fromFirstOccurrence(String s, String substring) {
        return fromFirstOccurrence(s, substring, 1);
    }

    public static String fromFirstOccurrence(String s, String substring, int direction) {
        int i = s.indexOf(substring);
        if (i == -1) {
            return s;
        }

        return StringUtils.substring(s, i, substring, direction);
    }

    public static String fromLastOccurrence(String s, String substring) {
        return fromLastOccurrence(s, substring, 1);
    }

    public static String fromLastOccurrence(String s, String substring, int direction) {
        int i = s.lastIndexOf(substring);
        if (i == -1) {
            return s;
        }

        return StringUtils.substring(s, i, substring, direction);
    }

    public static boolean isNumber(String s) {

        return NumberUtils.isNumber(s);
    }

    public static boolean isNumber(char c) {
        return Character.isDigit(c);
    }

    public static String unquote(String s) {

        if (s == null) {
            return null;
        }
        if (s.startsWith("'") && s.endsWith("'") || s.startsWith("\"") && s.endsWith("\"") || s.startsWith("`") && s
                .endsWith("`")) {
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

    /**
     * Return a String with all occurrences of the "from" String within "original" replaced with the "to" String. If
     * the
     * "original" string contains no occurrences of "from", "original" is itself returned, rather than a copy.
     *
     * @param original the original String
     * @param from the String to replace within "original"
     * @param to the String to replace "from" with
     * @returns a version of "original" with all occurrences of the "from" parameter being replaced with the "to"
     * parameter.
     */
    public static String replacePlainStr(String original, String from, String to) {
        int from_length = from.length();

        if (from_length != to.length()) {
            if (from_length == 0 && to.length() != 0) {
                throw new IllegalArgumentException("Replacing the empty string with something was attempted");
            }
            int start = original.indexOf(from);
            if (start == -1) {
                return original;
            }
            char[] original_chars = original.toCharArray();
            StringBuilder buffer = new StringBuilder(original.length());
            int copy_from = 0;
            while (start != -1) {
                buffer.append(original_chars, copy_from, start - copy_from);
                buffer.append(to);
                copy_from = start + from_length;
                start = original.indexOf(from, copy_from);
            }
            buffer.append(original_chars, copy_from, original_chars.length - copy_from);
            return buffer.toString();
        } else {
            if (from.equals(to)) {
                return original;
            }
            int start = original.indexOf(from);
            if (start == -1) {
                return original;
            }
            StringBuilder buffer = new StringBuilder(original);
            while (start != -1) {
                buffer.replace(start, start + from_length, to);
                start = original.indexOf(from, start + from_length);
            }
            return buffer.toString();
        }
    }

    public static String removeSpaces(String str) {
        str = str.trim();
        char last = str.charAt(0);
        StringBuilder argBuf = new StringBuilder();

        for (int cIdx = 0; cIdx < str.length(); cIdx++) {
            char ch = str.charAt(cIdx);
            if (ch != ' ' || last != ' ') {
                argBuf.append(ch);
                last = ch;
            }
        }

        return argBuf.toString();

    }

    public static String appendPaths(String rootPath, String childPath) {
        String path;
        if (rootPath.length() == 0 || !rootPath.substring(rootPath.length() - 1).equals("/")) {
            if (childPath != null && childPath.length() > 0 && childPath.substring(0, 1).equals("/")) {
                path = rootPath + childPath;
            } else {
                path = rootPath.length() == 0 ? childPath : rootPath + "/" + childPath;
            }
        } else {
            if (childPath != null && childPath.length() > 0 && childPath.substring(0, 1).equals("/")) {
                path = rootPath + childPath.substring(1);
            } else {
                path = rootPath + childPath;
            }
        }

        return path;
    }

    public static String getStringFromBytes(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Removes LF/CR and white spaces
     *
     * @param inputString
     * @return
     */
    public static String removeLineFeed(String inputString) {
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(inputString))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line.trim());
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.read.line"), e);
        }
    }

    private static String substring(String s, int i, String substring, int direction) {
        if (direction >= 0) {
            return s.substring(i + substring.length());
        } else {
            return s.substring(0, i);
        }
    }

    public static boolean isInJavaLangPackage(String className) {
        boolean systemClass = false;
        try {
            Class.forName("java.lang." + className, false, StringUtils.class.getClassLoader());
            systemClass = true;
        } catch (ClassNotFoundException e) {
            // given class not a system class.
        }
        return systemClass;
    }

}
