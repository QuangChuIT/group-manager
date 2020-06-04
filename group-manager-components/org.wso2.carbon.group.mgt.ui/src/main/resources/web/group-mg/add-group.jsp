<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.wso2.carbon.group.mgt.ui.GroupManagerClient" %>
<%@ page import="org.wso2.carbon.group.mgt.data.xsd.Group" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" prefix="carbon" %>
<script type="text/javascript" src="../admin/js/main.js"></script>

<fmt:bundle basename="org.wso2.carbon.group.mgt.ui.i18n.Resources">

    <carbon:breadcrumb label="system.user.store"
                       resourceBundle="org.wso2.carbon.group.mgt.ui.i18n.Resources"
                       topPage="true" request="<%=request%>"/>


    <script type="text/javascript">
    </script>

    <div id="middle">
        <h2><fmt:message key="add.group.management"/></h2>

        <div id="workArea">
            <table width="100%">
                <tr>
                    <td>
                        <table class="styledLeft" id="internal" width="100%">
                            <tr>
                                <td>
                                    <a class="icon-link"
                                       style="background-image:url(../group-mgt/images/group_user.png);"
                                       href="../group-user/add-group-user.jsp"><fmt:message key="add-group-user"/></a>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <a class="icon-link"
                                       style="background-image:url(../group-mgt/images/group-roles.gif);"
                                       href="../group-role/add-group-role.jsp"><fmt:message key="add-group-role"/></a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>

        </div>
    </div>
    <script type="text/javascript">
        alternateTableRows('internal', 'tableEvenRow', 'tableOddRow');
        alternateTableRows('external', 'tableEvenRow', 'tableOddRow');
    </script>
</fmt:bundle>
