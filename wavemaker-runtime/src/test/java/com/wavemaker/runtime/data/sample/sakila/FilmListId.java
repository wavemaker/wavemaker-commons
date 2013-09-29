/*
 *  Copyright (C) 2009 WaveMaker Software, Inc.
 *
 *  This file is part of the WaveMaker Server Runtime.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.wavemaker.runtime.data.sample.sakila;

// Generated Jul 5, 2007 6:21:54 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;

/**
 * FilmListId generated by hbm2java
 */
@SuppressWarnings({ "serial" })
public class FilmListId implements java.io.Serializable {

    private Short fid;

    private String title;

    private String description;

    private String category;

    private BigDecimal price;

    private Short length;

    private String rating;

    private String actors;

    public FilmListId() {
    }

    public FilmListId(String category) {
        this.category = category;
    }

    public FilmListId(Short fid, String title, String description, String category, BigDecimal price, Short length, String rating, String actors) {
        this.fid = fid;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.length = length;
        this.rating = rating;
        this.actors = actors;
    }

    public Short getFid() {
        return this.fid;
    }

    public void setFid(Short fid) {
        this.fid = fid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Short getLength() {
        return this.length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getActors() {
        return this.actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof FilmListId)) {
            return false;
        }
        FilmListId castOther = (FilmListId) other;

        return (this.getFid() == castOther.getFid() || this.getFid() != null && castOther.getFid() != null
            && this.getFid().equals(castOther.getFid()))
            && (this.getTitle() == castOther.getTitle() || this.getTitle() != null && castOther.getTitle() != null
                && this.getTitle().equals(castOther.getTitle()))
            && (this.getDescription() == castOther.getDescription() || this.getDescription() != null && castOther.getDescription() != null
                && this.getDescription().equals(castOther.getDescription()))
            && (this.getCategory() == castOther.getCategory() || this.getCategory() != null && castOther.getCategory() != null
                && this.getCategory().equals(castOther.getCategory()))
            && (this.getPrice() == castOther.getPrice() || this.getPrice() != null && castOther.getPrice() != null
                && this.getPrice().equals(castOther.getPrice()))
            && (this.getLength() == castOther.getLength() || this.getLength() != null && castOther.getLength() != null
                && this.getLength().equals(castOther.getLength()))
            && (this.getRating() == castOther.getRating() || this.getRating() != null && castOther.getRating() != null
                && this.getRating().equals(castOther.getRating()))
            && (this.getActors() == castOther.getActors() || this.getActors() != null && castOther.getActors() != null
                && this.getActors().equals(castOther.getActors()));
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + (getFid() == null ? 0 : this.getFid().hashCode());
        result = 37 * result + (getTitle() == null ? 0 : this.getTitle().hashCode());
        result = 37 * result + (getDescription() == null ? 0 : this.getDescription().hashCode());
        result = 37 * result + (getCategory() == null ? 0 : this.getCategory().hashCode());
        result = 37 * result + (getPrice() == null ? 0 : this.getPrice().hashCode());
        result = 37 * result + (getLength() == null ? 0 : this.getLength().hashCode());
        result = 37 * result + (getRating() == null ? 0 : this.getRating().hashCode());
        result = 37 * result + (getActors() == null ? 0 : this.getActors().hashCode());
        return result;
    }

}
