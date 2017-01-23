package com.wavemaker.studio.common.properties;

import java.util.*;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 14/4/16
 */
public class SortedProperties extends Properties {

    @Override
    public Enumeration keys() {
        Enumeration keysEnum = super.keys();
        List<String> keyList = new ArrayList<>();
        while (keysEnum.hasMoreElements()) {
            keyList.add((String) keysEnum.nextElement());
        }
        Collections.sort(keyList);
        return Collections.enumeration(keyList);
    }

    @Override
    public Set<String> stringPropertyNames() {
        return new TreeSet<>(super.stringPropertyNames());
    }

}