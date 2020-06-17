package org.wso2.carbon.group.mgt.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.group.mgt.data.Role;
import org.wso2.carbon.group.mgt.util.DatabaseUtil;
import org.wso2.carbon.user.mgt.UserAdmin;
import org.wso2.carbon.user.mgt.common.FlaggedName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDao {
    private static final Log log = LogFactory.getLog(RoleDao.class);

    public RoleDao() {
    }

    public Role[] getListRoleOfGroup(int groupId) {
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        ResultSet results = null;
        Role[] rpDOs = null;
        ArrayList<String> roleNames = new ArrayList();
        List<Role> roles = new ArrayList<Role>();

        try {
            prepStmt = connection.prepareStatement("SELECT role_name FROM group_role where group_id = ? ");
            prepStmt.setInt(1, groupId);
            results = prepStmt.executeQuery();

            while(results.next()) {
                roleNames.add(results.getString(1));
            }
            if(roleNames != null && roleNames.size() > 0) {
                for(String roleName : roleNames) {
                    Role role = getRoleByName(roleName);
                    if(role != null) {
                        roles.add(role);
                    }
                }
            }

            rpDOs = new Role[roles.size()];
            rpDOs = roles.toArray(rpDOs);
        } catch (SQLException e) {
            log.error("Error while accessing the database to load RPs.", e);
        } finally {
            DatabaseUtil.closeResultSet(results);
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }

        return rpDOs;
    }

    public Role getRoleByName(String filter) {
        try {
            Role[] roles = doLookup(filter);
            if(roles != null && roles.length > 0){
                return roles[0];
            }
        } catch (Exception e) {
            log.error("Error while accessing the database to load RPs.", e);
        }
        return null;
    }

    public Role[] getAllRole() {
        try {
            return doLookup("*");
        } catch (Exception e) {
            log.error("Error while accessing the database to load RPs.", e);
        }
        return null;
    }

    private Role[] doLookup(String filter) throws Exception {
        Role[] arrRole = null;
        List<Role> roles = new ArrayList<Role>();
        UserAdmin userAdmin = new UserAdmin();
        FlaggedName[] flaggedNames = userAdmin.getAllRolesNames(filter, -1);
        if(flaggedNames != null && flaggedNames.length > 1) {
            for(int i = 0; i < flaggedNames.length - 1; i++) {
                Role role = new Role(flaggedNames[i].getItemName());
                roles.add(role);
            }
        }
        if(roles.size() > 0) {
            arrRole = new Role[roles.size()];
            arrRole = roles.toArray(arrRole);
        }
        return arrRole;
    }
}
