package com.wavemaker.commons.util;

import java.beans.Introspector;

import com.wavemaker.commons.util.reveng.ReverseEngineeringStrategyUtil;

public class NamingUtils {

    private static final String TABLE_SUFFIX = "Entity";

    /**
     * Converts a database name (table or column) to a java name.
     * eg,Hello_World : helloWorld
     * eq,HelloWorld : helloWorld
     *
     * @return The converted java identifier.
     */
    public static String toJavaIdentifier(final String identifier) {
        String rtn = columnToPropertyName(identifier);
        int upperIndex = 1;
        if (rtn.length() > 1 && Character.isUpperCase(rtn.charAt(1))) {
            upperIndex = 2;
        }
        rtn = rtn.substring(0, upperIndex).toLowerCase() + rtn.substring(upperIndex);
        return StringUtils.toJavaIdentifier(rtn);
    }

    public static String toJavaClassName(final String name) {
        String rtn = ReverseEngineeringStrategyUtil.toUpperCamelCase(name);

        Tuple.Two<String, String> t = StringUtils.splitPackageAndClass(rtn);

        String className = t.v2;

        if (StringUtils.isInJavaLangPackage(className)) {
            className += TABLE_SUFFIX;
        }

        return StringUtils.fq(t.v1, StringUtils.toJavaIdentifier(className, "_", '_', false));
    }

    private static String columnToPropertyName(String identifier) {
        String decapitalize = Introspector.decapitalize( ReverseEngineeringStrategyUtil.toUpperCamelCase(identifier) );

        return keywordCheck(decapitalize);
    }

    private static String keywordCheck(String possibleKeyword) {
        if(ReverseEngineeringStrategyUtil.isReservedJavaKeyword(possibleKeyword)) {
            possibleKeyword = possibleKeyword + "_";
        }
        return possibleKeyword;
    }

}
