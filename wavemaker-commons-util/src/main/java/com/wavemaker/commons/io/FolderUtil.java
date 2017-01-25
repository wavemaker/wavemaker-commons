package com.wavemaker.commons.io;

import java.io.IOException;

import com.wavemaker.commons.util.IOUtils;
import com.wavemaker.commons.io.local.LocalFolder;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 18/10/15
 */
public abstract class FolderUtil {

    public static LocalFolder createTempFolder() {
        try {
            return new LocalFolder(IOUtils.createTempDirectory());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
