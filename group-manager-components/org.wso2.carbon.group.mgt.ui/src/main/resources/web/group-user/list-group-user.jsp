<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.wso2.carbon.group.mgt.ui.GroupManagerClient" %>
<%@ page import="org.wso2.carbon.group.mgt.data.xsd.Group" %>
<%@ page import="org.wso2.carbon.group.mgt.ui.GroupType" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" prefix="carbon" %>
<script type="text/javascript" src="../admin/js/main.js"></script>

<jsp:include page="../dialog/display_messages.jsp"/>
<fmt:bundle basename="org.wso2.carbon.group.mgt.ui.i18n.Resources">

    <carbon:breadcrumb label="system.user.store"
                       resourceBundle="org.wso2.carbon.group.mgt.ui.i18n.Resources"
                       topPage="true" request="<%=request%>"/>
    <script type="text/javascript">

        function deleteGroup(groupId) {
            function doDelete() {
                $.ajax({
                    type: 'POST',
                    url: 'delete-finish-ajaxprocessor.jsp',
                    headers: {
                        Accept: "text/html"
                    },
                    data: 'groupId=' + encodeURIComponent(groupId),
                    async: false,
                    success: function (responseText, status) {
                        if (status === "success") {
                            location.assign("list-group-user.jsp");
                        }
                    }
                });
            }
            CARBON.showConfirmationDialog("<fmt:message key="confirm.delete.group"/> \'" + "\'?", doDelete, null);
        }

        $(document).ready(function () {

        });

    </script>

    <div id="middle">
        <h2><fmt:message key="list.group.user"/></h2>

        <div id="workArea">
            <%
                Group[] groups = null;
                try {
                    String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
                    ConfigurationContext configContext =
                            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
                    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
                    GroupManagerClient serviceClient = new GroupManagerClient(configContext, serverURL, cookie);
                    groups = serviceClient.getGroupsByType(GroupType.GROUP_USER.name());
                } catch (Exception e) {
                    String message = "Error while loading service providers" + " : " + e.getMessage();
                }
            %>
            <table class="styledLeft" id="userTable">
                <thead>
                <tr>
                    <th class="leftCol-big"><fmt:message key="group.name"/></th>
                    <th><fmt:message key="actions"/></th>
                </tr>
                </thead>
                <tbody>
                        <%
                if (groups != null && groups.length > 0) {
                    for (int i = 0; i < groups.length; i++) {
            %>
                <tr>
                    <td><%=groups[i].getGroupName()%>
                    </td>
                    <td>
                        <a href="view-group.jsp?groupId=<%=groups[i].getID()%>"
                           class="icon-link"
                           style="background-image:url(../group-mgt/images/view.gif);"><fmt:message key="view.group"/></a>

                        <a href="edit-users.jsp?groupId=<%=groups[i].getID()%>"
                           class="icon-link"
                           style="background-image:url(../group-mgt/images/edit.gif);"><fmt:message key="edit.users"/></a>

                        <a href="edit-roles.jsp?groupId=<%=groups[i].getID()%>"
                           class="icon-link"
                           style="background-image:url(../group-mgt/images/edit.gif);"><fmt:message key="edit.roles"/></a>

                        <a href="#" onclick="deleteGroup('<%=groups[i].getID()%>')"
                           class="icon-link" style="background-image:url(../group-mgt/images/delete.gif);"><fmt:message key="delete"/></a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </div>
</fmt:bundle>
