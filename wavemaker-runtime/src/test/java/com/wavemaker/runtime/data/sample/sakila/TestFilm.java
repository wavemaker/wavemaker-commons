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

// Generated Jun 26, 2007 4:52:50 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Film generated by hbm2java
 */
@SuppressWarnings({ "serial", "unchecked" })
public class TestFilm implements java.io.Serializable {

    private Short filmId;

    private Language languageByOriginalLanguageId;

    private Language languageByLanguageId;

    private String title;

    private String description;

    private Date releaseYear;

    private byte rentalDuration;

    private BigDecimal rentalRate;

    private Short length;

    private BigDecimal replacementCost;

    private String rating;

    private String specialFeatures;

    private Date lastUpdate;

    private Set filmCategories = new HashSet(0);

    private Set inventories = new HashSet(0);

    private Set filmActors = new HashSet(0);

    public TestFilm() {
    }

    public TestFilm(Language languageByLanguageId, String title, byte rentalDuration, BigDecimal rentalRate, BigDecimal replacementCost,
        Date lastUpdate) {
        this.languageByLanguageId = languageByLanguageId;
        this.title = title;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
        this.lastUpdate = lastUpdate;
    }

    public TestFilm(Language languageByOriginalLanguageId, Language languageByLanguageId, String title, String description, Date releaseYear,
        byte rentalDuration, BigDecimal rentalRate, Short length, BigDecimal replacementCost, String rating, String specialFeatures, Date lastUpdate,
        Set filmCategories, Set inventories, Set filmActors) {
        this.languageByOriginalLanguageId = languageByOriginalLanguageId;
        this.languageByLanguageId = languageByLanguageId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.specialFeatures = specialFeatures;
        this.lastUpdate = lastUpdate;
        this.filmCategories = filmCategories;
        this.inventories = inventories;
        this.filmActors = filmActors;
    }

    public Short getFilmId() {
        return this.filmId;
    }

    public void setFilmId(Short filmId) {
        this.filmId = filmId;
    }

    public Language getLanguageByOriginalLanguageId() {
        return this.languageByOriginalLanguageId;
    }

    public void setLanguageByOriginalLanguageId(Language languageByOriginalLanguageId) {
        this.languageByOriginalLanguageId = languageByOriginalLanguageId;
    }

    public Language getLanguageByLanguageId() {
        return this.languageByLanguageId;
    }

    public void setLanguageByLanguageId(Language languageByLanguageId) {
        this.languageByLanguageId = languageByLanguageId;
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

    public Date getReleaseYear() {
        return this.releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public byte getRentalDuration() {
        return this.rentalDuration;
    }

    public void setRentalDuration(byte rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public BigDecimal getRentalRate() {
        return this.rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Short getLength() {
        return this.length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public BigDecimal getReplacementCost() {
        return this.replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSpecialFeatures() {
        return this.specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set getFilmCategories() {
        return this.filmCategories;
    }

    public void setFilmCategories(Set filmCategories) {
        this.filmCategories = filmCategories;
    }

    public Set getInventories() {
        return this.inventories;
    }

    public void setInventories(Set inventories) {
        this.inventories = inventories;
    }

    public Set getFilmActors() {
        return this.filmActors;
    }

    public void setFilmActors(Set filmActors) {
        this.filmActors = filmActors;
    }

}
