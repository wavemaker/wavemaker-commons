package com.wavemaker.studio.common.data.type;

/**
 * Created by sunilp on 21/7/15.
 */

import java.sql.Timestamp;

import org.jadira.usertype.dateandtime.shared.spi.AbstractSingleColumnUserType;
import org.joda.time.LocalDateTime;

import com.wavemaker.studio.common.data.mapper.WMDateColumnLocalDateTimeMapper;

/**
 * @author Uday Shankar
 */
public class WMPersistentLocalDateTime extends AbstractSingleColumnUserType<LocalDateTime, Timestamp, WMDateColumnLocalDateTimeMapper> {

    private static final long serialVersionUID = 1651096099046282660L;
}
