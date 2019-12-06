package m3.model;

import java.sql.*;
import java.util.Date;

import m3.IncentiveManagement;
import m3.mock.Dealer;
import m3.model.filter.Filter;
import m3.model.offer.Offer;

public class TableOperations {

    private static Connection connection;
    public static void CreateConnection(){
        //The connectionURL should be modified according to your own server.
        String connectionUrl = "jdbc:sqlserver://is-swang01.ischool.uw.edu:1433;databaseName=IncentiveManagementDB;user=INFO6210;password=NEUHusky!";
        try {
            // Load SQL Server JDBC driver and establish connection.
            connection = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }


    /*All the functions below need to be done with the date and the getConditions(), getOffer()*
    some functions must be added to the incentive class.*/


    //Create a new line.
    public void Create(Incentive I) throws SQLException{
        CreateConnection();
        String sql = new StringBuilder().append("if not exists (select * from sysobjects where name='").append(I.getDealerID()).append("' and xtype='U')").
                append("create table ").append(I.getDealerID()).append(" (").append("IncentiveID VARCHAR(50) primary key,").append("startDate DATETIME,").append("endDate DATETIME,").
                append("Title VARCHAR,").append("Disclaimer VARCHAR,").append("DealerID VARCHAR,").append("FilterList VARCHAR,").append("Offer VARCHAR )").append("INSERT INTO").append(I.getDealerID()).append("VALUES (").
                append(I.getIcentiveID()).append(",").append(I.getStartDate()).append(",").append(I.getEndDate()).append(",").append(I.getTitle()).append(",").append(I.getDisclaimer()).append(",").append(I.getDealerID()).
                append(",").append(I.getConditions()).append(",").append(I.getOffer()).append(")").toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    //Edit a item
    public static void EditItem(Incentive I) throws SQLException{
        //TODO
        CreateConnection();
        String sql = new StringBuilder().append("UPDATE IncentiveManagementDB.").append(I.getDealerID()).append().append("SET startDate='"+I.getStartDate()+"', SET endDate='"+
                I.getEndDate()+"', SET Title='"+I.getTitle()+"', SET Disclaimer='"+I.getDisclaimer()+"', SET DealerID='"+I.getDealerID()+"', SET offer='"+
                offer+"', SET condition='"+condition).append("WHERE IncentiveID="+IncentiveID).toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    //delete a item
    public static void DeleteItem(String IncentiveID) throws SQLException{
        //TODO
        CreateConnection();
        String sql = new StringBuilder().append("DELETE FROM IncentivesOperation.Incentives ").append("WHERE IncentiveID = '"+IncentiveID+"';").toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    public List<Incentive> getIncentiveByDealer(Dealer d) throws Exception {
        //TODO
        // search in dealer_id's column, matching dealer_id, then add this incentive to return list
    }

}

