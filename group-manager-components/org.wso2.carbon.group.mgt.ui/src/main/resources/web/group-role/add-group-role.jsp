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
            location.href = '../group-mg/add-group.jsp';
        }

        function doFinish() {
            var groupName = document.getElementById("groupName").value;
            if(groupName) {
                var roleNames = [];
                $(':checkbox:checked').each(function(i){
                    roleNames[i] = $(this).val();
                });
                $.ajax({
                    type: 'POST',
                    url: 'add-finish-ajaxprocessor.jsp?groupName=' + groupName,
                    headers: {
                        Accept: "text/html"
                    },
                    data: 'roleNames=' + roleNames,
                    async: false,
                    success: function (responseText) {
                        location.assign("list-group-role.jsp?ordinal=1");
                    }
                });
            } else {
                alert("<fmt:message key="enter.group.name.empty"/>");
            }
        }

        function doSearch() {
            var input, filter, table, tr, td, i, txtValue;
            input = document.getElementById("myInput");
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
        <h2><fmt:message key="add.new.group.role"/></h2>

        <div id="workArea">
            <%
                Role[] roles = null;
                try {
                    String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
                    ConfigurationContext configContext =
                            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
                    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
                    GroupManagerClient serviceClient = new GroupManagerClient(configContext, serverURL, cookie);
                    roles = serviceClient.getAllRole();
                } catch (Exception e) {
                    String message = "Error while loading service providers" + " : " + e.getMessage();
                }
            %>
            <form method="post" action="add-finish-ajaxprocessor.jsp" name="dataForm">
                <table class="styledLeft" id="mainTable">
                    <thead>
                    <tr>
                        <th><fmt:message key="enter.group.infor"/></th>
                    </tr>
                    </thead>
                    <tr>
                        <td class="formRaw">
                            <table class="normal" id="groupInfor">
                                <tr>
                                    <td><fmt:message key="group.name"/><span style="color: red; ">*</span></td>
                                    <td><input type="text" id="groupName" style="width:150px"/></td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="select.role"/>
                                    </td>
                                    <td>
                                        <input type="text" id="myInput" class="input_search" onkeyup="doSearch()"
                                               placeholder="Search for role names..">
                                    </td>
                                </tr>
                            </table>
                            <table id="dtRole" width="825px">
                                <tr>
                                    <td style="border: 0px;padding-bottom: 0px">
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
                                    <td style="border: 0px; padding-top: 0px">
                                        <div style="width:820px; height:200px; overflow:auto;">
                                            <table id="role_data" class="styledLeft" style="width:800px">
                                                <tbody>
                                                <%
                                                    if(roles != null && roles.length > 0) {
                                                        for(Role role : roles) {
                                                %>
                                                <tr>
                                                    <td width="10%"><input type="checkbox" value="<%=role.getRoleName() %>"></td>
                                                    <td width="90%"><%=role.getRoleName() %></td>
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
                    </tr>
                    <tr>
                        <td class="buttonRow">
                            <input type="button" class="button" value="<fmt:message key="create.group"/>"
                                   onclick="doFinish();"/>
                            <input type="button" class="button" value="<fmt:message key="cancel"/>"
                                   onclick="doCancel();"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</fmt:bundle>
