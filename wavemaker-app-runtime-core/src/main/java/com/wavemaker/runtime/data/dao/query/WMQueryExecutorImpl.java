/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.runtime.data.dao.query;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.wavemaker.runtime.data.dao.callbacks.NamedQueryExporterCallback;
import com.wavemaker.runtime.data.dao.callbacks.PaginatedQueryCallback;
import com.wavemaker.runtime.data.dao.callbacks.QueryCallback;
import com.wavemaker.runtime.data.dao.callbacks.UpdateQueryCallback;
import com.wavemaker.runtime.data.dao.query.providers.AppRuntimeParameterProvider;
import com.wavemaker.runtime.data.dao.query.providers.RuntimeParametersProvider;
import com.wavemaker.runtime.data.dao.query.providers.RuntimeQueryProvider;
import com.wavemaker.runtime.data.dao.query.providers.SessionBackedQueryProvider;
import com.wavemaker.runtime.data.dao.query.types.SessionBackedParameterResolver;
import com.wavemaker.runtime.data.dao.util.CustomQueryAdapter;
import com.wavemaker.runtime.data.dao.util.PageUtils;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.model.CustomQuery;
import com.wavemaker.runtime.data.model.queries.RuntimeQuery;
import com.wavemaker.runtime.file.model.DownloadResponse;
import com.wavemaker.runtime.file.model.Downloadable;

public class WMQueryExecutorImpl implements WMQueryExecutor {

    private HibernateTemplate template;
    private SessionBackedParameterResolver parameterResolvers;

    @PostConstruct
    private void init() {
        parameterResolvers =
                new SessionBackedParameterResolver((SessionFactoryImplementor) template.getSessionFactory());
    }

    @Override
    public Page<Object> executeNamedQuery(
            final String queryName, final Map<String, Object> params, final Pageable pageable) {
        return executeNamedQuery(queryName, params, Object.class, PageUtils.defaultIfNull(pageable));
    }

    @Override
    public <T> T executeNamedQuery(
            final String queryName, final Map<String, Object> params, final Class<T> returnType) {
        SessionBackedQueryProvider<T> queryProvider = new SessionBackedQueryProvider<>(queryName, returnType);
        return template.execute(new QueryCallback<>(queryProvider,
                new AppRuntimeParameterProvider(params, parameterResolvers.getResolver(queryName))))
                .orElseThrow(() -> new EntityNotFoundException("No row exists for given parameters:" + params));
    }

    @Override
    public <T> Page<T> executeNamedQuery(
            final String queryName, final Map<String, Object> params, final Class<T> returnType,
            final Pageable pageable) {
        SessionBackedQueryProvider<T> queryProvider = new SessionBackedQueryProvider<>(queryName, returnType);

        return template.execute(new PaginatedQueryCallback<>(queryProvider,
                new AppRuntimeParameterProvider(params, parameterResolvers.getResolver(queryName)),
                PageUtils.defaultIfNull(pageable)));
    }

    @Override
    public int executeNamedQueryForUpdate(final String queryName, final Map<String, Object> params) {
        SessionBackedQueryProvider<Integer> queryProvider = new SessionBackedQueryProvider<>(queryName, Integer.class);

        return template.execute(new UpdateQueryCallback(queryProvider,
                new AppRuntimeParameterProvider(params, parameterResolvers.getResolver(queryName))));

    }

    @Override
    public Page<Object> executeRuntimeQuery(final RuntimeQuery query, final Pageable pageable) {
        final RuntimeQueryProvider<Object> queryProvider = RuntimeQueryProvider.from(query, Object.class);
        final RuntimeParametersProvider parametersProvider = new RuntimeParametersProvider(query);

        return template.execute(
                new PaginatedQueryCallback<>(queryProvider, parametersProvider, PageUtils.defaultIfNull(pageable)));
    }

    @Override
    public int executeRuntimeQueryForUpdate(final RuntimeQuery query) {
        final RuntimeQueryProvider<Integer> queryProvider = RuntimeQueryProvider.from(query, Integer.class);
        final RuntimeParametersProvider parametersProvider = new RuntimeParametersProvider(query);

        return template.execute(new UpdateQueryCallback(queryProvider, parametersProvider));
    }

    public HibernateTemplate getTemplate() {
        return template;
    }


    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    @Override
    public <T> Downloadable exportNamedQueryData(
            final String queryName, final Map<String, Object> params, final ExportType exportType,
            Class<T> responseType, final Pageable pageable) {

        SessionBackedQueryProvider<T> queryProvider = new SessionBackedQueryProvider<>(queryName, responseType);
        AppRuntimeParameterProvider parameterProvider = new AppRuntimeParameterProvider(params, parameterResolvers
                .getResolver(queryName));

        NamedQueryExporterCallback<T> callback = new NamedQueryExporterCallback<>(queryProvider, parameterProvider,
                PageUtils.defaultIfNull(pageable), exportType, responseType);
        ByteArrayOutputStream outputStream = template.executeWithNativeSession(callback);
        return new DownloadResponse(new ByteArrayInputStream(outputStream.toByteArray()), exportType.getContentType(), queryName + exportType.getExtension());
    }

    @Override
    public Page<Object> executeCustomQuery(CustomQuery customQuery, Pageable pageable) {
        final RuntimeQuery runtimeQuery = CustomQueryAdapter.adapt(customQuery);
        return executeRuntimeQuery(runtimeQuery, PageUtils.defaultIfNull(pageable));
    }

    @Override
    public int executeCustomQueryForUpdate(final CustomQuery customQuery) {
        final RuntimeQuery runtimeQuery = CustomQueryAdapter.adapt(customQuery);
        return executeRuntimeQueryForUpdate(runtimeQuery);
    }
}