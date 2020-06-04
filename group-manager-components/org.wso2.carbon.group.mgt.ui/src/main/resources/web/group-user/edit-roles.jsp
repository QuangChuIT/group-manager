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
            location.href = 'list-group-user.jsp';
        }

        function doUpdate(groupId) {
            // var groupName = document.getElementById("groupName").value;
            if(groupId) {
                var roleNames = [];
                $(':checkbox:checked').each(function(i){
                    roleNames[i] = $(this).val();
                });
                $.ajax({
                    type: 'POST',
                    url: 'update-role-finish-ajaxprocessor.jsp?groupId=' + groupId,
                    headers: {
                        Accept: "text/html"
                    },
                    data: 'roleNames=' + roleNames,
                    async: false,
                    success: function (responseText, status) {
                        if (status === "success") {
                            location.assign("list-group-user.jsp");
                        }
                    }
                });
            } else {
                alert("<fmt:message key="enter.group.name.empty"/>");
            }
        }

        function doSearchRole() {
            var input, filter, table, tr, td, i, txtValue;
            input = document.getElementById("inputRole");
            filter = input.value.toUpperCase();
            table = document.getElementById("role_data");
            tr = table.getElementsByTagName("tr");
            for (i = 0; i < tr.length; i++) {
                td = tr[i].getElementsByTagName("td")[1];
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

        $(document).ready(function () {
        });

    </script>

    <div id="middle">
        <h2><fmt:message key="edit.roles"/></h2>

        <div id="workArea">
            <%
                Group group = null;
                Role[] allRoles = null;
                Role[] rolesOfGroup = null;
                List<Role> lstAllRole = new ArrayList<Role>();
                List<Role> lstRoleGroup = new ArrayList<Role>();
                List<Role> lstRoleNotAdded = new ArrayList<Role>(); // = lstAllRole - lstRoleGroup
                try {
                    String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
                    ConfigurationContext configContext =
                            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
                    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
                    GroupManagerClient serviceClient = new GroupManagerClient(configContext, serverURL, cookie);


                    allRoles = serviceClient.getAllRole();
                    if(allRoles != null && allRoles.length > 0) {
                        lstAllRole = Arrays.asList(allRoles);
                    }
                    String groupId = request.getParameter("groupId");
                    group = serviceClient.getGroupsById(Integer.parseInt(groupId));
                    if(groupId != null && !groupId.trim().equals("")) {
                        rolesOfGroup = serviceClient.getListRoleOfGroup(Integer.parseInt(groupId));
                        if(rolesOfGroup != null && rolesOfGroup.length > 0) {
                            lstRoleGroup = Arrays.asList(rolesOfGroup);
                        }

                    }
                    if(lstRoleGroup.size() > 0) {
                        for (Role role : lstAllRole) {
                            boolean added = false;
                            for(Role roleInGroup : lstRoleGroup) {
                                if(roleInGroup.getRoleName().equals(role.getRoleName())) {
                                    added = true;
                                    break;
                                }
                            }
                            if (!added) {
                                lstRoleNotAdded.add(role);
                            }
                        }
                    } else {
                        lstRoleNotAdded = lstAllRole;
                    }

                } catch (Exception e) {
                    String message = "Error while loading service providers" + " : " + e.getMessage();
                }
            %>
            <table class="styledLeft" id="roleAdd">
                <thead>
                <tr>
                    <th><fmt:message key="list.group.role"/></th>
                </tr>
                </thead>
                <tr>
                    <td class="formRaw">
                        <table class="normal" id="groupInfor">
                            <tr>
                                <td><fmt:message key="group.name"/></td>
                                <td><%=group.getGroupName() %></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="select.role"/>
                                </td>
                                <td>
                                    <input type="text" id="inputRole" class="input_search" onkeyup="doSearchRole()"
                                           style="margin-left: 523px" placeholder="Search for role names..">
                                </td>
                            </tr>
                        </table>
                        <table id="dtRole" width="825px">
                            <tr>
                                <td style="border: 0px; padding-bottom: 0px">
                                    <div style="width: 820px">
                                        <table class="styledLeft" style="width:800px">
                                            <thead>
                                            <tr>
                                                <th width="10%"></th>
                                                <th width="90%">Role name</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td style="border: 0px;padding-top: 0px">
                                    <div style="width:820px; height:200px; overflow:auto;">
                                        <table id="role_data" class="styledLeft" style="width:800px">
                                            <tbody>
                                            <%
                                                if(lstRoleGroup.size() > 0) {
                                                    for(Role role : lstRoleGroup) {
                                            %>
                                            <tr>
                                                <td><input type="checkbox" value="<%=role.getRoleName() %>" checked></td>
                                                <td width="90%"><%=role.getRoleName() %></td>
                                            </tr>
                                            <%
                                                    }
                                                }
                                            %>
                                            <%
                                                if(lstRoleNotAdded.size() > 0) {
                                                    for(Role role : lstRoleNotAdded) {
                                            %>
                                            <tr>
                                                <td><input type="checkbox" value="<%=role.getRoleName() %>"></td>
                                                <td width="90%"><%=role.getRoleName() %></td>
                                            </tr>
                                            <%
                                                    }
                                                }
                                            %>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="buttonRow">
                        <input type="button" class="button" value="<fmt:message key="update.role"/>"
                               onclick="doUpdate('<%=request.getParameter("groupId") %>');"/>
                        <input type="button" class="button" value="<fmt:message key="cancel"/>"
                               onclick="doCancel();"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</fmt:bundle>
