package com.wavemaker.runtime.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.wavemaker.common.util.SSLUtils;
import com.wavemaker.runtime.rest.model.RestRequestInfo;
import com.wavemaker.runtime.rest.model.RestResponse;

/**
 * @author Uday Shankar
 */
@Component
public class RestConnector {

    private final X509HostnameVerifier hostnameVerifier = new AllowAllHostnameVerifier();

    public RestResponse invokeRestCall(RestRequestInfo restRequestInfo) {
        HttpMethod httpMethod = HttpMethod.valueOf(restRequestInfo.getMethod());
        if(httpMethod == null) {
            throw new IllegalArgumentException("Invalid method value [" + restRequestInfo.getMethod() + "]");
        }
        RestTemplate restTemplate = new RestTemplate();


        HttpClientBuilder httpClientBuilder = HttpClients.custom();

        String endpointAddress = restRequestInfo.getEndpointAddress();
        if(endpointAddress.startsWith("https")) {
            httpClientBuilder.setSSLSocketFactory(new SSLConnectionSocketFactory(SSLUtils.getAllTrustedCertificateSSLContext(), hostnameVerifier));
        }
        if(restRequestInfo.getBasicAuth()) {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    AuthScope.ANY,
                    new UsernamePasswordCredentials(restRequestInfo.getUserName(), restRequestInfo.getPassword()));

            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }

        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory commonsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        MultiValueMap headersMap = new LinkedMultiValueMap();

        //set headers
        Map<String, String> headers = restRequestInfo.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headersMap.add(entry.getKey(), entry.getValue());
            }
        }

        String contentType = restRequestInfo.getContentType();
        if(!StringUtils.isBlank(contentType)) {
            headersMap.add("Content-Type", contentType);
        }

        restTemplate.setRequestFactory(commonsClientHttpRequestFactory);
        restTemplate.setErrorHandler(new WMRestServicesErrorHandler());

        HttpEntity requestEntity;

        if(HttpMethod.GET != httpMethod) {
            String requestBody = (restRequestInfo.getRequestBody() == null)?"":restRequestInfo.getRequestBody();
            requestEntity = new HttpEntity(requestBody, headersMap);
        } else {
            requestEntity = new HttpEntity(headersMap);
        }
        ResponseEntity<String> responseEntity = restTemplate.exchange(endpointAddress, httpMethod, requestEntity, String.class);

        RestResponse restResponse = new RestResponse();
        restResponse.setResponseBody(responseEntity.getBody());
        restResponse.setStatusCode(responseEntity.getStatusCode().value());
        Map<String, List<String>> responseHeaders = new HashMap<String, List<String>>();
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        for(String responseHeaderKey : httpHeaders.keySet()) {
            responseHeaders.put(responseHeaderKey, httpHeaders.get(responseHeaderKey));
        }
        MediaType mediaType = responseEntity.getHeaders().getContentType();
        if(mediaType != null) {
            String outputContentType = mediaType.toString();
            if(outputContentType.contains(";")) {
                outputContentType = outputContentType.substring(0, outputContentType.indexOf(";"));
            }
            restResponse.setContentType(outputContentType);
        }
        restResponse.setResponseHeaders(responseHeaders);
        restResponse.setResponseBody(restResponse.getResponseBody());
        return restResponse;
    }

    class WMRestServicesErrorHandler extends DefaultResponseErrorHandler {

        @Override
        protected boolean hasError(HttpStatus statusCode) {
            return false;
        }
    }
}