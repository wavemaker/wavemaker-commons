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

import java.beans.Introspector;

import org.apache.commons.lang3.tuple.Pair;

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

        Pair<String, String> packageAndClassPair = StringUtils.splitPackageAndClass(rtn);

        String className = packageAndClassPair.getRight();

        if (StringUtils.isInJavaLangPackage(className)) {
            className += TABLE_SUFFIX;
        }

        return StringUtils.fq(packageAndClassPair.getLeft(), StringUtils.toJavaIdentifier(className, "_", '_', false));
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
