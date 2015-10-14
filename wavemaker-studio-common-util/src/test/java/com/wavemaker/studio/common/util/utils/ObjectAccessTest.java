package com.wavemaker.studio.common.util.utils;

import com.wavemaker.studio.common.util.ObjectAccess;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by Prithvi Medavaram on 13/10/15.
 */
public class ObjectAccessTest {

    @Test
    public void newInstanceForClassName(){
        String className = Car.class.getName();
        Object testObject = ObjectAccess.getInstance().newInstance(className);
        assertTrue(testObject instanceof Car);
    }

    @Test
    public void newInstanceForClass(){
        Class<?> clazz = Car.class;
        Object testObject = ObjectAccess.getInstance().newInstance(clazz);
        assertTrue(testObject instanceof Car);
    }

    @Test
    public void forNameTest() {
        String className = Car.class.getName();
        Class testClass = ObjectAccess.getInstance().forName(className);
        assertEquals(Car.class, testClass);
    }

}
