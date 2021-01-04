package com.wavemaker.commons.util;

import com.wavemaker.commons.WMRuntimeException;


/*
*  This class is used to validate File Paths having /.., ../, ./ etc which are directory traversal elements
*  by which attacker can gain access to parent directory.
* */

public class FileValidationUtils {

    public static String validateFilePath(String path) {
        if (path != null) {
            for (int i = path.length(); i > 0;) {
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
