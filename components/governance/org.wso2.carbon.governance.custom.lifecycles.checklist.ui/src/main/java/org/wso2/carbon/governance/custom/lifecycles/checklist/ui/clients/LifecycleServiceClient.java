/*
 * Copyright (c) 2006, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.governance.custom.lifecycles.checklist.ui.clients;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.CarbonConstants;
import org.wso2.carbon.governance.custom.lifecycles.checklist.stub.CustomLifecyclesChecklistAdminServiceStub;
import org.wso2.carbon.governance.custom.lifecycles.checklist.stub.beans.xsd.LifecycleBean;
import org.wso2.carbon.governance.custom.lifecycles.checklist.stub.services.ArrayOfString;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.ui.CarbonUIUtil;
import org.wso2.carbon.utils.ServerConstants;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;

public class LifecycleServiceClient {

    private static final Log log = LogFactory.getLog(LifecycleServiceClient.class);

    private CustomLifecyclesChecklistAdminServiceStub stub;
    private String epr;

    public LifecycleServiceClient (
            String cookie, String backendServerURL, ConfigurationContext configContext)
            throws RegistryException {

        epr = backendServerURL + "CustomLifecyclesChecklistAdminService";

        try {
            stub = new CustomLifecyclesChecklistAdminServiceStub(configContext, epr);

            ServiceClient client = stub._getServiceClient();
            Options option = client.getOptions();
            option.setManageSession(true);
            option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, cookie);

        } catch (AxisFault axisFault) {
            String msg = "Failed to initiate lifecycles service client. " + axisFault.getMessage();
            log.error(msg, axisFault);
            throw new RegistryException(msg, axisFault);
        }
    }

    public LifecycleServiceClient(String cookie, ServletConfig config, HttpSession session)
            throws RegistryException {

        String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
        ConfigurationContext configContext = (ConfigurationContext) config.
                getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
        epr = backendServerURL + "CustomLifecyclesChecklistAdminService";

        try {
            stub = new CustomLifecyclesChecklistAdminServiceStub(configContext, epr);

            ServiceClient client = stub._getServiceClient();
            Options option = client.getOptions();
            option.setManageSession(true);
            option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, cookie);

        } catch (AxisFault axisFault) {
            String msg = "Failed to initiate lifecycles service client. " + axisFault.getMessage();
            log.error(msg, axisFault);
            throw new RegistryException(msg, axisFault);
        }   
    }

    public LifecycleServiceClient(ServletConfig config, HttpSession session)
            throws RegistryException {

        String cookie = (String)session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
        String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
        ConfigurationContext configContext = (ConfigurationContext) config.
                getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
        epr = backendServerURL + "CustomLifecyclesChecklistAdminService";

        try {
            stub = new CustomLifecyclesChecklistAdminServiceStub(configContext, epr);

            ServiceClient client = stub._getServiceClient();
            Options option = client.getOptions();
            option.setManageSession(true);
            option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, cookie);

        } catch (AxisFault axisFault) {
            String msg = "Failed to initiate lifecycles service client. " + axisFault.getMessage();
            log.error(msg, axisFault);
            throw new RegistryException(msg, axisFault);
        }
    }

    public LifecycleBean getLifecycleBean(String path) throws Exception {
        return stub.getLifecycleBean(path);       
    }

    public void addAspect(String path, String aspect) throws Exception {
        stub.addAspect(path, aspect);
    }

    public void invokeAspect(String path, String aspect, String action, String[] items) throws Exception {
        stub.invokeAspect(path, aspect, action, items);
    }

    public void invokeAspectWithParams(String path, String aspect, String action, String[] items,String[][] params) throws Exception {
        // TODO: Janaka
        ArrayOfString[] paramsArray = new ArrayOfString[params.length];

        for (int i = 0; i < params.length; i++) {
            String[] param = params[i];
            ArrayOfString arrayOfString = new ArrayOfString();
            arrayOfString.addArray(param[0]);
            arrayOfString.addArray(param[1]);
            paramsArray[i] = arrayOfString;
        }
        stub.invokeAspectWithParams(path, aspect, action, items, paramsArray);
    }

    public void removeAspect(String path, String aspect) throws Exception {
        stub.removeAspect(path, aspect);
    }

    public String[] getAllDependencies(String path) throws Exception{
        return stub.getAllDependencies(path);
    }

    public void setDefaultAspect(String path, String aspect) throws Exception {
        stub.setDefaultAspect(path, aspect);
    }
}
