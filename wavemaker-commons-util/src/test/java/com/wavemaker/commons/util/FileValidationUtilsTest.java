package com.wavemaker.commons.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.wavemaker.commons.WMRuntimeException;

public class FileValidationUtilsTest {

    private List<String> positiveCase;
    private List<String> negativeCase;

    @Before
    public void initialize() {
        positiveCase = new ArrayList<>();
        positiveCase.add("/etc/master.passwd");
        positiveCase.add("/master.passwd");
        positiveCase.add("etc/passwd");

        negativeCase = new ArrayList<>();
        negativeCase.add("../../etc/passwd");
        negativeCase.add("../../../administrator/inbox");
        negativeCase.add("../_config.php%00");
    }

    @Test
    public void validateFilePath() {
        positiveCase.forEach(FileValidationUtils::validateFilePath);
    }

    @Test(expected = WMRuntimeException.class)
    public void validateFilePathThrowsWMRuntimeExceptionWhenPathContainsDots() {
        negativeCase.forEach(FileValidationUtils::validateFilePath);
    }
}