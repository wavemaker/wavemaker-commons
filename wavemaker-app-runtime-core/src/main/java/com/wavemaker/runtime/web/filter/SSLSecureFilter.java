package com.wavemaker.runtime.web.filter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import com.wavemaker.runtime.WMAppContext;
import com.wavemaker.runtime.security.config.WMAppSecurityConfig;
import com.wavemaker.studio.common.model.security.SSLConfig;
import com.wavemaker.studio.common.pattern.URLPattern;
import com.wavemaker.studio.common.util.CoreFilterUtil;


/**
 * SSL Filter for Wavemaker Runtime.
 *
 * @author Arjun Sahasranam
 */
public class SSLSecureFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(SSLSecureFilter.class);

    private static final String WM_APP_SECURITY_CONFIG = "WMAppSecurityConfig";
    private List<URLPattern> excludedUrlsList = null;

    private final SSLConfig sslConfig = new SSLConfig();

    @Override
    protected void initFilterBean() throws ServletException {
        WMAppSecurityConfig wmAppSecurityConfig = WMAppContext.getInstance().getSpringBean(WM_APP_SECURITY_CONFIG);
        SSLConfig sslConfig = wmAppSecurityConfig.getSslConfig();
        if (sslConfig != null) {
            this.sslConfig.setUseSSL(sslConfig.isUseSSL());
            this.sslConfig.setSslPort(sslConfig.getSslPort());
            this.sslConfig.setExcludedUrls(sslConfig.getExcludedUrls());
            excludedUrlsList = CoreFilterUtil.extractExcludedUrlsList(this.sslConfig.getExcludedUrls());
            logger.info("SSLConfig set in filter {}", sslConfig);
        }
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (sslConfig.isUseSSL()) {
            if (request.isSecure()) {
                filterChain.doFilter(request, response);
            } else if (CoreFilterUtil.isExcluded(request, excludedUrlsList)) {
                logger.debug("RequestURI {} is a excluded external request {}", request.getRequestURI());
                filterChain.doFilter(request, response);
            } else {
                String requestUri = request.getRequestURI();
                String queryStr = request.getQueryString();
                String uriWithQueryStr = requestUri + ((queryStr != null) ? ("?" + queryStr) : "");
                URL redirectUrl = new URL("https", request.getServerName(), sslConfig.getSslPort(), uriWithQueryStr);
                logger.debug("Redirecting current request {} to https request {}", requestUri, redirectUrl.toString());
                response.sendRedirect(redirectUrl.toExternalForm());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    public void destroy() {
    }
}