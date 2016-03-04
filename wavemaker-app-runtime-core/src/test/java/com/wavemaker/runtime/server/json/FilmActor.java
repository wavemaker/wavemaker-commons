/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
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
package com.wavemaker.runtime.server.json;

// Generated Jul 5, 2007 6:21:54 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * FilmActor generated by hbm2java
 */
public class FilmActor {

    private FilmActorId id;

    private Film film;

    private Actor actor;

    private Date lastUpdate;

    public FilmActor() {
    }

    public FilmActor(FilmActorId id, Film film, Actor actor, Date lastUpdate) {
        this.id = id;
        this.film = film;
        this.actor = actor;
        this.lastUpdate = lastUpdate;
    }

    public FilmActorId getId() {
        return this.id;
    }

    public void setId(FilmActorId id) {
        this.id = id;
    }

    public Film getFilm() {
        return this.film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Actor getActor() {
        return this.actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
