package com.wavemaker.runtime.data.dialect;

import java.sql.Types;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StandardBasicTypes;

/**
 * Created by sunilp on 4/2/15.
 */
public class WMSQLServerDialect extends SQLServer2012Dialect {

    public WMSQLServerDialect() {
        super();
        registerHibernateType(Types.NCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.TEXT.getName());
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());

        registerColumnType(Types.CHAR, "nchar(1)");
    }
}
