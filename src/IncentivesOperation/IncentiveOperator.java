package IncentivesOperation;

import java.sql.SQLException;

public class IncentiveOperator {

    /*The Create function is used to create an incentive item in the table*/
    public void Create(String IncentiveID, String startDate, String endDate, String Title,
                       String Disclaimer, String DealerID, float offer, String condition) throws SQLException {
        TableOperations.CreateItem(IncentiveID, startDate, endDate, Title, Disclaimer, DealerID, offer, condition);
    }

    /*The modify function is applied to modify one or more attributes in the table.*/
    public void Modify(){

    }

    /*The delete function is applied to delete an item from the table.*/
    public void Delete(){

    }

}
