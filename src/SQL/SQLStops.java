package SQL;

import Objects.DBInfo;
import Objects.Stop;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SQLStops extends SQLConnection {
 
 

    public SQLStops(DBInfo dbInfo){
        super(dbInfo);
    } 
    /*
    Prints entire database.
    dbo - database to print
    */
    public void print(ArrayList<Stop> data){
        for(int i = 0 ; i < data.size() ; i++){
            data.get(i).print();
        }
    } 
    /*
    Returns Entire Database
    */
    public ArrayList<Stop> getDataBase(String  dbo) {
        ArrayList<Stop> data=new ArrayList<>();
        try{
            if(!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            rs       = selector.executeQuery("SELECT * FROM "+dbo+" ORDER BY "+"StartIdle");
            addToArrayList(rs,data);
        }catch(Exception queryException)
        {
            System.out.println("SQLStop.java - getDataBase() Exception - " + queryException.getMessage());
        }
        return data;
    }
    /*
     Returns data from dbo that is within the date range
     dbo            - database to fetch from
     dateColumnName - name of column that that has the date
     dateStart      - start date to fetch from
     dateEnd        - end date to stop fetching
     */
    public ArrayList<Stop> getDataByDateAndHours(String dbo,String dateStart, String dateEnd,String hourStart,String hourEnd)
    {
        ArrayList<Stop> dataByDate = new ArrayList<>();
        try{
            if(!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            if (checkHours(hourStart,hourEnd))
                rs=selector.executeQuery("SELECT * FROM "+dbo+" WHERE "+ (
                        ( "CAST (StartIdle as date )"+" >='"+dateStart+"' AND ("+"CAST (StartIdle as date ) <='"+dateEnd+"')")+
                                " AND " + (" CAST (StartIdle as time )"+" >='"+hourStart+"' AND ("+  " CAST (StartIdle as time )"+" <='"+hourEnd+"')"))+" ORDER BY "+"StartIdle");
            else {
                String nextDay=getNextDate(dateEnd);
                rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + ("CAST (StartIdle as date )" + " >='" + dateStart + "'  AND " +
                        "(" + "CAST (StartIdle as date )" + " <= '" + dateEnd + "')" + " AND " +
                        "CAST (StartIdle as time)" + " NOT BETWEEN '" + hourEnd + "'  AND '" + hourStart + "'") +
                        " OR " + ("CAST (StartIdle as date ) = '" + nextDay + "' AND " + "CAST (StartIdle as time)" + " BETWEEN '" + "00:00" + "'  AND '" + hourEnd + "'")+
                        " ORDER BY " + "StartIdle");

            }
            addToArrayList(rs,dataByDate);
        }catch(Exception grabDataException){
            System.out.println("SqlServer.java - getDataByDate() Exception - " + grabDataException.getMessage());
        }
        return dataByDate;
    }
    //return   Returns data from dbo by machineId
    public ArrayList<Stop> getDataByMachineId(String dbo,String MachineID)
    {
        ArrayList<Stop> dataByMachineID = new ArrayList<>();
        try{
            if(!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            rs=selector.executeQuery("SELECT * FROM "+dbo+" WHERE "+"MachineId = "+MachineID+" ORDER BY "+"StartIdle");
            addToArrayList(rs,dataByMachineID);
        }catch(Exception grabDataException){
            System.out.println("SqlServer.java - getDataByDate() Exception - " + grabDataException.getMessage());
        }
        return dataByMachineID;
    }

    //returns data from dbo by machineID and within the date range
    public ArrayList<Stop> getDataByDateAndHoursMachineID(String dbo,String dateStart, String dateEnd,String hourStart,String hourEnd,String MachineID)
    {
        ArrayList<Stop> dataByDateAndMachineID = new ArrayList<>();
        try{
            if(!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            if (checkHours(hourStart,hourEnd))
                rs = selector.executeQuery("SELECT * FROM "+dbo+" WHERE "+"CAST (StartIdle as date )"+" >='"+dateStart+"'  AND " +
                        "("+"CAST (StartIdle as date )"+" <= '"+dateEnd+"')"+" AND "+
                        ( "CAST (StartIdle as time)"+" >='"+hourStart)+"'  AND ("+"CAST (StartIdle as time)"+" <= '"+hourEnd+"')"+" AND (MachineId = "+MachineID+")"+" ORDER BY "+"StartIdle");
            else {
                String nextDay=getNextDate(dateEnd);
                rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + ("CAST (StartIdle as date )" + " >='" + dateStart + "'  AND " +
                        "(" + "CAST (StartIdle as date )" + " <= '" + dateEnd + "')" + " AND " +
                        "CAST (StartIdle as time)" + " NOT BETWEEN '" + hourEnd + "'  AND '" + hourStart + "' AND (MachineId = " + MachineID + ")" )
                        + " OR " + ("CAST (StartIdle as date ) = '" + nextDay + "' AND " + "CAST (StartIdle as time)" + " BETWEEN '" + "00:00" + "'  AND '" + hourEnd + "'")
                        + " ORDER BY " + "StartIdle");
            }
            addToArrayList(rs,dataByDateAndMachineID);
        }catch(Exception grabDataException){
            System.out.println("SqlServer.java - getDataByDate() Exception - " + grabDataException.getMessage());
        }
        return dataByDateAndMachineID;
    }


    private void addToArrayList(ResultSet rs,ArrayList<Stop> data) throws SQLException {
        while (rs.next()){
            Stop currentRow=new Stop(rs.getString("StartIdle"),rs.getString("EndIdle"),rs.getString("MachineId"),
                    rs.getString("ManagerBegin"),rs.getInt("IdleCause"));
            data.add(currentRow);
        }
    }
    public boolean checkHours(String startHour,String endHour){
        Integer startH = new Integer(startHour.substring(0, 2));
        Integer endH = new Integer(endHour.substring(0, 2));
        Integer startM = new Integer(startHour.substring(3));
        Integer endM = new Integer(endHour.substring(3));
        if (startH > endH||(startH.equals(endH) &&startM>endM))
            return false;
        return true;
    }
    public String getNextDate(String curDate) throws ParseException {
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         Date date = (Date) format.parse(curDate);
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.add(Calendar.DAY_OF_YEAR, 1);
         return format.format(calendar.getTime());
    }
}
