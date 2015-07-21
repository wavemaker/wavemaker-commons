package com.wavemaker.studio.common.data.mapper;

/**
 * Created by sunilp on 21/7/15.
 */

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.type.AbstractStandardBasicType;
import org.hibernate.type.StandardBasicTypes;
import org.jadira.usertype.dateandtime.joda.util.Formatter;
import org.jadira.usertype.dateandtime.shared.spi.AbstractColumnMapper;
import org.jadira.usertype.dateandtime.shared.spi.ColumnMapper;
import org.joda.time.LocalDateTime;

public class WMDateColumnLocalDateTimeMapper extends AbstractColumnMapper<LocalDateTime, Timestamp> implements ColumnMapper<LocalDateTime, Timestamp> {

    private static final long serialVersionUID = -7670411089210984705L;

    @Override
    public final AbstractStandardBasicType<Date> getHibernateType() {
        return StandardBasicTypes.TIMESTAMP;
    }

    @Override
    public final int getSqlType() {
        return -777;
    }

    @Override
    public LocalDateTime fromNonNullString(String s) {
        return new LocalDateTime(s);
    }

    @Override
    public LocalDateTime fromNonNullValue(Timestamp value) {
        return Formatter.LOCAL_DATETIME_FORMATTER.parseDateTime(value.toString()).toLocalDateTime();
    }

    @Override
    public String toNonNullString(LocalDateTime value) {
        return value.toString();
    }

    @Override
    public Timestamp toNonNullValue(LocalDateTime value) {

        String formattedTimestamp = Formatter.LOCAL_DATETIME_FORMATTER.print(value);
        if (formattedTimestamp.endsWith(".")) {
            formattedTimestamp = formattedTimestamp.substring(0, formattedTimestamp.length() - 1);
        }

        final Timestamp timestamp = Timestamp.valueOf(formattedTimestamp);
        return timestamp;
    }
}
