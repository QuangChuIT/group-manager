package org.wso2.carbon.group.mgt.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.group.mgt.data.Group;
import org.wso2.carbon.group.mgt.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    private static final Log log = LogFactory.getLog(GroupDao.class);

    public GroupDao() {
    }

    public Group[] getGroups() {
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        ResultSet results = null;
        Group[] rpDOs = null;
        ArrayList rpdos = new ArrayList();

        try {
            prepStmt = connection.prepareStatement("SELECT id, group_name, group_type, created_user, created_time FROM um_group ");
            results = prepStmt.executeQuery();

            while(results.next()) {
                Group rpdo = new Group();
                rpdo.setID(results.getInt(1));
                rpdo.setGroupName(results.getString(2));
                rpdo.setGroupType(results.getString(3));
                rpdo.setCreatedUser(results.getString(4));
                rpdo.setCreatedTime(results.getTimestamp(5).getTime());
                rpdos.add(rpdo);
            }

            rpDOs = new Group[rpdos.size()];
            rpDOs = (Group[])rpdos.toArray(rpDOs);
        } catch (SQLException e) {
            log.error("Error while accessing the database to load RPs.", e);
        } finally {
            DatabaseUtil.closeResultSet(results);
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }

        return rpDOs;
    }

    public Group[] getGroupsByType(String groupType) {
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        ResultSet results = null;
        Group[] rpDOs = null;
        ArrayList rpdos = new ArrayList();

        try {
            prepStmt = connection.prepareStatement("SELECT id, group_name, group_type, created_user, created_time FROM um_group WHERE group_type=?");
            prepStmt.setString(1, groupType);
            results = prepStmt.executeQuery();

            while(results.next()) {
                Group rpdo = new Group();
                rpdo.setID(results.getInt(1));
                rpdo.setGroupName(results.getString(2));
                rpdo.setGroupType(results.getString(3));
                rpdo.setCreatedUser(results.getString(4));
                rpdo.setCreatedTime(results.getTimestamp(5).getTime());
                rpdos.add(rpdo);
            }

            rpDOs = new Group[rpdos.size()];
            rpDOs = (Group[])rpdos.toArray(rpDOs);
        } catch (SQLException e) {
            log.error("Error while accessing the database to load RPs.", e);
        } finally {
            DatabaseUtil.closeResultSet(results);
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }

        return rpDOs;
    }

    public Group getGroupsByName(String groupName) {
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        ResultSet results = null;
        Group rpdo = null;
        try {
            prepStmt = connection.prepareStatement("SELECT id, group_name, group_type, created_user, created_time FROM um_group WHERE group_name=?");
            prepStmt.setString(1, groupName);
            results = prepStmt.executeQuery();

            while(results.next()) {
                rpdo = new Group();
                rpdo.setID(results.getInt(1));
                rpdo.setGroupName(results.getString(2));
                rpdo.setGroupType(results.getString(3));
                rpdo.setCreatedUser(results.getString(4));
                rpdo.setCreatedTime(results.getTimestamp(5).getTime());
                break;
            }
        } catch (SQLException e) {
            log.error("Error while accessing the database to load RPs.", e);
        } finally {
            DatabaseUtil.closeResultSet(results);
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }
        return rpdo;
    }

    public Group getGroupsById(int groupId) {
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        ResultSet results = null;
        Group rpdo = null;
        try {
            prepStmt = connection.prepareStatement("SELECT id, group_name, group_type, created_user, created_time FROM um_group WHERE id=?");
            prepStmt.setInt(1, groupId);
            results = prepStmt.executeQuery();

            while(results.next()) {
                rpdo = new Group();
                rpdo.setID(results.getInt(1));
                rpdo.setGroupName(results.getString(2));
                rpdo.setGroupType(results.getString(3));
                rpdo.setCreatedUser(results.getString(4));
                rpdo.setCreatedTime(results.getTimestamp(5).getTime());
                break;
            }
        } catch (SQLException e) {
            log.error("Error while accessing the database to load RPs.", e);
        } finally {
            DatabaseUtil.closeResultSet(results);
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }
        return rpdo;
    }

    public int createGroup(Group group) {
        if(checkGroupExists(group)) {
            return 0;
        }
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement("INSERT INTO um_group (group_name, group_type, created_user, created_time) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, group.getGroupName());
            prepStmt.setString(2, group.getGroupType());
            prepStmt.setString(3, group.getCreatedUser());
            prepStmt.setTimestamp(4, new Timestamp(group.getCreatedTime()));
            prepStmt.execute();
            ResultSet rs = prepStmt.getGeneratedKeys();
            if(rs.next()) {
                int last_inserted_id = rs.getInt(1);
                DatabaseUtil.commitTransaction(connection);
                return last_inserted_id;
            }
        } catch(SQLException e){
            DatabaseUtil.rollbackTransaction(connection);
            log.error("Failed to store group: " + group.getGroupName() + " Error while accessing the database", e);
        } finally{
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }
        return -1;
    }

    public boolean updateGroup(Group group) {
        boolean result = true;
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement("UPDATE um_group SET group_name = ?, group_type = ?, created_user = ?, created_time = ? WHERE id = ?");
            prepStmt.setString(1, group.getGroupName());
            prepStmt.setString(2, group.getGroupType());
            prepStmt.setString(3, group.getCreatedUser());
            prepStmt.setTimestamp(4, new Timestamp(group.getCreatedTime()));
            prepStmt.setInt(5, group.getID());
            prepStmt.execute();
            DatabaseUtil.commitTransaction(connection);
        } catch(SQLException e){
            DatabaseUtil.rollbackTransaction(connection);
            result= false;
            log.error("Failed to store group: " + group.getGroupName() + " Error while accessing the database", e);
        } finally{
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }
        return result;
    }

    public boolean deleteGroup(int groupId) {
        boolean result = true;
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;

        try {
            if (this.isGroupExist(connection, groupId)) {
                // delete group user
                prepStmt = connection.prepareStatement("DELETE FROM group_user WHERE group_id = ?");
                prepStmt.setInt(1, groupId);
                prepStmt.execute();

                // delete group role
                prepStmt = connection.prepareStatement("DELETE FROM group_role WHERE group_id = ?");
                prepStmt.setInt(1, groupId);
                prepStmt.execute();

                // delete group
                prepStmt = connection.prepareStatement("DELETE FROM um_group WHERE id = ?");
                prepStmt.setInt(1, groupId);
                prepStmt.execute();
                DatabaseUtil.commitTransaction(connection);
            }
        } catch (SQLException e) {
            DatabaseUtil.rollbackTransaction(connection);
            result = false;
            log.error("Failed to remove group", e);
        } finally {
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }
        return result;
    }

    public String[] addUsersToGroup(int groupId, String[] userNames) {
        List<String> result = new ArrayList<String>();
        String[] rpDOs = null;
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        if(userNames != null && userNames.length > 0) {
            try {
                if (this.isGroupExist(connection, groupId)) {
                    for (String userName : userNames) {
                        if(!addUser(groupId, userName)){
                            result.add(userName);
                        }
                    }
                }
                rpDOs = new String[result.size()];
                rpDOs = result.toArray(rpDOs);
            } catch (SQLException e) {
                DatabaseUtil.rollbackTransaction(connection);
                log.error("Failed to remove group", e);
            } finally {
                DatabaseUtil.closeStatement(prepStmt);
                DatabaseUtil.closeConnection(connection);
            }
        }
        return rpDOs;
    }

    public String[] removeUsersFromGroup(int groupId, String[] userNames) {
        List<String> result = new ArrayList<String>();
        String[] rpDOs = null;
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        if(userNames != null && userNames.length > 0) {
            try {
                if (this.isGroupExist(connection, groupId)) {
                    for (String userName : userNames) {
                        if(!removeUser(groupId, userName)){
                            result.add(userName);
                        }
                    }
                }
                rpDOs = new String[result.size()];
                rpDOs = result.toArray(rpDOs);
            } catch (SQLException e) {
                DatabaseUtil.rollbackTransaction(connection);
                log.error("Failed to remove group", e);
            } finally {
                DatabaseUtil.closeStatement(prepStmt);
                DatabaseUtil.closeConnection(connection);
            }
        }
        return rpDOs;
    }

    public String[] addRolesToGroup(int groupId, String[] roleNames) {
        List<String> result = new ArrayList<String>();
        String[] rpDOs = null;
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        if(roleNames != null && roleNames.length > 0) {
            try {
                if (this.isGroupExist(connection, groupId)) {
                    for (String roleName : roleNames) {
                        if(!addRole(groupId, roleName)){
                            result.add(roleName);
                        }
                    }
                }
                rpDOs = new String[result.size()];
                rpDOs = result.toArray(rpDOs);
            } catch (SQLException e) {
                DatabaseUtil.rollbackTransaction(connection);
                log.error("Failed to remove group", e);
            } finally {
                DatabaseUtil.closeStatement(prepStmt);
                DatabaseUtil.closeConnection(connection);
            }
        }
        return rpDOs;
    }

    public String[] removeRolesFromGroup(int groupId, String[] roleNames) {
        List<String> result = new ArrayList<String>();
        String[] rpDOs = null;
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        if(roleNames != null && roleNames.length > 0) {
            try {
                if (this.isGroupExist(connection, groupId)) {
                    for (String roleName : roleNames) {
                        if(!removeRole(groupId, roleName)){
                            result.add(roleName);
                        }
                    }
                }
                rpDOs = new String[result.size()];
                rpDOs = result.toArray(rpDOs);
            } catch (SQLException e) {
                DatabaseUtil.rollbackTransaction(connection);
                log.error("Failed to remove group", e);
            } finally {
                DatabaseUtil.closeStatement(prepStmt);
                DatabaseUtil.closeConnection(connection);
            }
        }
        return rpDOs;
    }

    private boolean isGroupExist(Connection connection, int groupId) throws SQLException {
        PreparedStatement prepStmt = null;
        ResultSet results = null;
        boolean result = false;

        try {
            prepStmt = connection.prepareStatement("SELECT * FROM um_group WHERE id = ?");
            prepStmt.setInt(1, groupId);
            results = prepStmt.executeQuery();
            if (results != null && results.next()) {
                result = true;
            }
        } finally {
            DatabaseUtil.closeResultSet(results);
            DatabaseUtil.closeStatement(prepStmt);
        }

        return result;
    }

    private boolean addUser(int groupId, String userName) throws SQLException {
        boolean result = true;
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement("INSERT INTO group_user (group_id, user_name) VALUES (?,?)");
            prepStmt.setInt(1, groupId);
            prepStmt.setString(2, userName);
            prepStmt.execute();
            DatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            result= false;
            DatabaseUtil.rollbackTransaction(connection);
            log.error("Failed to add user", e);
        } finally {
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }
        return result;
    }

    private boolean removeUser(int groupId, String userName) throws SQLException {
        boolean result = true;
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement("DELETE FROM group_user WHERE group_id = ? AND user_name = ?");
            prepStmt.setInt(1, groupId);
            prepStmt.setString(2, userName);
            prepStmt.execute();
            DatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            result= false;
            DatabaseUtil.rollbackTransaction(connection);
            log.error("Failed to remove user", e);
        } finally {
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }
        return result;
    }

    private boolean addRole(int groupId, String roleName) throws SQLException {
        boolean result = true;
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement("INSERT INTO group_role (group_id, role_name) VALUES (?,?)");
            prepStmt.setInt(1, groupId);
            prepStmt.setString(2, roleName);
            prepStmt.execute();
            DatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            result= false;
            DatabaseUtil.rollbackTransaction(connection);
            log.error("Failed to add user", e);
        } finally {
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }
        return result;
    }

    private boolean removeRole(int groupId, String roleName) throws SQLException {
        boolean result = true;
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement("DELETE FROM group_role WHERE group_id = ? AND role_name = ?");
            prepStmt.setInt(1, groupId);
            prepStmt.setString(2, roleName);
            prepStmt.execute();
            DatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            result= false;
            DatabaseUtil.rollbackTransaction(connection);
            log.error("Failed to remove user", e);
        } finally {
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }
        return result;
    }

    private boolean checkGroupExists(Group group) {
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        ResultSet results = null;
        Group rpdo = null;
        try {
            prepStmt = connection.prepareStatement("SELECT id, group_name, group_type, created_user, created_time FROM um_group WHERE group_name=? AND group_type = ?");
            prepStmt.setString(1, group.getGroupName());
            prepStmt.setString(2, group.getGroupType());
            results = prepStmt.executeQuery();

            if(results.next()) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Error while accessing the database to load RPs.", e);
        } finally {
            DatabaseUtil.closeResultSet(results);
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }
        return false;
    }
}
