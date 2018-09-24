package com.wavemaker.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SortedListTest {


    @Test
    public void testAdd() {
        List<String> delegate = new ArrayList<>(Arrays.asList("c", "d", "b"));
        SortedList<String> sortedList = new SortedList<>(delegate);
        sortedList.add("a");
        String[] expected = {"a","b","c","d"};
        Assert.assertEquals(expected,sortedList.toArray());
    }

    @Test
    public void testAddAll() {
        List<String> delegate = new ArrayList<>(Arrays.asList("c", "d", "b"));
        SortedList<String> sortedList = new SortedList<>(delegate);
        sortedList.addAll(Arrays.asList("a","e","f"));
        String[] expected = {"a","b","c","d","e","f"};
        Assert.assertEquals(expected,sortedList.toArray());
    }
}