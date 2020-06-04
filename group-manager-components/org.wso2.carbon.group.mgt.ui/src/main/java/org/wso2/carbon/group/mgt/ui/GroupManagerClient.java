package org.wso2.carbon.group.mgt.ui;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.wso2.carbon.group.mgt.data.xsd.Group;
import org.wso2.carbon.group.mgt.data.xsd.Role;
import org.wso2.carbon.group.mgt.data.xsd.User;
import org.wso2.carbon.group.mgt.stub.GroupManagerStub;

import java.rmi.RemoteException;
import java.util.List;

public class GroupManagerClient {
    private GroupManagerStub stub;

    public GroupManagerClient(ConfigurationContext configCtx, String backendServerURL, String cookie) throws Exception{
        String serviceURL = backendServerURL + "GroupManager";
        stub = new GroupManagerStub(configCtx, serviceURL);
        ServiceClient client = stub._getServiceClient();
        Options options = client.getOptions();
        options.setManageSession(true);
        options.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, cookie);
    }

    public Group[] getGroups() throws Exception{
        try{
            return stub.getGroups();
        }catch (RemoteException e) {
            String msg = "Cannot get the list of groups"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public Group[] getGroupsByType(String groupType) throws Exception{
        try{
            return stub.getGroupsByType(groupType);
        }catch (RemoteException e) {
            String msg = "Cannot get the list of groups"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public Group getGroupsById(int groupId) throws Exception{
        try{
            return stub.getGroupsById(groupId);
        }catch (RemoteException e) {
            String msg = "Cannot get the list of groups"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public int createGroup(Group group) throws Exception {
        try{
            return stub.createGroup(group);
        }catch (RemoteException e) {
            String msg = "Cannot create group"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public boolean updateGroup(Group group) throws Exception {
        try{
            return stub.updateGroup(group);
        }catch (RemoteException e) {
            String msg = "Cannot update the group"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public boolean deleteGroup(int groupId) throws Exception {
        try{
            return stub.deleteGroup(groupId);
        }catch (RemoteException e) {
            String msg = "Cannot delete the group"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public String[] addUsersToGroup(int groupId, String[] userNames) throws Exception {
        try{
            return stub.addUsersToGroup(groupId, userNames);
        }catch (RemoteException e) {
            String msg = "Cannot add user to group"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public String[] removeUsersFromGroup(int groupId, String[] userNames) throws Exception {
        try{
            return stub.removeUsersFromGroup(groupId, userNames);
        }catch (RemoteException e) {
            String msg = "Cannot remove user from group"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public User[] getListUserOfGroup(int groupId) throws Exception{
        try{
            return stub.getListUserOfGroup(groupId);
        }catch (RemoteException e) {
            String msg = "Cannot get list user of the group"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public String[] addRolesToGroup(int groupId, String[] roleNames) throws Exception{
        try{
            return stub.addRolesToGroup(groupId, roleNames);
        }catch (RemoteException e) {
            String msg = "Cannot add role to the group"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public String[] removeRolesFromGroup(int groupId, String[] roleNames) throws Exception{
        try{
            return stub.removeRolesFromGroup(groupId, roleNames);
        }catch (RemoteException e) {
            String msg = "Cannot remove role of the group"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public Role[] getListRoleOfGroup(int groupId) throws Exception{
        try{
            return stub.getListRoleOfGroup(groupId);
        }catch (RemoteException e) {
            String msg = "Cannot get list role of group"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public User getUserByName(String userName) throws Exception{
        try{
            return stub.getUserByName(userName);
        }catch (RemoteException e) {
            String msg = "Cannot get user by user name"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public Role getRoleByName(String roleName) throws Exception{
        try{
            return stub.getRoleByName(roleName);
        }catch (RemoteException e) {
            String msg = "Cannot get role by role name"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public User[] getAllUser() throws Exception{
        try{
            return stub.getAllUser();
        }catch (RemoteException e) {
            String msg = "Cannot get all user of system"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }

    public Role[] getAllRole() throws Exception{
        try{
            return stub.getAllRole();
        }catch (RemoteException e) {
            String msg = "Cannot get all role of system"
                    + " . Backend service may be unavailable";
            throw new Exception(msg, e);
        }
    }
}