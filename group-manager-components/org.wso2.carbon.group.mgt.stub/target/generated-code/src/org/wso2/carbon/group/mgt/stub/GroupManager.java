

/**
 * GroupManager.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:02:54 UTC)
 */

    package org.wso2.carbon.group.mgt.stub;

    /*
     *  GroupManager java interface
     */

    public interface GroupManager {
          

        /**
          * Auto generated method signature
          * 
                    * @param addUsersToGroup26
                
         */

         
                     public java.lang.String[] addUsersToGroup(

                        int groupId27,java.lang.String[] userNames28)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param addUsersToGroup26
            
          */
        public void startaddUsersToGroup(

            int groupId27,java.lang.String[] userNames28,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param removeUsersFromGroup31
                
         */

         
                     public java.lang.String[] removeUsersFromGroup(

                        int groupId32,java.lang.String[] userNames33)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param removeUsersFromGroup31
            
          */
        public void startremoveUsersFromGroup(

            int groupId32,java.lang.String[] userNames33,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param createGroup36
                
         */

         
                     public int createGroup(

                        org.wso2.carbon.group.mgt.data.xsd.Group group37)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param createGroup36
            
          */
        public void startcreateGroup(

            org.wso2.carbon.group.mgt.data.xsd.Group group37,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getAllRole40
                
         */

         
                     public org.wso2.carbon.group.mgt.data.xsd.Role[] getAllRole(

                        )
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getAllRole40
            
          */
        public void startgetAllRole(

            

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getGroupsByName43
                
         */

         
                     public org.wso2.carbon.group.mgt.data.xsd.Group getGroupsByName(

                        java.lang.String groupName44)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getGroupsByName43
            
          */
        public void startgetGroupsByName(

            java.lang.String groupName44,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getRoleByName47
                
         */

         
                     public org.wso2.carbon.group.mgt.data.xsd.Role getRoleByName(

                        java.lang.String roleName48)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getRoleByName47
            
          */
        public void startgetRoleByName(

            java.lang.String roleName48,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param updateGroup51
                
         */

         
                     public boolean updateGroup(

                        org.wso2.carbon.group.mgt.data.xsd.Group group52)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param updateGroup51
            
          */
        public void startupdateGroup(

            org.wso2.carbon.group.mgt.data.xsd.Group group52,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getGroupsById55
                
         */

         
                     public org.wso2.carbon.group.mgt.data.xsd.Group getGroupsById(

                        int groupId56)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getGroupsById55
            
          */
        public void startgetGroupsById(

            int groupId56,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param removeRolesFromGroup59
                
         */

         
                     public java.lang.String[] removeRolesFromGroup(

                        int groupId60,java.lang.String[] roleNames61)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param removeRolesFromGroup59
            
          */
        public void startremoveRolesFromGroup(

            int groupId60,java.lang.String[] roleNames61,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getListRoleOfGroup64
                
         */

         
                     public org.wso2.carbon.group.mgt.data.xsd.Role[] getListRoleOfGroup(

                        int groupId65)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getListRoleOfGroup64
            
          */
        public void startgetListRoleOfGroup(

            int groupId65,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param addRolesToGroup68
                
         */

         
                     public java.lang.String[] addRolesToGroup(

                        int groupId69,java.lang.String[] roleNames70)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param addRolesToGroup68
            
          */
        public void startaddRolesToGroup(

            int groupId69,java.lang.String[] roleNames70,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getGroups73
                
         */

         
                     public org.wso2.carbon.group.mgt.data.xsd.Group[] getGroups(

                        )
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getGroups73
            
          */
        public void startgetGroups(

            

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param deleteGroup76
                
         */

         
                     public boolean deleteGroup(

                        int groupId77)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param deleteGroup76
            
          */
        public void startdeleteGroup(

            int groupId77,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getGroupsByType80
                
         */

         
                     public org.wso2.carbon.group.mgt.data.xsd.Group[] getGroupsByType(

                        java.lang.String groupType81)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getGroupsByType80
            
          */
        public void startgetGroupsByType(

            java.lang.String groupType81,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getListUserOfGroup84
                
         */

         
                     public org.wso2.carbon.group.mgt.data.xsd.User[] getListUserOfGroup(

                        int groupId85)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getListUserOfGroup84
            
          */
        public void startgetListUserOfGroup(

            int groupId85,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getUserByName88
                
         */

         
                     public org.wso2.carbon.group.mgt.data.xsd.User getUserByName(

                        java.lang.String userName89)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getUserByName88
            
          */
        public void startgetUserByName(

            java.lang.String userName89,

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getAllUser92
                
         */

         
                     public org.wso2.carbon.group.mgt.data.xsd.User[] getAllUser(

                        )
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getAllUser92
            
          */
        public void startgetAllUser(

            

            final org.wso2.carbon.group.mgt.stub.GroupManagerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    