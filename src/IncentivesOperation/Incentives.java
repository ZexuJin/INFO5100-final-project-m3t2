package IncentivesOperation;

public class Incentives {
    private static String IncentiveID;
    private static String startDate;
    private static String endDate;
    private static String Title;
    private static String Disclaimer;
    private static String DealerID;
    private static float offer;
    private static String condition;

    public Incentives(String IncentiveID, String startDate, String endDate, String Title,
                      String Disclaimer, String DealerID, float offer, String condition){
        this.IncentiveID = IncentiveID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.Title = Title;
        this.Disclaimer =Disclaimer;
        this.DealerID = DealerID;
        this.offer = offer;
        this.condition = condition;
    }


    public void setIncentive(String IncentiveID){
        this.IncentiveID = IncentiveID;
    }

    public static String getIncentiveID(){
        return IncentiveID;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }


}


