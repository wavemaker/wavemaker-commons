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
package com.wavemaker.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.commons.io.IOUtils;

import com.wavemaker.commons.CommonConstants;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.io.exception.ResourceException;
import com.wavemaker.commons.util.WMIOUtils;

/**
 * Abstract base class for {@link FileContent}.
 *
 * @author Phillip Webb
 */
public abstract class AbstractFileContent implements FileContent {

    @Override
    public Reader asReader() {
        try {
            return new InputStreamReader(asInputStream(), CommonConstants.UTF8);
        } catch (UnsupportedEncodingException ex) {
            throw new WMRuntimeException(ex);
        }
    }

    @Override
    public String asString() {
        Reader reader = null;
        try {
             reader = asReader();
            return IOUtils.toString(reader);
        } catch (IOException e) {
            throw new ResourceException(e);
        } finally {
            WMIOUtils.closeSilently(reader);
        }
    }

    @Override
    public byte[] asBytes() {
        InputStream inputStream = null;
        try {
            inputStream = asInputStream();
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new ResourceException(e);
        } finally {
            WMIOUtils.closeSilently(inputStream);
        }
    }

    @Override
    public void copyTo(OutputStream outputStream) {
        InputStream inputStream = null;
        try {
            inputStream = asInputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            throw new ResourceException(e);
        } finally {
            WMIOUtils.closeSilently(inputStream);
            WMIOUtils.closeSilently(outputStream);
        }
    }

    @Override
    public void copyTo(Writer writer) {
        Reader reader = null;
        try {
            reader = asReader();
            IOUtils.copy(reader, writer);
        } catch (IOException e) {
            throw new ResourceException(e);
        } finally {
            WMIOUtils.closeSilently(reader);
            WMIOUtils.closeSilently(writer);
        }
    }

    @Override
    public Writer asWriter() {
        return asWriter(false);
    }

    @Override
    public Writer asWriter(boolean append) {
        try {
            return new OutputStreamWriter(asOutputStream(append), CommonConstants.UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new ResourceException(e);
        }
    }

    @Override
    public void write(File file) {
        write(file.getContent().asInputStream());
    }

    @Override
    public void write(InputStream inputStream) {
        OutputStream outputStream = null;
        try {
            outputStream = asOutputStream();
            IOUtils.copyLarge(inputStream, outputStream);
        } catch (IOException e) {
            throw new ResourceException(e);
        } finally {
            WMIOUtils.closeSilently(inputStream);
            WMIOUtils.closeSilently(outputStream);
        }
    }

    @Override
    public void write(Reader reader) {
        Writer writer = null;
        try {
            writer = asWriter();
            IOUtils.copyLarge(reader, writer);
        } catch (IOException e) {
            throw new ResourceException(e);
        } finally {
            WMIOUtils.closeSilently(reader);
            WMIOUtils.closeSilently(writer);
        }
    }

    @Override
    public void write(String string) {
        Writer writer = null;
        try {
            writer = asWriter();
            IOUtils.write(string, writer);
        } catch (IOException e) {
            throw new ResourceException(e);
        } finally {
            WMIOUtils.closeSilently(writer);
        }
    }
}
