package org.wso2.carbon.group.mgt.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final Log log = LogFactory.getLog(DatabaseUtil.class);
    public DatabaseUtil() {
    }

    public static Connection getDBConnection() {
        try {
            return ConfigUtil.getInstance().getConnectionString();
        } catch (Exception var2) {
            log.error("Database error. Could not get DBConnection. - " + var2.getMessage(), var2);
        }
        return null;
    }

    public static void closeAllConnections(Connection dbConnection, ResultSet rs, PreparedStatement prepStmt) {
        closeResultSet(rs);
        closeStatement(prepStmt);
        closeConnection(dbConnection);
    }

    public static void closeConnection(Connection dbConnection) {
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException var2) {
                log.error("Database error. Could not close statement. Continuing with others. - " + var2.getMessage(), var2);
            }
        }

    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException var2) {
                log.error("Database error. Could not close result set  - " + var2.getMessage(), var2);
            }
        }

    }

    public static void closeStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException var2) {
                log.error("Database error. Could not close statement. Continuing with others. - " + var2.getMessage(), var2);
            }
        }

    }

    public static void rollBack(Connection dbConnection) {
        rollbackTransaction(dbConnection);
    }

    public static void rollbackTransaction(Connection dbConnection) {
        try {
            if (dbConnection != null) {
                dbConnection.rollback();
            }
        } catch (SQLException var3) {
            log.error("An error occurred while rolling back transactions. ", var3);
        }
    }

    public static void commitTransaction(Connection dbConnection) {
        try {
            if (dbConnection != null) {
                dbConnection.commit();
            }
        } catch (SQLException var3) {
            log.error("An error occurred while commit transactions. ", var3);
        }
    }
}
