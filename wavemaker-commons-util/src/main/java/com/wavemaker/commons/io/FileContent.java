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
package com.wavemaker.commons.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * Provides access to {@link File} content in a variety of ways.
 *
 * @author Phillip Webb
 * @see File
 */
public interface FileContent {

    /**
     * Return an {@link InputStream} that can be used to access file contents. This method can be called multiple times
     * if required. The stream should be closed by the caller.
     *
     * @return The file contents {@link InputStream}
     */
    InputStream asInputStream();

    /**
     * Return a {@link Reader} that can be used to access file contents. This method can be called multiple times if
     * required. The reader should be closed by the caller.
     *
     * @return the file contents {@link Reader}
     */
    Reader asReader();

    /**
     * Return the contents of the file as a <tt>String</tt>. This method should be used with caution if working with
     * large files.
     *
     * @return the contents as a <tt>String</tt>
     */
    String asString();

    /**
     * Return the contents of the file as a new <tt>byte array</tt>. This method should be used with caution if working
     * with large files.
     *
     * @return the contents as a new byte array.
     */
    byte[] asBytes();

    /**
     * Copy the contents of the file to another stream, closing the stream when complete.
     */
    void copyTo(OutputStream outputStream);

    /**
     * Copy the contents of the file to another writer, closing the writer when complete.
     */
    void copyTo(Writer writer);

    /**
     * Return an {@link OutputStream} that can be used to write file contents. The output stream should be closed by the
     * caller. When possible, consider using the {@link #write(InputStream)} method instead to ensure that streams are
     * closed.
     *
     * @return The output stream
     */
    OutputStream asOutputStream();

    /**
     * @param append if <code>true</code>, open the file OutputStream in append mode, otherwise as new file
     *               Return an {@link OutputStream} that can be used to write file contents. The output stream should be closed by the
     *               caller. When possible, consider using the {@link #write(InputStream)} method instead to ensure that streams are
     *               closed.
     *
     * @return The output stream
     */
    OutputStream asOutputStream(boolean append);

    /**
     * Return a {@link Writer} that can be used to write file contents. The writer should be closed by the caller. When
     * possible, consider using the {@link #write(Reader)} method instead to ensure that streams are closed.
     *
     * @return The writer
     */
    Writer asWriter();

    /**
     * @param append if <code>true</code>, open the file Writer in append mode, otherwise as new file
     *               Return a {@link Writer} that can be used to write file contents with append mode. The writer should be closed by
     *               the caller. When possible, consider using the {@link #write(Reader)} method instead to ensure that streams are closed.
     *
     * @return The writer
     */
    Writer asWriter(boolean append);

    /**
     * Write the contents of the specified file to this file.
     *
     * @param file the file to copy.
     */
    void write(File file);

    /**
     * Write the contents of the specified output stream to this file, closing the stream when complete.
     *
     * @param inputStream the input stream to write
     */
    void write(InputStream inputStream);

    /**
     * Write the contents of the specified reader to this file, closing the reader when complete.
     *
     * @param reader the reader to write
     */
    void write(Reader reader);

    /**
     * Write the contents of the specified string to this file.
     *
     * @param string the string contents
     */
    void write(String string);
}
