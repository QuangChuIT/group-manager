<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.wso2.carbon.group.mgt.ui.GroupManagerClient" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javax.swing.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="org.wso2.carbon.group.mgt.data.xsd.Role" %>
<%@ page import="org.wso2.carbon.group.mgt.ui.GroupType" %>

<%
    String errorMessage = "";
    Role[] rolesOfGroup = null;
    String[] names = null;
    List<String> lstNewRoleNames = new ArrayList<String>();
    List<String> lstRolesOfGroup = new ArrayList<String>();
    List<String> lstRoleAddNew = new ArrayList<String>();
    List<String> lstRoleRemove = new ArrayList<String>();
    int groupId = Integer.parseInt(request.getParameter("groupId"));
    try {
        String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
        ConfigurationContext configContext =
                (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
        String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
        GroupManagerClient serviceClient = new GroupManagerClient(configContext, serverURL, cookie);

        String roleNames = request.getParameter("roleNames");

        if(roleNames != null && !roleNames.trim().equals("")) {
            names = roleNames.split(",");
            lstNewRoleNames = Arrays.asList(names);
        }

        rolesOfGroup = serviceClient.getListRoleOfGroup(groupId);
        if(rolesOfGroup != null) {
            for(Role role : rolesOfGroup) {
                lstRolesOfGroup.add(role.getRoleName());
            }
        }
        for(int i = 0; i < lstNewRoleNames.size(); i++) {
            if(!lstRolesOfGroup.contains(lstNewRoleNames.get(i))) {
                lstRoleAddNew.add(lstNewRoleNames.get(i));
            }
        }
        for(int i = 0; i < lstRolesOfGroup.size(); i++) {
            if(!lstNewRoleNames.contains(lstRolesOfGroup.get(i))) {
                lstRoleRemove.add(lstRolesOfGroup.get(i));
            }
        }

        if(lstRoleAddNew.size() > 0) {
            String[] roleAdd = new String[lstRoleAddNew.size()];

            String[] roleAddResult = serviceClient.addRolesToGroup(groupId, lstRoleAddNew.toArray(roleAdd));
            if(roleAddResult != null && roleAddResult.length > 0) {
                StringBuilder lstRoleName = new StringBuilder();
                for(String name : roleAddResult) {
                    lstRoleName.append(name).append(", ");
                }
                errorMessage = "Cannot add role " + lstRoleName + "to group";
                CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.ERROR, request);
            }
        }

        if(lstRoleRemove.size() > 0) {
            String[] roleRemove = new String[lstRoleRemove.size()];
            String[] roleRemoveResult = serviceClient.removeRolesFromGroup(groupId, lstRoleRemove.toArray(roleRemove));
            if(roleRemoveResult != null && roleRemoveResult.length > 0) {
                StringBuilder lstRoleName = new StringBuilder();
                for(String name : roleRemoveResult) {
                    lstRoleName.append(name).append(", ");
                }
                errorMessage = "Cannot remove role " + lstRoleName + "from group";
                CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.ERROR, request);
            }
        }

    } catch (Exception e) {
        errorMessage = "Exception " + " : " + e.getMessage();
        CarbonUIMessage.sendCarbonUIMessage(errorMessage, CarbonUIMessage.ERROR, request);
    }
%>
