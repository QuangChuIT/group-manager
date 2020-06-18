<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.wso2.carbon.group.mgt.ui.GroupManagerClient" %>
<%@ page import="org.wso2.carbon.group.mgt.ui.GroupType" %>
<%@ page import="org.wso2.carbon.group.mgt.data.xsd.Group" %>
<%@ page import="org.wso2.carbon.group.mgt.data.xsd.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javax.swing.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Arrays" %>


<%
    String errorMessage = "";
    try {
        String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
        ConfigurationContext configContext =
                (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
        String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
        GroupManagerClient serviceClient = new GroupManagerClient(configContext, serverURL, cookie);

        int groupId = Integer.parseInt(request.getParameter("groupId"));
        boolean isSuccess = serviceClient.deleteGroup(groupId);
        if(!isSuccess) {
            errorMessage = "Cannot delete group";
            CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.ERROR, request);
        }
    } catch (Exception e) {
        errorMessage = "Exception " + " : " + e.getMessage();
        CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.ERROR, request);
    }
%>