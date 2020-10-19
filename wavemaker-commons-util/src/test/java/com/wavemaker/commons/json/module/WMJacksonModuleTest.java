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
package com.wavemaker.commons.json.module;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavemaker.tools.apidocs.tools.core.model.Swagger;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 3/10/18
 */
public class WMJacksonModuleTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WMJacksonModuleTest.class);

    private ObjectMapper objectMapper;

    private A a;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();

        C c = new C("c value");

        B b = new B("b value", c);

        a = new A("a value", b);

        c.setA(a);
    }

    @Test(expected = JsonMappingException.class)
    public void test1() throws JsonProcessingException {

        final String valueAsString = objectMapper.writeValueAsString(a);

        LOGGER.info(valueAsString);

    }

    @Test
    public void test2() throws JsonProcessingException {

        objectMapper.registerModule(new WMJacksonModule(false));

        final String valueAsString = objectMapper.writeValueAsString(a);

        Assert.assertEquals("{\"value\":\"a value\",\"b\":{\"value\":\"b value\",\"c\":{\"value\":\"c value\"," +
                "\"a\":null}}}", valueAsString);

    }

    @Test
    public void test3() throws JsonProcessingException {

        objectMapper.registerModule(new WMJacksonModule(true));

        try {
            objectMapper.writeValueAsString(a);

            Assert.fail("Should throw cyclic reference issue.");

        } catch (JsonProcessingException e) {
            Assert.assertEquals("Cyclic-reference leading to cycle, Object Reference Stack:A->B->C (through reference" +
                    " chain: com.wavemaker.commons.json.module.A[\"b\"]->com.wavemaker.commons.json.module" +
                    ".B[\"c\"]->com.wavemaker.commons.json.module.C[\"a\"])", e.getMessage());
        }

    }

    @Test
    public void test4() throws IOException {

        final Swagger swagger = objectMapper
                .readValue(this.getClass().getResourceAsStream("/hrdb_API.json"), Swagger.class);

        Assert.assertNotNull(swagger,"Could not get swagger object from json.");

        objectMapper.registerModule(new WMJacksonModule(true));

        final String string = objectMapper.writeValueAsString(swagger);
        Assert.assertNotNull(string,"Could not convert swagger object to json.");

    }

}