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
    String forwardTo = null;
    User[] usersOfGroup = null;
    String[] names = null;
    List<String> lstNewUserNames = new ArrayList<String>();
    List<String> lstUsersOfGroup = new ArrayList<String>();
    List<String> lstUserAddNew = new ArrayList<String>();
    List<String> lstUserRemove = new ArrayList<String>();
    int groupId = Integer.parseInt(request.getParameter("groupId"));
    try {
        String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
        ConfigurationContext configContext =
                (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
        String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
        GroupManagerClient serviceClient = new GroupManagerClient(configContext, serverURL, cookie);

        String userNames = request.getParameter("userNames");

        if(userNames != null && !userNames.trim().equals("")) {
            names = userNames.split(",");
            lstNewUserNames = Arrays.asList(names);
        }

        usersOfGroup = serviceClient.getListUserOfGroup(groupId);
        if(usersOfGroup != null) {
            for(User user : usersOfGroup) {
                lstUsersOfGroup.add(user.getUserName());
            }
        }
        for(int i = 0; i < lstNewUserNames.size(); i++) {
            if(!lstUsersOfGroup.contains(lstNewUserNames.get(i))) {
                lstUserAddNew.add(lstNewUserNames.get(i));
            }
        }
        for(int i = 0; i < lstUsersOfGroup.size(); i++) {
            if(!lstNewUserNames.contains(lstUsersOfGroup.get(i))) {
                lstUserRemove.add(lstUsersOfGroup.get(i));
            }
        }

        if(lstUserAddNew.size() > 0) {
            String[] userAdd = new String[lstUserAddNew.size()];

            String[] userAddResult = serviceClient.addUsersToGroup(groupId, lstUserAddNew.toArray(userAdd));
            if(userAddResult != null && userAddResult.length > 0) {
                StringBuilder lstUserName = new StringBuilder();
                for(String name : userAddResult) {
                    lstUserName.append(name).append(", ");
                }
                errorMessage = "Cannot add user " + lstUserName + "to group";
                CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.ERROR, request);
            }
        }

        if(lstUserRemove.size() > 0) {
            String[] userRemove = new String[lstUserRemove.size()];
            String[] userRemoveResult = serviceClient.removeUsersFromGroup(groupId, lstUserRemove.toArray(userRemove));
            if(userRemoveResult != null && userRemoveResult.length > 0) {
                StringBuilder lstUserName = new StringBuilder();
                for(String name : userRemoveResult) {
                    lstUserName.append(name).append(", ");
                }
                errorMessage = "Cannot remove user " + lstUserName + "from group";
                CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.ERROR, request);
            }
        }

    } catch (Exception e) {
        errorMessage = "Exception " + " : " + e.getMessage();
        CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.ERROR, request);
    }
%>