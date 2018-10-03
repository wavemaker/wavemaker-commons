package com.wavemaker.commons.json.module;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 3/10/18
 */
public class CircularLoopHandlingSerializer<T> extends JsonSerializer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CircularLoopHandlingSerializer.class);
    /**
     * Stack to store object call stack and to determine cycling dependency during serialization.
     */
    private static final ThreadLocal<Deque<Object>> objectReferenceStackTL = new ThreadLocal<>();

    private final JsonSerializer<T> delegate;
    private final boolean failOnCircularReferences;


    public CircularLoopHandlingSerializer(final JsonSerializer<T> delegate, final boolean failOnCircularReferences) {
        this.delegate = delegate;
        this.failOnCircularReferences = failOnCircularReferences;
    }


    @Override
    public void serialize(
            final T value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        LOGGER.debug("Serialization started for :{}", value.getClass());

        if (hasCyclicReference(value) && !delegate.usesObjectId()) {
            handleCyclicReference(gen, serializers);
            return;
        }

        notifyStartSerialization(value);
        try {
            delegate.serialize(value, gen, serializers);
        } finally {
            LOGGER.debug("Serialization completed for:{}", value.getClass());
            notifyEndSerialization();
        }
    }

    public void notifyStartSerialization(Object value) {
        getObjectRefStack().push(value);
    }

    /**
     * Removes the last added object from the stack.
     */
    public void notifyEndSerialization() {
        getObjectRefStack().pop();
        if (getObjectRefStack().isEmpty()) {
            objectReferenceStackTL.set(null); // gc
        }
    }


    /**
     * Check for the cycle in object serialization stack. It ignores Self references as they are handled separately.
     *
     * @param value to be check for cycle.
     * @return true when value in serialization reference stack and not as top value else false
     */
    public boolean hasCyclicReference(Object value) {
        // ignores self references,
        return (!getObjectRefStack().isEmpty() && !getObjectRefStack().getFirst().equals(value)) &&
                getObjectRefStack().contains(value);
    }

    public void handleCyclicReference(final JsonGenerator jgen, SerializerProvider provider) throws IOException {
        String refStack = printableRefStack();
        if (failOnCircularReferences) {
            throw new JsonMappingException(jgen,
                    "Cyclic-reference leading to cycle, Object Reference Stack:" + refStack);
        }
        // else serializing as NULL.
        provider.defaultSerializeValue(null, jgen);
    }

    private String printableRefStack() {
        StringBuilder sb = new StringBuilder();
        Object[] objects = getObjectRefStack().toArray();
        for (int i = objects.length - 1; i >= 0; i--) {
            sb.append(objects[i].getClass().getSimpleName());
            if (i > 0) {
                sb.append("->");
            }
        }
        return sb.toString();
    }

    private Deque<Object> getObjectRefStack() {
        if (objectReferenceStackTL.get() == null) {
            objectReferenceStackTL.set(new ArrayDeque<>());
        }
        return objectReferenceStackTL.get();
    }

}
