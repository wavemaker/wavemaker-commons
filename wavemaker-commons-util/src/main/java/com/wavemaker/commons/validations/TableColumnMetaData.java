package com.wavemaker.commons.validations;

import java.util.List;

import com.wavemaker.tools.apidocs.tools.core.model.Constraint;

/**
 * Created by srujant on 17/8/17.
 */
public class TableColumnMetaData {
    private List<Constraint> constraints;

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }
}
