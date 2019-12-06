package m3.model;

import java.sql.*;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import m3.mock.Dealer;
import m3.model.filter.Filter;
import m3.model.offer.Offer;

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


    // util.date -> sql.date
    public Date JavaStartDateToSqlDate(Incentive I) {
        java.util.Date utilDate1 = new java.util.Date(I.getStartDate().getTime());
        java.sql.Date sqlStartDate = new java.sql.Date(utilDate1.getTime());
        return sqlStartDate;
    }
    public Date JavaEndDateToSqlDate(Incentive I) {
        java.util.Date utilDate2 = new java.util.Date(I.getEndDate().getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(utilDate2.getTime());
        return sqlEndDate;
    }

    //Filter/Offer to String
    public String FilterToString(Incentive I) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String filterList = mapper.writeValueAsString(I.getConditions()); //convert filter list to string
        return filterList;
    }
    public String OfferToString(Incentive I) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String Offer = mapper.writeValueAsString(I.getOffer()); //convert offer to string
        return Offer;
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

        // convert format
        String filterList = FilterToString(I);
        String offer = OfferToString(I);
        Date startDate = JavaStartDateToSqlDate(I);
        Date endDate = JavaEndDateToSqlDate(I);

        String sql = new StringBuilder().append("if not exists (select * from sysobjects where name='").append(DealerID).append("' and xtype='U')").
                append("create table ").append(DealerID).append(" (").append("IncentiveID VARCHAR(50) primary key,").append("startDate DATETIME,").append("endDate DATETIME,").
                append("Title VARCHAR,").append("Disclaimer VARCHAR,").append("DealerID VARCHAR,").append("FilterList VARCHAR,").append("Offer VARCHAR )").append("INSERT INTO").append(DealerID).append("VALUES (").
                append(I.getIncentiveID()).append(",").append(startDate).append(",").append(endDate).append(",").append(I.getTitle()).append(",").append(I.getDisclaimer()).append(",").append(DealerID).
                append(",").append(filterList).append(",").append(offer).append(")").toString();

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    //Edit a item
    /*
        
        UPDATE [DealerID]
        SET startDate = '[I.getStartDate(convert to sql format)]',
        SET endDate = '[I.getEndDate(convert to sql format)]',
        SET Title = '[I.getTitle()]',
        SET Disclaimer = '[I.getDisclaimer()]',
        SET FilterList = '[I.getFilterList(Convert to String)]',
        SET Offer = '[I.getOffer(Convert to String)]'
        WHERE IncentiveID = [I.getIncentiveID()];
        
    * */

    public void EditItem(Incentive I) throws SQLException, JsonProcessingException {
        CreateConnection(); //get connection;
        String DealerID = I.getDealer().getDealerID(); //get DealerID

        // convert format
        String filterList = FilterToString(I);
        String offer = OfferToString(I);
        Date startDate = JavaStartDateToSqlDate(I);
        Date endDate = JavaEndDateToSqlDate(I);

        String sql = new StringBuilder().append("UPDATE ").append(DealerID).
                append("SET startDate='"+startDate+"', SET endDate='"+ endDate
                        +"', SET Title='"+I.getTitle()+"', SET Disclaimer='"+I.getDisclaimer()
                        +"', SET FilterList='"+ filterList +"', SET FilterList='"+ offer +"'").
                append("WHERE IncentiveID="+I.getIncentiveID()).toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    //delete a item
    /*
        
        DELETE FROM [DealerID] WHERE IncentiveID = [I.IncentiveID];
        
    * */
    public static void DeleteItem(Incentive I) throws SQLException{
        CreateConnection();
        String DealerID = I.getDealer().getDealerID();
        String sql = new StringBuilder().append("DELETE FROM  ").append(DealerID).append(" WHERE IncentiveID = '"+I.getIncentiveID()+"';").toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    // Get list of incentives by DealerID
    /*
        SELECT * FROM [DealerID]
        
    * */
    public List<Incentive> getIncentiveByDealer(String DealerID) throws SQLException {
        List<Incentive> incentives = new ArrayList<>();
        CreateConnection();
        String sql = "SELECT * FROM " + DealerID;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(!rs.next()) {
                Incentive i = new Incentive();
                i.setIncentiveID(rs.getString("IncentiveID"));

                java.util.Date utilStartDate = rs.getDate("startDate");
                i.setStartDate(utilStartDate);
                java.util.Date utilEndDate = rs.getDate("endDate");
                i.setEndDate(utilEndDate);
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
        connection.close();
        return incentives;
    }

}

