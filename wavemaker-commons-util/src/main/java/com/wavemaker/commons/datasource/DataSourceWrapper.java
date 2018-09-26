package com.wavemaker.commons.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.wavemaker.commons.web.filter.RequestTrackingFilter;
import com.wavemaker.commons.web.filter.ServerTimingMetric;

/**
 * @author Uday Shankar
 */
public class DataSourceWrapper implements DataSource {
    
    private DataSource delegateDataSource;
    
    @Autowired
    private RequestTrackingFilter requestTrackingFilter;

    public DataSourceWrapper(DataSource delegateDataSource) {
        this.delegateDataSource = delegateDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        long startTime = System.currentTimeMillis();
        Connection connection = delegateDataSource.getConnection();
        long endTime = System.currentTimeMillis();
        requestTrackingFilter.addServerTimingMetrics(new ServerTimingMetric("connAcquisition", endTime-startTime, null));
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        long startTime = System.currentTimeMillis();
        Connection connection = delegateDataSource.getConnection(username, password);
        long endTime = System.currentTimeMillis();
        requestTrackingFilter.addServerTimingMetrics(new ServerTimingMetric("connAcquisition", endTime-startTime, null));
        return connection;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return delegateDataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        delegateDataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        delegateDataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return delegateDataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return delegateDataSource.getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return delegateDataSource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return delegateDataSource.isWrapperFor(iface);
    }
}
