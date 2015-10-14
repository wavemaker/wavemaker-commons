/**
 * Copyright (C) 2014 WaveMaker, Inc. All rights reserved.
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

package com.wavemaker.studio.common.util.utils;

import com.wavemaker.studio.common.util.WMUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/*
 * Created by Prithvi Medavaram on 9/10/15.
 */

public class WMUtilsTest {
    @Test(dataProvider = "objectCreator")
    public void areObjectsEqualTest(Object o1, Object o2, boolean expectedResult) {
        boolean actual = WMUtils.areObjectsEqual(o1, o2);
        Assert.assertEquals(actual, expectedResult);
    }

    @DataProvider
    public Object[][] objectCreator(){

        Object[][] obj= new Object[4][3];
        Car car = new Car();


        Person person = new Person();
        obj[0][0]=person;
        obj[0][1]=car;
        obj[0][2]=false;

        Car lamborghiniCar = new Car();
        lamborghiniCar.setBrand("Lamborghini");
        lamborghiniCar.setModel("Veneno");

        Car lamborghiniCar1 = new Car();
        lamborghiniCar1.setBrand("Lamborghini");
        lamborghiniCar1.setModel("Veneno");

        obj[1][0]=lamborghiniCar;
        obj[1][1]=lamborghiniCar1;
        obj[1][2]=true;

        Car marutiCar = new Car();
        marutiCar.setModel("1000cc");
        marutiCar.setBrand("maruti");

        obj[2][0]=lamborghiniCar;
        obj[2][1]=marutiCar;
        obj[2][2]=false;

        obj[3][0]=lamborghiniCar;
        obj[3][1]=lamborghiniCar;
        obj[3][2]=true;
        return obj;
    }
}
