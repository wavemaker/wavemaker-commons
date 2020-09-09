/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ForwardingList;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 1/9/16
 */
public class SortedList<E extends Comparable<E>> extends ForwardingList<E> {

    private final List<E> delegator;

    public SortedList(final List<E> delegator) {
        this.delegator = delegator;
        if (!(delegator instanceof SortedList)) {
            sort();
        }
    }


    @Override
    public boolean add(final E element) {
        final boolean added = super.add(element);
        sort();
        return added;
    }

    @Override
    public boolean addAll(final Collection<? extends E> collection) {
        final boolean added = super.addAll(collection);
        sort();
        return added;
    }

    public void sort() {
        Collections.sort(delegate());
    }

    @Override
    protected List<E> delegate() {
        return delegator;
    }
}
