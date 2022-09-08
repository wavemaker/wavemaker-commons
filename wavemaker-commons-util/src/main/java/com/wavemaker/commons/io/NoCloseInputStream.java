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

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * {@link InputStream} that does not perform any operation when {@link #close()} is called.
 *
 * @author Phillip Webb
 */
public class NoCloseInputStream extends FilterInputStream {

    public NoCloseInputStream(InputStream in) {
        super(in);
    }

    /**
     * Intentionally ignoring the implementation to avoid closing the enclosing stream
     * in such cases like iterating through zip entries... etc
     */
    @Override
    public void close() throws IOException {
    }

    public void doClose() throws IOException {
        super.close();
    }
}
