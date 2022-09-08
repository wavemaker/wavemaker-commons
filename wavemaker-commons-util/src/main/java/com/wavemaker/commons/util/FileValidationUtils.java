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

package com.wavemaker.commons.util;

import com.wavemaker.commons.WMRuntimeException;


/*
 *  This class is used to validate File Paths having /.., ../, ./ etc which are directory traversal elements
 *  by which attacker can gain access to parent directory.
 * */

public class FileValidationUtils {

    public static String validateFilePath(String path) {
        if (path != null) {
            for (int i = path.length(); i > 0; ) {
                int slashIndex = path.lastIndexOf('/', i - 1);
                int gap = i - slashIndex;
                if (gap == 2 && path.charAt(slashIndex + 1) == '.') {
                    throw new WMRuntimeException("Path contains Directory Traversal elements");
                }
                if (gap == 3 && path.charAt(slashIndex + 1) == '.' && path.charAt(slashIndex + 2) == '.') {
                    throw new WMRuntimeException("Path contains Directory Traversal elements");
                }
                i = slashIndex;
            }
        }
        return path;
    }
}
