package com.wavemaker.runtime.report.generator;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

import com.wavemaker.runtime.file.model.DownloadResponse;
import com.wavemaker.runtime.report.helper.JasperReportHelper;
import com.wavemaker.runtime.report.model.ExportType;
import com.wavemaker.runtime.report.model.ReportContext;

/**
 * Created by kishorer on 23/5/16.
 */
public abstract class AbstractJasperReportGenerator implements JasperReportGenerator {

    protected DownloadResponse buildReport(ReportContext reportContext) {
        ExportType reportExportType = reportContext.getExportType();
        JasperReportHelper jasperReportHelper = JasperReportHelper.getInstance();
        InputStream dataStream = jasperReportHelper.exportAsStream(reportContext);
        return new DownloadResponse(dataStream, null, getFileName(reportContext.getReportName(), reportExportType));
    }

    protected String getFileName(String reportName, ExportType reportExportType) {
        return reportName + reportExportType.getExtension();
    }
}
