package com.wavemaker.runtime.data.export.hqlquery;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.ScrollableResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.runtime.data.export.DataExporter;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.model.returns.ReturnProperty;

/**
 * @author <a href="mailto:anusha.dharmasagar@wavemaker.com">Anusha Dharmasagar</a>
 * @since 8/11/16
 */
public class HQLQueryDataExporter extends DataExporter {

    private static final Logger logger = LoggerFactory.getLogger(HQLQueryDataExporter.class);


    private ScrollableResults results;

    private List<ReturnProperty> returnPropertyList;


    public HQLQueryDataExporter(ScrollableResults results, List<ReturnProperty> returnPropertyList) {
        this.results = results;
        this.returnPropertyList = returnPropertyList;
    }

    @Override
    public ByteArrayOutputStream export(final ExportType exportType) {
        logger.info(
                "Exporting all Records matching the given input query to the given exportType format " + exportType);
        Workbook workbook = HQLQueryExportBuilder.build(results, returnPropertyList);
        return exportWorkbook(workbook, exportType);
    }
}