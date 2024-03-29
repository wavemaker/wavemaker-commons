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
package com.wavemaker.commons.util.utils;

import java.util.List;

import org.testng.annotations.Test;

import com.wavemaker.commons.util.CastUtils;
import com.wavemaker.commons.util.ObjectLiteralParser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Simon Toens
 */
public class ObjectLiteralParserTest {

    public static class City {

        private Short cityId;

        private Object country;

        public void setCityId(Short cityId) {
            this.cityId = cityId;
        }

        public Short getCityId() {
            return this.cityId;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public Object getCountry() {
            return this.country;
        }
    }

    public static class A {

        private String foo;

        private String blah;

        private A self;

        private B b;

        public void setFoo(String foo) {
            this.foo = foo;
        }

        public void setBlah(String blah) {
            this.blah = blah;
        }

        public void setSelf(A self) {
            this.self = self;
        }

        public void setB(B b) {
            this.b = b;
        }
    }

    public static class B {

        private String name;

        private String address;

        private int number;

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    @Test
    public void simple0Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a != null);
    }

    @Test
    public void simple1Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{foo:test}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("test"));
    }

    @Test
    public void spaceInValueTest() {
        ObjectLiteralParser p = new ObjectLiteralParser("{foo:t e s t}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("t e s t"));
    }

    @Test
    public void space2InValueTest() {
        ObjectLiteralParser p = new ObjectLiteralParser("{foo:' t e s t '}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals(" t e s t "));
    }

    @Test
    public void simple2Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{foo:test, blah:test2}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("test"));
        assertTrue(a.blah.equals("test2"));
    }

    @Test
    public void simple3Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{foo:'test', blah:\"test2\"}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("test"));
        assertTrue(a.blah.equals("test2"));
    }

    @Test
    public void simple4Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{  foo: 'test' , blah : \"test2\"  }", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("test"));
        assertTrue(a.blah.equals("test2"));
    }

    @Test
    public void nested1Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{self:{foo:test}}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.self.foo.equals("test"));
    }

    @Test
    public void nested2Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{foo:foo1, self:{foo:test}}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("foo1"));
        assertTrue(a.self.foo.equals("test"));
    }

    @Test
    public void nested3Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{foo:foo1, self:{foo:test},blah:blah2}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("foo1"));
        assertTrue(a.blah.equals("blah2"));
        assertTrue(a.self.foo.equals("test"));
    }

    @Test
    public void nested4Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{foo:foo1, self:{foo:test}," + "blah:blah2,foo:foo2}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("foo2"));
        assertTrue(a.blah.equals("blah2"));
        assertTrue(a.self.foo.equals("test"));
    }

    @Test
    public void test2Nested1() {
        ObjectLiteralParser p = new ObjectLiteralParser("{foo:foo1, self:{foo:test1}," + "b:{address:a1,number:123,name:n1}," + "blah:blah1}",
            A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("foo1"));
        assertTrue(a.blah.equals("blah1"));
        assertTrue(a.self.foo.equals("test1"));
        assertTrue(a.b.name.equals("n1"));
        assertTrue(a.b.address.equals("a1"));
        assertTrue(a.b.number == 123);
    }

    @Test
    public void quotedAttr1Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{'foo':test}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("test"));
    }

    @Test
    public void quotedAttr2Test() {
        ObjectLiteralParser p = new ObjectLiteralParser("{\"foo\":test}", A.class.getName());
        A a = (A) p.parse();
        assertTrue(a.foo.equals("test"));
    }

    @Test
    public void cityTest() {
        ObjectLiteralParser p = new ObjectLiteralParser("\"{cityId:2,country:{}}\"", City.class);
        City c = (City) p.parse();
        assertTrue(c.getCityId().equals(Short.valueOf("2")));
    }

    @Test
    public void simpleListTest() {
        ObjectLiteralParser p = new ObjectLiteralParser("[a,b,c]", String.class);

        List<?> lp = (List<?>) p.parse();
        List<String> l = CastUtils.cast(lp);

        assertEquals(3, l.size());

        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
        assertEquals("c", l.get(2));
    }
}
