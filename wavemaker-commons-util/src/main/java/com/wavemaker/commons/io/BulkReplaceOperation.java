package com.wavemaker.commons.io;

import java.util.HashMap;
import java.util.Map;

/**
 * This operation can be used for bulk replace of tokens. The operation has a single write at the end.
 *
 * Created by ArjunSahasranam on 12/10/15.
 */
public class BulkReplaceOperation implements ResourceOperation<File> {

    private final Map<String, String> map;

    public BulkReplaceOperation() {
        map = new HashMap<>();
    }

    public void add(String from, String to) {
        map.put(from, to);
    }

    public void addAll(Map<String, String> values) {
        map.putAll(values);
    }

    @Override
    public void perform(final File resource) {
        String content = resource.getContent().asString();
        for (String from : map.keySet()) {
            content = content.replace(from, map.get(from));
        }
        resource.getContent().write(content);
    }
}
