package com.wavemaker.studio.common.util;

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
