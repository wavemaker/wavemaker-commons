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

package com.wavemaker.commons.indexing.model.databinding;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("serviceVariableDataBinding")
public class ServiceVariableDataBinding extends DataBinding {
    private List<com.wavemaker.commons.indexing.model.DataBinding> dataBindings = new ArrayList<>();
    private final String variableCategory = "serviceVariableDataBinding";

    public ServiceVariableDataBinding() {
    }

    public ServiceVariableDataBinding(List<com.wavemaker.commons.indexing.model.DataBinding> dataBindings) {
        this.dataBindings = dataBindings;
    }

    public List<com.wavemaker.commons.indexing.model.DataBinding> getDataBindings() {
        return dataBindings;
    }

    public void setDataBindings(List<com.wavemaker.commons.indexing.model.DataBinding> dataBindings) {
        this.dataBindings = dataBindings;
    }

    public String getVariableCategory() {
        return variableCategory;
    }

    @Override
    public String getType() {
        return variableCategory;
    }
}
