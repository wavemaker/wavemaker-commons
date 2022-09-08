/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wavemaker.commons.json.module;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 3/10/18
 */
public class CircularLoopHandlingSerializer<T> extends JsonSerializer<T> implements ResolvableSerializer,
        ContextualSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CircularLoopHandlingSerializer.class);
    /**
     * Stack to store object call stack and to determine cycling dependency during serialization.
     */
    private static final ThreadLocal<Deque<Object>> objectReferenceStackTL = new ThreadLocal<>();

    private final BeanSerializerBase delegate;
    private final boolean failOnCircularReferences;


    public CircularLoopHandlingSerializer(final BeanSerializerBase delegate, final boolean failOnCircularReferences) {
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

    @Override
    public JsonSerializer<?> createContextual(
            final SerializerProvider prov, final BeanProperty property) throws JsonMappingException {
        final JsonSerializer<?> contextual = delegate.createContextual(prov, property);
        return wrapIfNeeded(contextual);
    }

    @Override
    public void resolve(final SerializerProvider provider) throws JsonMappingException {
        delegate.resolve(provider);
    }

    @Override
    public JsonSerializer<T> unwrappingSerializer(final NameTransformer unwrapper) {
        return (JsonSerializer<T>) wrapIfNeeded(delegate.unwrappingSerializer(unwrapper));
    }

    @Override
    public JsonSerializer<T> replaceDelegatee(final JsonSerializer<?> delegatee) {
        return (JsonSerializer<T>) wrapIfNeeded(delegate.replaceDelegatee(delegatee));
    }

    @Override
    public JsonSerializer<?> withFilterId(final Object filterId) {
        return wrapIfNeeded(delegate.withFilterId(filterId));
    }

    @Override
    public void serializeWithType(
            final T value, final JsonGenerator gen, final SerializerProvider serializers,
            final TypeSerializer typeSer) throws IOException {
        delegate.serializeWithType(value, gen, serializers, typeSer);
    }

    @Override
    public Class<T> handledType() {
        return (Class<T>) delegate.handledType();
    }

    @Override
    public boolean isEmpty(final T value) {
        return delegate.isEmpty(value);
    }

    @Override
    public boolean isEmpty(final SerializerProvider provider, final T value) {
        return delegate.isEmpty(provider, value);
    }

    @Override
    public boolean usesObjectId() {
        return delegate.usesObjectId();
    }

    @Override
    public boolean isUnwrappingSerializer() {
        return delegate.isUnwrappingSerializer();
    }

    @Override
    public JsonSerializer<?> getDelegatee() {
        return delegate.getDelegatee();
    }

    @Override
    public Iterator<PropertyWriter> properties() {
        return delegate.properties();
    }

    @Override
    public void acceptJsonFormatVisitor(
            final JsonFormatVisitorWrapper visitor, final JavaType type) throws JsonMappingException {
        delegate.acceptJsonFormatVisitor(visitor, type);
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
            objectReferenceStackTL.remove(); // gc
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

    private JsonSerializer wrapIfNeeded(JsonSerializer serializer) {
        return serializer instanceof BeanSerializerBase ?
                new CircularLoopHandlingSerializer<>((BeanSerializerBase) serializer, failOnCircularReferences)
                : serializer;
    }
}
