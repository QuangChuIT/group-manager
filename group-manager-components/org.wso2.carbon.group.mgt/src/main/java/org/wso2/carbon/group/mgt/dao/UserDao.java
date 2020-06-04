package org.wso2.carbon.group.mgt.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.group.mgt.PropertyFileReader;
import org.wso2.carbon.group.mgt.data.User;
import org.wso2.carbon.group.mgt.util.DatabaseUtil;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
            return doLookup("");
        } catch (Exception e) {
            log.error("Error while accessing the database to load RPs.", e);
        }
        return null;
    }

    private User[] doLookup(String filter) throws Exception {
        User[] arrUser = null;
        String providerUrl = PropertyFileReader.readPropertyFile().getProperty("LDAP_PROVIDER_URL");
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL, providerUrl);
        properties.put(Context.SECURITY_AUTHENTICATION,"simple");
        properties.put(Context.SECURITY_PRINCIPAL,"uid=admin,ou=system");
        properties.put(Context.SECURITY_CREDENTIALS,"admin");

        try {
            String[] returnedAtts = new String[]{"uid", "sn", "mail","givenname"};
            DirContext context = new InitialDirContext(properties);
            SearchControls searchCtrls = new SearchControls();
            searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            searchCtrls.setReturningAttributes(returnedAtts);
            String finalFilter = "(&(objectClass=person)(uid=*))";
            if(filter != null && !"".equals(filter.trim())) {
                finalFilter = "(&(objectClass=person)(uid=*" + filter + "))";
            }

            NamingEnumeration<SearchResult> values = context.search("ou=Users,dc=wso2,dc=org", finalFilter, searchCtrls);
            List<User> users = new ArrayList<User>();
            while (values.hasMoreElements())
            {
                SearchResult result = (SearchResult) values.next();
                Attributes attribs = result.getAttributes();

                if (null != attribs)
                {
                    if(attribs.get("sn") != null && !("Service".equals(attribs.get("sn").get()))) {
                        User user = new User();
                        user.setFirstName(attribs.get("givenname") == null ? "" : (String)attribs.get("givenname").get());
                        user.setUserName(attribs.get("sn") == null ? "" : (String)attribs.get("sn").get());
                        user.setEmail(attribs.get("mail") == null ? "" : (String)attribs.get("mail").get());
                        users.add(user);
                    }
                }
            }
            context.close();
            if(users.size() > 0) {
                arrUser = new User[users.size()];
                arrUser = users.toArray(arrUser);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return arrUser;
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        try{
            User[] users = userDao.doLookup("");
            System.out.println("123");
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
