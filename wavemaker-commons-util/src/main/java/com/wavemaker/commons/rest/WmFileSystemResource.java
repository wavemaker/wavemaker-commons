package com.wavemaker.commons.rest;

import java.io.File;

import org.springframework.core.io.FileSystemResource;

/**
 * Created by srujant on 14/2/17.
 */
public class WmFileSystemResource extends FileSystemResource {

    private String contentType;

    public WmFileSystemResource(File file, String contentType) {
        super(file);
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

}


