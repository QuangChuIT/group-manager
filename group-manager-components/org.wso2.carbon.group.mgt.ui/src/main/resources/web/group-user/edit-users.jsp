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
                var userNames = [];
                $(':checkbox:checked').each(function(i){
                    userNames[i] = $(this).val();
                });
                $.ajax({
                    type: 'POST',
                    url: 'update-user-finish-ajaxprocessor.jsp?groupId=' + groupId,
                    headers: {
                        Accept: "text/html"
                    },
                    data: 'userNames=' + userNames,
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

        function doSearchUser() {
            var input, filter, table, tr, td, i, txtValue;
            input = document.getElementById("inputUser");
            filter = input.value.toUpperCase();
            table = document.getElementById("user_data");
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
        <h2><fmt:message key="edit.users"/></h2>

        <div id="workArea">
            <%
                Group group = null;
                User[] allUsers = null;
                User[] usersOfGroup = null;
                List<User> lstAllUser = new ArrayList<User>();
                List<User> lstUserGroup = new ArrayList<User>();
                List<User> lstUserNotAdded = new ArrayList<User>(); // = lstAllUser - lstUserGroup
                try {
                    String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
                    ConfigurationContext configContext =
                            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
                    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
                    GroupManagerClient serviceClient = new GroupManagerClient(configContext, serverURL, cookie);


                    allUsers = serviceClient.getAllUser();
                    if(allUsers != null && allUsers.length > 0) {
                        lstAllUser = Arrays.asList(allUsers);
                    }
                    String groupId = request.getParameter("groupId");
                    group = serviceClient.getGroupsById(Integer.parseInt(groupId));
                    if(groupId != null && !groupId.trim().equals("")) {
                        usersOfGroup = serviceClient.getListUserOfGroup(Integer.parseInt(groupId));
                        if(usersOfGroup != null && usersOfGroup.length > 0) {
                            lstUserGroup = Arrays.asList(usersOfGroup);
                        }

                    }
                    if(lstUserGroup.size() > 0) {
                        for (User user : lstAllUser) {
                            boolean added = false;
                            for(User userInGroup : lstUserGroup) {
                                if(userInGroup.getUserName().equals(user.getUserName())) {
                                    added = true;
                                    break;
                                }
                            }
                            if (!added) {
                                lstUserNotAdded.add(user);
                            }
                        }
                    } else {
                        lstUserNotAdded = lstAllUser;
                    }

                } catch (Exception e) {
                    String message = "Error while loading service providers" + " : " + e.getMessage();
                }
            %>
            <table class="styledLeft" id="userAdd">
                <thead>
                <tr>
                    <th><fmt:message key="list.group.user"/></th>
                </tr>
                </thead>
                <tr>
                    <td class="formRaw">
                        <table class="normal" id="groupInfor">
                            <tr>
                                <td><fmt:message key="group.name"/>:</td>
                                <td><%=group.getGroupName() %></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="select.user"/>
                                </td>
                                <td>
                                    <input type="text" id="inputUser" class="input_search" onkeyup="doSearchUser()"
                                          style="margin-left: 523px" placeholder="Search for user names..">
                                </td>
                            </tr>
                        </table>
                        <table id="dtUser" width="825px">
                            <tr>
                                <td style="border: 0px;padding-bottom: 0px">
                                    <div style="width: 820px">
                                        <table class="styledLeft" style="width:800px">
                                            <thead>
                                            <tr>
                                                <th width="10%"></th>
                                                <th width="30%">User name</th>
                                                <th width="60%">Email</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td style="border: 0px;padding-top: 0px">
                                    <div style="width:820px; height:200px; overflow:auto;">
                                        <table id="user_data" class="styledLeft" style="width:800px">
                                            <tbody>
                                            <%
                                                if(lstUserGroup.size() > 0) {
                                                    for(User user : lstUserGroup) {
                                            %>
                                            <tr>
                                                <td><input type="checkbox" value="<%=user.getUserName() %>" checked></td>
                                                <td width="30%"><%=user.getUserName() %></td>
                                                <td width="60%"><%=user.getEmail() %></td>
                                            </tr>
                                            <%
                                                    }
                                                }
                                            %>
                                            <%
                                                if(lstUserNotAdded.size() > 0) {
                                                    for(User user : lstUserNotAdded) {
                                            %>
                                            <tr>
                                                <td><input type="checkbox" value="<%=user.getUserName() %>"></td>
                                                <td width="30%"><%=user.getUserName() %></td>
                                                <td width="60%"><%=user.getEmail() %></td>
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
                        <input type="button" class="button" value="<fmt:message key="update.user"/>"
                               onclick="doUpdate('<%=request.getParameter("groupId") %>');"/>
                        <input type="button" class="button" value="<fmt:message key="cancel"/>"
                               onclick="doCancel();"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</fmt:bundle>
