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
        group.setGroupType(GroupType.GROUP_ROLE.name());
        int groupId = serviceClient.createGroup(group);
        if(groupId != 0) {
            String roleNames = request.getParameter("roleNames");
            String[] listRole = null;
            if(roleNames != null && !roleNames.trim().equals("")) {
                listRole = roleNames.split(",");
                String[] role1 = serviceClient.addRolesToGroup(groupId, listRole);
                if(role1 != null && role1.length > 0) {
                    String lstRoleName = "";
                    for(String name : role1) {
                        lstRoleName += name + ", ";
                    }
                    JOptionPane.showMessageDialog(null, "Cannot add role " + lstRoleName + "to group ");
                }
            }
        } else {
            if(groupId == 0) {
                JOptionPane.showMessageDialog(null, "Group with name " + group.getGroupName() + " is exists");
            } else {
                JOptionPane.showMessageDialog(null, "Cannot create group with name " + group.getGroupName());
            }
        }

        //users = serviceClient.getUsers();
    } catch (Exception e) {
        String message = "Error " + " : " + e.getMessage();
        JOptionPane.showMessageDialog(null, message);
    }
%>