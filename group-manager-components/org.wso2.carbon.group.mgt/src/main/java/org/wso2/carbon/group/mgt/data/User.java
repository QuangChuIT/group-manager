package org.wso2.carbon.group.mgt.data;

public class User {
    private int ID;
    private String userName;
    private String firstName;
    private String email;

    public User() {

    }

    public User(int ID, String userName, String email) {
        this.ID = ID;
        this.userName = userName;
        this.email = email;
    }

    public int getID() {
        return this.ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDisplayName() {
        return (firstName + " " + userName).trim();
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
