package com.wavemaker.studio.common.util.utils;

import com.wavemaker.studio.common.util.CastUtils;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prithvi Medavaram on 13/10/15.
 */
public class CastUtilsTest {
    @Test
    public void castListTest(){
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");

        List unknownTypesList = stringList;
        List<String> castedList = CastUtils.cast(unknownTypesList);
        for (String i : castedList) {
            assertTrue(i instanceof String);
        }
        assertEquals(castedList, unknownTypesList);
    }

    @Test(expectedExceptions = {ClassCastException.class})
    public void castListClassCastExceptionTest(){
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");

        List unknownTypesList = stringList;
        List<Integer> castedList = CastUtils.cast(unknownTypesList);
        for (Integer i : castedList) {// this line should throw ClassCastException as we cannot iterate over integers in a string list
        }
    }
}
