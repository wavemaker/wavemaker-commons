package com.wavemaker.commons.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;

import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.util.WMIOUtils;

/**
 * @author Kishore Routhu on 10/11/17 12:21 PM.
 */
public class FileRenameOperation implements ResourceOperation<Resource> {

    private String newFileName;

    public FileRenameOperation(String newFileName) {
        this.newFileName = newFileName;
    }

    @Override
    public void perform(Resource resource) {
        String fileExtension = FilenameUtils.getExtension(resource.getName());
        Path path = WMIOUtils.getJavaIOFile(resource).toPath();
        try {
            Files.move(path, path.resolveSibling(newFileName + FilenameUtils.EXTENSION_SEPARATOR + fileExtension));
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to renameFile"+resource.getName(),e);
        }
    }
}
