package m3.model;

import java.sql.*;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import m3.IncentiveManagement;
import m3.mock.Dealer;
import m3.model.filter.Filter;
import m3.model.offer.Offer;
import m3.model.Incentive;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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


    //Create a new line.
    /*

    if not exists (select * from sysobjects where name='[DealerID]' and xtype='U')
    create table [I.IncentiveID]] (
        IncentiveID VARCHAR(50) primary key,
        startDate DATETIME,
        endDate DATETIME,
        Title VARCHAR,
        Disclaimer VARCHAR,
        DealerID VARCHAR,
        FilterList(Convert to String) VARCHAR,
        Offer(Convert to String) VARCHAR
        )

        INSERT INTO [DealerID] VALUES (
            [I.getIncentiveID],[I.getStartDate],[I.getEndDate],
            [I.getTitle],[I.getDisclaimer],[DealerID],
            [I.getFilterList(Convert to String)],
            [I.getOffer(Convert to String)]
        )

     */
    public void Create(Incentive I) throws SQLException, JsonProcessingException {
        CreateConnection(); //get connection;

        String DealerID = I.getDealer().getDealerID(); //get DealerID
        ObjectMapper mapper = new ObjectMapper();
        String filterList = mapper.writeValueAsString(I.getConditions()); //convert filter list to string
        String offer = mapper.writeValueAsString(I.getOffer()); //convert offer list to string


        String sql = new StringBuilder().append("if not exists (select * from sysobjects where name='").append(DealerID).append("' and xtype='U')").
                append("create table ").append(DealerID).append(" (").append("IncentiveID VARCHAR(50) primary key,").append("startDate DATETIME,").append("endDate DATETIME,").
                append("Title VARCHAR,").append("Disclaimer VARCHAR,").append("DealerID VARCHAR,").append("FilterList VARCHAR,").append("Offer VARCHAR )").append("INSERT INTO").append(DealerID).append("VALUES (").
                append(I.getIncentiveID()).append(",").append(I.getStartDate()).append(",").append(I.getEndDate()).append(",").append(I.getTitle()).append(",").append(I.getDisclaimer()).append(",").append(DealerID).
                append(",").append(filterList).append(",").append(offer).append(")").toString();

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    //Edit a item
    public static void EditItem(Incentive I) throws SQLException{
        //TODO
        CreateConnection();
        String DealerID = I.getDealer().getDealerID();
        String sql = new StringBuilder().append("UPDATE IncentiveManagementDB.").append(DealerID).append("SET startDate='"+I.getStartDate()+"', SET endDate='"+
                I.getEndDate()+"', SET Title='"+I.getTitle()+"', SET Disclaimer='"+I.getDisclaimer()+"', SET DealerID='"+I.getDealerID()+"', SET offer='"+
                I.getConditions()+"', SET condition='"+condition).append("WHERE IncentiveID="+IncentiveID).toString();
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

    // Get list of incentives by DealerID
    public List<Incentive> getIncentiveByDealer(String DealerId) {
        List<Incentive> incentives = new ArrayList<>();
        conn = DBConnection.CreateConnection();
        String sql = "SELECT * FROM " + DealerId;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(!rs.next()) {
                Incentive i = new Incentive();
                i.setStartDate(rs.getDate("startDate"));
                i.setEndDate(rs.getDate("endDate"));
                i.setTitle(rs.getString("Title"));
                i.setDisclaimer(rs.getString("Disclaimer"));
                ObjectMapper mapper = new ObjectMapper();
                Dealer d = mapper.readValue(rs.getString("dealer"), Dealer.class);
                i.setDealer(d); // Json -> Dealer类
                List<Filter> filters = mapper.readValue(rs.getString("FilterList"), new TypeReference<List<Filter>>(){});
                i.setConditions(filters); // JSON -> List<Filter>
                Offer offer = mapper.readValue(rs.getString("Offer"), Offer.class);
                i.setOffer(offer); // Json -> Offer
                incentives.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incentives;
    }

}

