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

import java.util.Map;

import org.testng.annotations.Test;

import com.wavemaker.commons.MethodNotFoundRuntimeException;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * @author Simon Toens
 */
public class ObjectInvokerTest {

    private final ObjectAccess oi = ObjectAccess.getInstance();

    public void setUp() {
        A.RTN = null;
        A.RTN_OBJ = null;
    }

    static class Arg1 {
    }

    static class A {

        static String SIMPLE_METHOD_RTN = "simpleMethodRtn";

        static Arg1 RTN = null;

        static Object RTN_OBJ = null;

        public String simpleMethod() {
            return SIMPLE_METHOD_RTN;
        }

        public Integer simpleMethodArg(Integer i) {
            return i;
        }

        public ObjectAccess simpleMethodArg(Integer i, ObjectAccess oi) {
            return oi;
        }

        public void methodArg1(Arg1 a) {
            RTN = a;
        }

        public void methodObject(Object o) {
            RTN_OBJ = o;
        }

        @SuppressWarnings("unused")
        private void privateMethod() {
        }

        protected void protectedMethod() {
        }

        void packageProtectedMethod() {
        }

    }

    class B extends A {

        private String foo;

        public String methodInChildClass(Integer i) {
            return i.toString();
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        public String getFoo() {
            return this.foo;
        }

    }

    class AnnotationClass {

        public void annotatedMethod() {
        }

        public void notAnnotatedMethod() {
        }

    }
    @Test
    public void methodNoArgTest() {
        String rtn = this.oi.invoke(new A(), "simpleMethod");
        assertTrue(rtn == A.SIMPLE_METHOD_RTN);
    }
    @Test
    public void methodArgTest() {
        Integer i = Integer.valueOf(2);
        Integer rtn = this.oi.invoke(new A(), "simpleMethodArg", i);
        assertTrue(rtn == i);
    }
    @Test
    public void methodArgOverloadedTest() {
        Integer i = Integer.valueOf(2);
        ObjectAccess rtn = this.oi.invoke(new A(), "simpleMethodArg", i, this.oi);
        assertTrue(rtn == this.oi);
    }
    @Test
    public void methodArg1Test() {
        Arg1 a = new Arg1();
        assertTrue(A.RTN == null);
        this.oi.invoke(new A(), "methodArg1", a);
        assertTrue(A.RTN == a);
    }
    @Test
    public void methodObjectTest() {
        Arg1 a = new Arg1();
        assertTrue(A.RTN_OBJ == null);
        this.oi.invoke(new A(), "methodObject", a);
        assertTrue(A.RTN_OBJ == a);
    }
    @Test
    public void cannotCallPrivateMethodTest() {
        try {
            this.oi.invoke(new A(), "privateMethod");
        } catch (MethodNotFoundRuntimeException ex) {
            return;
        }
        fail();
    }
    @Test
    public void cannotCallProtectedMethodTest() {
        try {
            this.oi.invoke(new A(), "protectedMethod");
        } catch (MethodNotFoundRuntimeException ex) {
            return;
        }
        fail();
    }
    @Test
    public void cannotCallPackageProtectedMethodTest() {
        try {
            this.oi.invoke(new A(), "packageProtectedMethod");
        } catch (MethodNotFoundRuntimeException ex) {
            return;
        }
        fail();
    }
    @Test
    public void methodInChildClassTest() {
        String s = this.oi.invoke(new B(), "methodInChildClass", 2);
        assertTrue(s.equals("2"));
    }
    @Test
    public void methodInParentClassTest() {
        String rtn = this.oi.invoke(new B(), "simpleMethod");
        assertTrue(rtn == A.SIMPLE_METHOD_RTN);
    }
    @Test
    public void getPropertiesTest() {
        Map<String, Class<?>> properties = this.oi.getProperties(B.class);
        assertTrue(properties.size() == 1);
        assertTrue(properties.get("foo") == String.class);
    }
    @Test
    public void newInstanceTest() {
        ObjectInvokerTest t = (ObjectInvokerTest) this.oi.newInstance(ObjectInvokerTest.class.getName());
        assertTrue(t != null);
    }
    @Test
    public void setPropertyTest() {
        B b = new B();
        this.oi.setProperty(b, "foo", "test");
        assertTrue(b.getFoo().equals("test"));
    }
    @Test
    public void setPropertyToNullTest() {
        B b = new B();
        this.oi.setProperty(b, "foo", null);
        assertTrue(b.getFoo() == null);
    }

}
