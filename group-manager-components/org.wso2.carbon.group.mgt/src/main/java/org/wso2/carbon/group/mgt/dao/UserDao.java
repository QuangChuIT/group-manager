package org.wso2.carbon.group.mgt.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.group.mgt.data.User;
import org.wso2.carbon.group.mgt.util.DatabaseUtil;
import org.wso2.carbon.user.mgt.UserAdmin;
import org.wso2.carbon.user.mgt.common.UserProfileClient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final Log log = LogFactory.getLog(UserDao.class);

    public UserDao() {
    }

    public User[] getListUserOfGroup(int groupId) {
        Connection connection = DatabaseUtil.getDBConnection();
        PreparedStatement prepStmt = null;
        ResultSet results = null;
        User[] rpDOs = null;
        ArrayList<String> userNames = new ArrayList();
        List<User> users = new ArrayList<User>();

        try {
            prepStmt = connection.prepareStatement("SELECT user_name FROM group_user where group_id = ? ");
            prepStmt.setInt(1, groupId);
            results = prepStmt.executeQuery();

            while(results.next()) {
                userNames.add(results.getString(1));
            }
            if(userNames != null && userNames.size() > 0) {
                for(String userName : userNames) {
                    User user = getUserByName(userName);
                    if(user != null) {
                        users.add(user);
                    }
                }
            }

            rpDOs = new User[users.size()];
            rpDOs = users.toArray(rpDOs);
        } catch (SQLException e) {
            log.error("Error while accessing the database to load RPs.", e);
        } finally {
            DatabaseUtil.closeResultSet(results);
            DatabaseUtil.closeStatement(prepStmt);
            DatabaseUtil.closeConnection(connection);
        }

        return rpDOs;
    }

    public User getUserByName(String userName) {
        try {
            User[] users = doLookup(userName);
            if(users != null && users.length > 0){
                return users[0];
            }
        } catch (Exception e) {
            log.error("Error while accessing the database to load RPs.", e);
        }
        return null;
    }

    public User[] getAllUser() {
        try {
            return doLookup("*");
        } catch (Exception e) {
            log.error("Error while accessing the database to load RPs.", e);
        }
        return null;
    }

    private User[] doLookup(String filter) throws Exception {
        User[] arrUser = null;
        List<User> users = new ArrayList<User>();
        String domainName = "PRIMARY";  // get user from main user store
        String finalFilter = domainName + "/" + filter;
        String returnAtts = "uid,sn,mail,givenName";

        try {

            UserAdmin userAdmin = new UserAdmin();
            UserProfileClient[] userProfileClients = userAdmin.exportUsers(finalFilter, -1, returnAtts);
            if(userProfileClients != null && userProfileClients.length > 0) {
                for(int i = 0; i < userProfileClients.length; i++) {
                    User user = new User();
                    user.setFirstName(userProfileClients[i].getUserPropertiesValue()[3]);
                    user.setLastName(userProfileClients[i].getUserPropertiesValue()[1]);
                    user.setUserName(userProfileClients[i].getUserPropertiesValue()[0]);
                    user.setEmail(userProfileClients[i].getUserPropertiesValue()[2]);
                    users.add(user);
                }
            }

            if(users.size() > 0) {
                arrUser = new User[users.size()];
                arrUser = users.toArray(arrUser);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return arrUser;
    }
}
