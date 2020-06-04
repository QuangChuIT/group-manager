
/**
 * GroupManagerCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:02:54 UTC)
 */

    package org.wso2.carbon.group.mgt.stub;

    /**
     *  GroupManagerCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class GroupManagerCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public GroupManagerCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public GroupManagerCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addUsersToGroup method
            * override this method for handling normal response from addUsersToGroup operation
            */
           public void receiveResultaddUsersToGroup(
                    java.lang.String[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addUsersToGroup operation
           */
            public void receiveErroraddUsersToGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeUsersFromGroup method
            * override this method for handling normal response from removeUsersFromGroup operation
            */
           public void receiveResultremoveUsersFromGroup(
                    java.lang.String[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeUsersFromGroup operation
           */
            public void receiveErrorremoveUsersFromGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createGroup method
            * override this method for handling normal response from createGroup operation
            */
           public void receiveResultcreateGroup(
                    int result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createGroup operation
           */
            public void receiveErrorcreateGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllRole method
            * override this method for handling normal response from getAllRole operation
            */
           public void receiveResultgetAllRole(
                    org.wso2.carbon.group.mgt.data.xsd.Role[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllRole operation
           */
            public void receiveErrorgetAllRole(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGroupsByName method
            * override this method for handling normal response from getGroupsByName operation
            */
           public void receiveResultgetGroupsByName(
                    org.wso2.carbon.group.mgt.data.xsd.Group result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGroupsByName operation
           */
            public void receiveErrorgetGroupsByName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRoleByName method
            * override this method for handling normal response from getRoleByName operation
            */
           public void receiveResultgetRoleByName(
                    org.wso2.carbon.group.mgt.data.xsd.Role result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRoleByName operation
           */
            public void receiveErrorgetRoleByName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateGroup method
            * override this method for handling normal response from updateGroup operation
            */
           public void receiveResultupdateGroup(
                    boolean result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateGroup operation
           */
            public void receiveErrorupdateGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGroupsById method
            * override this method for handling normal response from getGroupsById operation
            */
           public void receiveResultgetGroupsById(
                    org.wso2.carbon.group.mgt.data.xsd.Group result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGroupsById operation
           */
            public void receiveErrorgetGroupsById(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeRolesFromGroup method
            * override this method for handling normal response from removeRolesFromGroup operation
            */
           public void receiveResultremoveRolesFromGroup(
                    java.lang.String[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeRolesFromGroup operation
           */
            public void receiveErrorremoveRolesFromGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getListRoleOfGroup method
            * override this method for handling normal response from getListRoleOfGroup operation
            */
           public void receiveResultgetListRoleOfGroup(
                    org.wso2.carbon.group.mgt.data.xsd.Role[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListRoleOfGroup operation
           */
            public void receiveErrorgetListRoleOfGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addRolesToGroup method
            * override this method for handling normal response from addRolesToGroup operation
            */
           public void receiveResultaddRolesToGroup(
                    java.lang.String[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addRolesToGroup operation
           */
            public void receiveErroraddRolesToGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGroups method
            * override this method for handling normal response from getGroups operation
            */
           public void receiveResultgetGroups(
                    org.wso2.carbon.group.mgt.data.xsd.Group[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGroups operation
           */
            public void receiveErrorgetGroups(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteGroup method
            * override this method for handling normal response from deleteGroup operation
            */
           public void receiveResultdeleteGroup(
                    boolean result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteGroup operation
           */
            public void receiveErrordeleteGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGroupsByType method
            * override this method for handling normal response from getGroupsByType operation
            */
           public void receiveResultgetGroupsByType(
                    org.wso2.carbon.group.mgt.data.xsd.Group[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGroupsByType operation
           */
            public void receiveErrorgetGroupsByType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getListUserOfGroup method
            * override this method for handling normal response from getListUserOfGroup operation
            */
           public void receiveResultgetListUserOfGroup(
                    org.wso2.carbon.group.mgt.data.xsd.User[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListUserOfGroup operation
           */
            public void receiveErrorgetListUserOfGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUserByName method
            * override this method for handling normal response from getUserByName operation
            */
           public void receiveResultgetUserByName(
                    org.wso2.carbon.group.mgt.data.xsd.User result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUserByName operation
           */
            public void receiveErrorgetUserByName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllUser method
            * override this method for handling normal response from getAllUser operation
            */
           public void receiveResultgetAllUser(
                    org.wso2.carbon.group.mgt.data.xsd.User[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllUser operation
           */
            public void receiveErrorgetAllUser(java.lang.Exception e) {
            }
                


    }
    