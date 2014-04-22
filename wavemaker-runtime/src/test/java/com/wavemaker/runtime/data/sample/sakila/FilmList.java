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
package com.wavemaker.runtime.data.sample.sakila;

// Generated Jul 5, 2007 6:21:54 PM by Hibernate Tools 3.2.0.b9

/**
 * FilmList generated by hbm2java
 */
@SuppressWarnings({ "serial" })
public class FilmList implements java.io.Serializable {

    private FilmListId id;

    public FilmList() {
    }

    public FilmList(FilmListId id) {
        this.id = id;
    }

    public FilmListId getId() {
        return this.id;
    }

    public void setId(FilmListId id) {
        this.id = id;
    }

}
