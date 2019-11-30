package IncentivesOperation;

import java.sql.*;
import java.util.concurrent.Callable;

public class TableOperations {

    private static Connection connection;
    public static void CreateConnection(){

        //The connectionURL should be modified according to your own server.
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=master;user=sa;password=Docker_sql_jzx123";
        try {
            // Load SQL Server JDBC driver and establish connection.
            connection = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }

    //Create a table
    public static void CreateTable() throws SQLException {
        //TODO
        CreateConnection();
        String sql = new StringBuilder().append("USE IncentivesOperation.Incentives; ").append("CREATE TABLE IncentivesDemo (")
                .append("IncentiveID VARCHAR(50) primary key,").append("startDate datetime,")
                .append("endDate datetime,").append("Title VARCHAR,").append("Disclaimer VARCHAR,")
                .append(" DealerID VARCHAR(50),").append("Offer float,").append("Condition VARCHAR,").toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    //Create a new line.
    public static void CreateItem(String IncentiveID, String startDate, String endDate, String Title,
                                  String Disclaimer, String DealerID, float offer, String condition) throws SQLException{
        //TODO
        CreateConnection();
        String sql = new StringBuilder().append("INSERT INTO Incentives ").append("VALUES ("+IncentiveID+","+startDate+","+endDate
                +","+Title+","+Disclaimer+","+DealerID+","+offer+","+condition+");").toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    //Edit a item
    public void EditItem(String IncentiveID, String startDate, String endDate, String Title,
                         String Disclaimer, String DealerID, float offer, String condition) throws SQLException{
        //TODO
        CreateConnection();
        String sql = new StringBuilder().append("UPDATE IncentivesOperation.Incentives ").append("SET startDate='"+startDate+"', SET endDate='"+
                endDate+"', SET Title='"+Title+"', SET Disclaimer='"+Disclaimer+"', SET DealerID='"+DealerID+"', SET offer='"+
                offer+"', SET condition='"+condition).append("WHERE IncentiveID="+IncentiveID).toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    //delete a item
    public void DeleteItem(String IncentiveID) throws SQLException{
        //TODO
        CreateConnection();
        String sql = new StringBuilder().append("DELETE FROM IncentivesOperation.Incentives ").append("WHERE IncentiveID = '"+IncentiveID+"';").toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }
}

