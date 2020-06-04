package org.wso2.carbon.group.mgt;

import org.wso2.carbon.group.mgt.dao.GroupDao;
import org.wso2.carbon.group.mgt.dao.RoleDao;
import org.wso2.carbon.group.mgt.dao.UserDao;
import org.wso2.carbon.group.mgt.data.Group;
import org.wso2.carbon.group.mgt.data.Role;
import org.wso2.carbon.group.mgt.data.User;

import java.util.List;

public class GroupManager {
    private GroupDao groupDao;
    private RoleDao roleDao;
    private UserDao userDao;

    public GroupManager() {
        groupDao = new GroupDao();
        userDao = new UserDao();
        roleDao = new RoleDao();
    }

    public Group[] getGroups() {
        return groupDao.getGroups();
    }

    public Group[] getGroupsByType(String groupType) {
        return groupDao.getGroupsByType(groupType);
    }

    public Group getGroupsByName(String groupName) {
        return groupDao.getGroupsByName(groupName);
    }

    public Group getGroupsById(int groupId) {
        return groupDao.getGroupsById(groupId);
    }

    public int createGroup(Group group) {
        return groupDao.createGroup(group);
    }

    public boolean updateGroup(Group group) {
        return groupDao.updateGroup(group);
    }

    public boolean deleteGroup(int groupId) {
        return groupDao.deleteGroup(groupId);
    }

    public String[] addUsersToGroup(int groupId, String[] userNames) {
        return groupDao.addUsersToGroup(groupId, userNames);
    }

    public String[] removeUsersFromGroup(int groupId, String[] userNames) {
        return groupDao.removeUsersFromGroup(groupId, userNames);
    }

    public User[] getListUserOfGroup(int groupId) {
        return userDao.getListUserOfGroup(groupId);
    }

    public String[] addRolesToGroup(int groupId, String[] roleNames) {
        return groupDao.addRolesToGroup(groupId, roleNames);
    }

    public String[] removeRolesFromGroup(int groupId, String[] roleNames) {
        return groupDao.removeRolesFromGroup(groupId, roleNames);
    }

    public Role[] getListRoleOfGroup(int groupId) {
        return roleDao.getListRoleOfGroup(groupId);
    }

    public User getUserByName(String userName) {
        return userDao.getUserByName(userName);
    }

    public Role getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);
    }

    public User[] getAllUser() {
        return userDao.getAllUser();
    }

    public Role[] getAllRole() {
        return roleDao.getAllRole();
    }
}
