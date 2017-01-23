/**
 * Copyright (C) 2015 WaveMaker, Inc.
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
package com.wavemaker.studio.common.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertThat;

/**
 * Tests for {@link AbstractFileContent}.
 * 
 * @author Phillip Webb
 */
public class AbstFileContentTest {

    private final String CONTENT = "Test";

    private ByteArrayOutputStream outputStream;

    private ByteArrayInputStream inputStream;

    private AbstractFileContent content;

    @Before
    public void setup() {
        this.outputStream = Mockito.spy(new ByteArrayOutputStream());
        this.inputStream = Mockito.spy(new ByteArrayInputStream(this.CONTENT.getBytes()));
        this.content = new AbstractFileContent() {

            @Override
            public OutputStream asOutputStream() {
                return AbstFileContentTest.this.outputStream;
            }

            @Override
            public OutputStream asOutputStream(boolean append) {
                return AbstFileContentTest.this.outputStream;
            }

            @Override
            public InputStream asInputStream() {
                return AbstFileContentTest.this.inputStream;
            }
        };
    }

    @Test
    public void shouldGetAsReader() throws Exception {
        char[] cbuf = new char[4];
        Reader reader = this.content.asReader();
        reader.read(cbuf);
        assertThat(cbuf, Matchers.is(Matchers.equalTo(this.CONTENT.toCharArray())));
        reader.close();
        Mockito.verify(this.inputStream).close();
    }

    @Test
    public void shouldGetAsString() throws Exception {
        String string = this.content.asString();
        assertThat(string, Matchers.is(Matchers.equalTo(this.CONTENT)));
        Mockito.verify(this.inputStream).close();
    }

    @Test
    public void shouldGetAsBytes() throws Exception {
        byte[] bytes = this.content.asBytes();
        assertThat(bytes, Matchers.is(Matchers.equalTo(this.CONTENT.getBytes())));
        Mockito.verify(this.inputStream).close();
    }

    @Test
    public void shouldCopyToOutputStream() throws Exception {
        ByteArrayOutputStream copyStream = Mockito.spy(new ByteArrayOutputStream());
        this.content.copyTo(copyStream);
        assertThat(copyStream.toByteArray(), Matchers.is(Matchers.equalTo(this.CONTENT.getBytes())));
        Mockito.verify(this.inputStream).close();
        Mockito.verify(copyStream).close();
    }

    @Test
    public void shouldCopyToWriter() throws Exception {
        StringWriter writer = Mockito.spy(new StringWriter());
        this.content.copyTo(writer);
        assertThat(writer.toString(), Matchers.is(Matchers.equalTo(this.CONTENT)));
        Mockito.verify(this.inputStream).close();
        Mockito.verify(writer).close();
    }

    @Test
    public void shouldGetAsWriter() throws Exception {
        Writer writer = this.content.asWriter();
        writer.write(this.CONTENT.toCharArray());
        writer.close();
        assertThat(this.outputStream.toByteArray(), Matchers.is(this.CONTENT.getBytes()));
        Mockito.verify(this.outputStream).close();
    }

    @Test
    public void shouldWriteOutputStream() throws Exception {
        OutputStream outputStream = this.content.asOutputStream();
        outputStream.write(this.CONTENT.getBytes());
        outputStream.close();
        assertThat(this.outputStream.toByteArray(), Matchers.is(Matchers.equalTo(this.CONTENT.getBytes())));
        Mockito.verify(this.outputStream).close();
    }

    @Test
    public void shouldWriteReader() throws Exception {
        StringReader reader = Mockito.spy(new StringReader(this.CONTENT));
        this.content.write(reader);
        assertThat(this.outputStream.toByteArray(), Matchers.is(Matchers.equalTo(this.CONTENT.getBytes())));
        Mockito.verify(reader).close();
        Mockito.verify(this.outputStream).close();
    }

    @Test
    public void shouldWriteString() throws Exception {
        this.content.write(this.CONTENT);
        assertThat(this.outputStream.toByteArray(), Matchers.is(Matchers.equalTo(this.CONTENT.getBytes())));
        Mockito.verify(this.outputStream).close();
    }
}
