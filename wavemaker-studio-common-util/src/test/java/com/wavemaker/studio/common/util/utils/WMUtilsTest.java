/**
 * Copyright (C) 2014 WaveMaker, Inc. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wavemaker.studio.common.util.utils;

import com.wavemaker.studio.common.util.WMUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;


/*
 * Created by Prithvi Medavaram on 9/10/15.
 */

public class WMUtilsTest {
    @Test(dataProvider = "objectCreator")
    public void areObjectsEqualTest(Object o1, Object o2, boolean expectedResult) {
        boolean actual = WMUtils.areObjectsEqual(o1, o2);
        assertEquals(actual, expectedResult);
    }

    @Test(dataProvider = "fileProvider")
    public void getFileExtensionsFromFileNameTest(String fileName, String extension) {
        assertEquals(WMUtils.getFileExtensionFromFileName(fileName), extension);
    }

    @Test(dataProvider = "uriProvider")
    public void decodeRequestURITest(String encoded, String decoded) {
        assertEquals(WMUtils.decodeRequestURI(encoded), decoded);
    }

    @DataProvider
    public Object[][] fileProvider() throws IOException {
        Object[][] objects = new Object[3][2];

            objects[0][0] = File.createTempFile("deleteIfYouSee", ".txt").getName();
            objects[0][1] = "txt";

            objects[1][0] = File.createTempFile("delete.If.You.;See", ".txt").getName();
            objects[1][1] = "txt";

            objects[2][0] = File.createTempFile("deleteIfYouSee", ".xml").getName();
            objects[2][1] = "xml";

        return objects;
    }

    @DataProvider
    public Object[][] uriProvider() {
        Object[][] objects = new Object[3][2];
        objects[0][0] = "https%3A%2F%2FWaveMaker%2FUtilClass%2FtestURL";
        objects[0][1] = "https://WaveMaker/UtilClass/testURL";

        objects[1][0] = "http%3A%2F%2Fwww.example.com%2F";
        objects[1][1] = "http://www.example.com/";

        objects[2][0] = "https%3A%2F%2Fwww.google.co.in%2Fwebhp%3Fsourceid%3Dchrome-instant%26ion%3D1%26espv%3D2%26ie%3DUTF-8%26client%3Dubuntu%23q%3Dwavemaker";
        objects[2][1] = "https://www.google.co.in/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8&client=ubuntu#q=wavemaker";
        return objects;
    }

    @DataProvider
    public Object[][] objectCreator() {

        Object[][] obj = new Object[4][3];
        Car car = new Car();


        Person person = new Person();
        obj[0][0] = person;
        obj[0][1] = car;
        obj[0][2] = false;

        Car lamborghiniCar = new Car();
        lamborghiniCar.setBrand("Lamborghini");
        lamborghiniCar.setModel("Veneno");

        Car lamborghiniCar1 = new Car();
        lamborghiniCar1.setBrand("Lamborghini");
        lamborghiniCar1.setModel("Veneno");

        obj[1][0] = lamborghiniCar;
        obj[1][1] = lamborghiniCar1;
        obj[1][2] = true;

        Car marutiCar = new Car();
        marutiCar.setModel("1000cc");
        marutiCar.setBrand("maruti");

        obj[2][0] = lamborghiniCar;
        obj[2][1] = marutiCar;
        obj[2][2] = false;

        obj[3][0] = lamborghiniCar;
        obj[3][1] = lamborghiniCar;
        obj[3][2] = true;
        return obj;
    }
}
