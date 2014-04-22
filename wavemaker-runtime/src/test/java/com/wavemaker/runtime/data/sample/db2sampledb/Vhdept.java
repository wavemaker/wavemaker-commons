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
package com.wavemaker.runtime.data.sample.db2sampledb;

// Generated Feb 7, 2008 1:47:51 PM by Hibernate Tools 3.2.0.CR1

/**
 * Vhdept generated by hbm2java
 */
@SuppressWarnings({ "serial" })
public class Vhdept implements java.io.Serializable {

    private VhdeptId id;

    public Vhdept() {
    }

    public Vhdept(VhdeptId id) {
        this.id = id;
    }

    public VhdeptId getId() {
        return this.id;
    }

    public void setId(VhdeptId id) {
        this.id = id;
    }

}
