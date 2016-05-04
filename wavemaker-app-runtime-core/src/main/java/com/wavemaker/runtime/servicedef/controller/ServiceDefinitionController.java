package com.wavemaker.runtime.servicedef.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.runtime.servicedef.service.ServiceDefinitionService;
import com.wavemaker.studio.common.servicedef.model.ServiceDefinition;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 21/3/16
 */

@RestController
@RequestMapping(value = "/servicedefs")
public class ServiceDefinitionController {

    @Autowired
    private ServiceDefinitionService serviceDefinitionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, ServiceDefinition> listServiceDefs() {
        return serviceDefinitionService.listServiceDefs();
    }

}
