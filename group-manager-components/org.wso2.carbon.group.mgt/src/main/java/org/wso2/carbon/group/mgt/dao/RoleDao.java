package org.wso2.carbon.group.mgt.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.group.mgt.PropertyFileReader;
import org.wso2.carbon.group.mgt.data.Role;
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
            return doLookup("");
        } catch (Exception e) {
            log.error("Error while accessing the database to load RPs.", e);
        }
        return null;
    }

    private Role[] doLookup(String filter) throws Exception {
        Role[] arrRole = null;
        String providerUrl = PropertyFileReader.readPropertyFile().getProperty("LDAP_PROVIDER_URL");
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL, providerUrl);
        properties.put(Context.SECURITY_AUTHENTICATION,"simple");
        properties.put(Context.SECURITY_PRINCIPAL,"uid=admin,ou=system");
        properties.put(Context.SECURITY_CREDENTIALS,"admin");

        try {
            String[] returnedAtts = new String[]{"cn"};
            DirContext context = new InitialDirContext(properties);
            SearchControls searchCtrls = new SearchControls();
            searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            searchCtrls.setReturningAttributes(returnedAtts);
            String finalFilter = "(&(objectClass=groupOfNames)(cn=*))";
            if(filter != null && !"".equals(filter.trim())) {
                finalFilter = "(&(objectClass=groupOfNames)(cn=*" + filter + "))";
            }

            NamingEnumeration<SearchResult> values = context.search("ou=Groups,dc=wso2,dc=org", finalFilter, searchCtrls);
            List<Role> roles = new ArrayList<Role>();
            while (values.hasMoreElements())
            {
                SearchResult result = (SearchResult) values.next();
                Attributes attribs = result.getAttributes();
                if (null != attribs)
                {
                    if(attribs.get("cn") != null) {
                        Role role = new Role((String)attribs.get("cn").get());
                        roles.add(role);
                    }
                }
            }
            context.close();
            if(roles.size() > 0) {
                arrRole = new Role[roles.size()];
                arrRole = roles.toArray(arrRole);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return arrRole;
    }

    public static void main(String[] args) {
        RoleDao roleDao = new RoleDao();
        try{
            Role[] roles = roleDao.doLookup("");
            System.out.println("123");
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
