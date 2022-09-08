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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 20/1/16
 */
public class StringTemplateTest {

    @Test
    public void testSubstitute() throws Exception {

        StringTemplate t1 = new StringTemplate(
                "${this} is a ${test} of the ${foo} bar=${bar} ${emergency.broadcasting.system}");
        Map<String, String> m = new HashMap<>();
        m.put("this", "*This*");
        m.put("test", "*TEST*");
        m.put("foo", "$$$aaa\\\\111");
        m.put("emergency.broadcasting.system", "EBS");

        final String replaced = t1.substitute(m);
        assertEquals("*This* is a *TEST* of the $$$aaa\\\\111 bar=${bar} EBS", replaced);
    }

    @Test
    public void testSubstituteWithOptional() throws Exception {

        StringTemplate t1 = new StringTemplate(
                "${this[this is optional]} is a ${test[again ${test} and ${this}]} of the ${foo} bar=${bar} " +
                        "${emergency.broadcasting.system[this wont be print]}");
        Map<String, String> m = new HashMap<>();
        m.put("this", "*This*");
        m.put("test", "*TEST*");
        m.put("foo", "$$$aaa\\\\111");

        final String replaced = t1.substitute(m);
        assertEquals("this is optional is a again *TEST* and *This* of the $$$aaa\\\\111 bar=${bar} ", replaced);
    }

    @Test
    public void testSubstituteWithSetNull() throws Exception {

        StringTemplate t1 = new StringTemplate(
                "${this} is a ${test} of the ${foo} bar=${bar} ${emergency.broadcasting.system}", true);
        Map<String, String> m = new HashMap<>();
        m.put("this", "*This*");
        m.put("test", "*TEST*");
        m.put("foo", "$$$aaa\\\\111");
        m.put("emergency.broadcasting.system", "EBS");

        final String replaced = t1.substitute(m);
        assertEquals("*This* is a *TEST* of the $$$aaa\\\\111 bar= EBS", replaced);
    }

    @Test
    public void testSubstituteWithOptionalAndSetEmpty() throws Exception {

        StringTemplate t1 = new StringTemplate(
                "${this[this is optional]} is a ${test[again ${test} and ${this}]} of the ${foo} bar=${bar} " +
                        "${emergency.broadcasting.system[this should not print]}", true);
        Map<String, String> m = new HashMap<>();
        m.put("this", "*This*");
        m.put("test", "*TEST*");
        m.put("foo", "$$$aaa\\\\111");

        final String replaced = t1.substitute(m);
        assertEquals("this is optional is a again *TEST* and *This* of the $$$aaa\\\\111 bar= ", replaced);
    }
}