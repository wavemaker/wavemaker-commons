package com.wavemaker.commons.util.utils;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private List<Car> tasks = new ArrayList<>();

    @MyAnnotation
    public List<Car> getTasks() {
        return tasks;
    }

    public void setTasks(List<Car> tasks) {
        this.tasks = tasks;
    }
}
