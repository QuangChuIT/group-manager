
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Apr 16, 2014 (01:16:09 UTC)
 */

        
            package org.wso2.carbon.group.mgt;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://data.mgt.group.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "User".equals(typeName)){
                   
                            return  org.wso2.carbon.group.mgt.data.xsd.User.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://data.mgt.group.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "Role".equals(typeName)){
                   
                            return  org.wso2.carbon.group.mgt.data.xsd.Role.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://data.mgt.group.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "Group".equals(typeName)){
                   
                            return  org.wso2.carbon.group.mgt.data.xsd.Group.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    