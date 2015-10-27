package com.wavemaker.studio.common.util.classloader;

import com.wavemaker.studio.common.util.ObjectAccess;
import com.wavemaker.studio.common.util.utils.Car;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by Prithvi Medavaram on 13/10/15.
 */
public class ObjectAccessTest {

    @Test
    public void newInstanceForClassNameTest() {
        ObjectAccess objectAccess = ObjectAccess.getInstance();
        String className = com.wavemaker.studio.common.util.utils.Car.class.getName();
        Object expectedObject = objectAccess.newInstance(className);
        assertTrue(expectedObject instanceof Car);
    }

    @Test
    public void newInstanceForClassObjectTest() {
        ObjectAccess objectAccess = ObjectAccess.getInstance();
        Class clazz = Car.class;
        Object expectedObject = objectAccess.newInstance(clazz);
        assertTrue(expectedObject instanceof Car);
    }

    @Test
    public void forNameTest() {
        ObjectAccess objectAccess = ObjectAccess.getInstance();
        String className = com.wavemaker.studio.common.util.utils.Car.class.getName();
        Class<?> clazz = objectAccess.forName(className);
        assertEquals(Car.class, clazz);
    }
}

