<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.wso2.carbon.group.mgt.ui.GroupManagerClient" %>
<%@ page import="org.wso2.carbon.group.mgt.data.xsd.Group" %>
<%@ page import="org.wso2.carbon.group.mgt.data.xsd.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="org.wso2.carbon.group.mgt.data.xsd.Role" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" prefix="carbon" %>
<script type="text/javascript" src="../admin/js/main.js"></script>

<fmt:bundle basename="org.wso2.carbon.group.mgt.ui.i18n.Resources">

    <carbon:breadcrumb label="system.user.store"
                       resourceBundle="org.wso2.carbon.group.mgt.ui.i18n.Resources"
                       topPage="true" request="<%=request%>"/>
    <script type="text/javascript">

        function doCancel() {
            location.href = 'list-group-role.jsp';
        }

        function doSearchUser() {
            var input, filter, table, tr, td, i, txtValue;
            input = document.getElementById("inputUser");
            filter = input.value.toUpperCase();
            table = document.getElementById("user_data");
            tr = table.getElementsByTagName("tr");
            for (i = 0; i < tr.length; i++) {
                td = tr[i].getElementsByTagName("td")[0];
                if (td) {
                    txtValue = td.textContent || td.innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        tr[i].style.display = "";
                    } else {
                        tr[i].style.display = "none";
                    }
                }
            }
        }

        function doSearchRole() {
            var input, filter, table, tr, td, i, txtValue;
            input = document.getElementById("inputRole");
            filter = input.value.toUpperCase();
            table = document.getElementById("role_data");
            tr = table.getElementsByTagName("tr");
            for (i = 0; i < tr.length; i++) {
                td = tr[i].getElementsByTagName("td")[0];
                if (td) {
                    txtValue = td.textContent || td.innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        tr[i].style.display = "";
                    } else {
                        tr[i].style.display = "none";
                    }
                }
            }
        }

    </script>

    <div id="middle">
        <h2><fmt:message key="group.menu"/></h2>

        <div id="workArea">
            <%
                User[] usersOfGroup = null;
                Role[] rolesOfGroup = null;
                Group group = null;
                try {
                    String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
                    ConfigurationContext configContext =
                            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
                    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
                    GroupManagerClient serviceClient = new GroupManagerClient(configContext, serverURL, cookie);

                    String groupId = request.getParameter("groupId");
                    if(groupId != null && !groupId.trim().equals("")) {
                        group = serviceClient.getGroupsById(Integer.parseInt(groupId));
                        usersOfGroup = serviceClient.getListUserOfGroup(Integer.parseInt(groupId));
                        rolesOfGroup = serviceClient.getListRoleOfGroup(Integer.parseInt(groupId));

                    }
                } catch (Exception e) {
                    String message = "Error while loading service providers" + " : " + e.getMessage();
                }
            %>
            <table class="styledLeft">
                <thead>
                <tr>
                    <th><fmt:message key="group.infor"/></th>
                </tr>
                </thead>
                <tr>
                    <td class="formRaw">
                        <table class="normal">
                            <tr>
                                <td><fmt:message key="group.name"/>:</td>
                                <td><%=group.getGroupName() %></td>
                            </tr>
                        </table>
                        <hr>
                        <table id="dtUser" width="825px">
                            <tr>
                                <td style="border: 0px">
                                    <input type="text" id="inputUser" class="input_search" onkeyup="doSearchUser()"
                                           style="margin-left: 620px" placeholder="Search for user names..">
                                </td>
                            </tr>
                            <tr>
                                <td style="border: 0px; padding-bottom: 0px">
                                    <div style="width: 820px">
                                        <table class="styledLeft" style="width:800px">
                                            <thead>
                                            <tr>
                                                <th width="40%">User name</th>
                                                <th width="60%">Email</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td style="border: 0px; padding-top: 0px">
                                    <div style="width:820px; height:160px; overflow:auto;">
                                        <table id="user_data" class="styledLeft" style="width:800px">
                                            <tbody>
                                            <%
                                                if(usersOfGroup != null && usersOfGroup.length > 0) {
                                                    for(User user : usersOfGroup) {
                                            %>
                                            <tr>
                                                <td width="40%"><%=user.getUserName() %></td>
                                                <td width="60%"><%=user.getEmail() %></td>
                                            </tr>
                                            </tbody>
                                            <%
                                                    }
                                                }
                                            %>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <hr>
                        <br>
                        <table id="dtRole" width="825px">
                            <tr>
                                <td style="border: 0px">
                                    <input type="text" id="inputRole" class="input_search" onkeyup="doSearchRole()"
                                           style="margin-left: 620px" placeholder="Search for role names..">
                                </td>
                            </tr>
                            <tr>
                                <td style="border: 0px; padding-bottom: 0px">
                                    <div style="width: 820px">
                                        <table class="styledLeft" style="width:800px">
                                            <thead>
                                            <tr>
                                                <th width="100%">Role name</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td style="border: 0px;padding-top: 0px">
                                    <div style="width:820px; height:160px; overflow:auto;">
                                        <table id="role_data" class="styledLeft" style="width:800px">
                                            <tbody>
                                            <%
                                                if(rolesOfGroup != null && rolesOfGroup.length > 0) {
                                                    for(Role role : rolesOfGroup) {
                                            %>
                                            <tr>
                                                <td width="100%"><%=role.getRoleName() %></td>
                                            </tr>
                                            </tbody>
                                            <%
                                                    }
                                                }
                                            %>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                <tr>
                    <td class="buttonRow">
                        <input type="button" class="button" value="<fmt:message key="back"/>"
                               onclick="doCancel();"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</fmt:bundle>
