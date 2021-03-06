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


<%
    String errorMessage = "";
    Group group = new Group();
    try {
        String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
        ConfigurationContext configContext =
                (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
        String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
        GroupManagerClient serviceClient = new GroupManagerClient(configContext, serverURL, cookie);
        // retrieve session attributes
        group.setCreatedUser((String) session.getAttribute("logged-user"));
        group.setGroupName(request.getParameter("groupName"));
        group.setCreatedTime((new Date()).getTime());
        group.setGroupType(GroupType.GROUP_USER.name());
        int groupId = serviceClient.createGroup(group);
        if(groupId != 0) {
            String userNames = request.getParameter("userNames");
            String[] listUser = null;
            if(userNames != null && !userNames.trim().equals("")) {
                listUser = userNames.split(",");
                String[] result = serviceClient.addUsersToGroup(groupId, listUser);
                if(result != null && result.length > 0) {
                    StringBuilder lstUserName = new StringBuilder();
                    for(String name : result) {
                        lstUserName.append(name).append(", ");
                    }
                    errorMessage = "Cannot add user " + lstUserName + "to group " + group.getGroupName();
                    CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.WARNING, request);
                }
            }
            errorMessage = "Add group " + request.getParameter("groupName") + " success";
            CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.INFO, request);
        } else {
            if(groupId == 0) {
                errorMessage = "Group with name " + group.getGroupName() + " is exists";
                CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.WARNING, request);
            } else {
                errorMessage = "Cannot create group with name " + group.getGroupName();
                CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.ERROR, request);
            }
        }

        //users = serviceClient.getUsers();
    } catch (Exception e) {
        errorMessage = "Exception " + " : " + e.getMessage();
        CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.ERROR, request);
    }
%>