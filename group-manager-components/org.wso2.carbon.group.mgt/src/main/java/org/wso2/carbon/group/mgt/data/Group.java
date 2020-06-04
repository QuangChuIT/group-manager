package org.wso2.carbon.group.mgt.data;

import java.util.Date;

public class Group {
    private int ID;
    private String groupName;
    private String groupType;
    private long createdTime;
    private String createdUser;

    public Group() {

    }

    public Group(String groupName, String groupType, String createdUser) {
        this.groupName = groupName;
        this.groupType = groupType;
        this.createdTime = (new Date()).getTime();
        this.createdUser = createdUser;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupType() {
        return groupType;
    }
    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
    public long getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
    public String getCreatedUser() {
        return createdUser;
    }
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

}
