
package SQL;
 
import Objects.Cycle;
import Objects.DBInfo;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
public class SQLCycles extends SQLConnection{

    
    /*
    Constructor
    dbServerIp   - sql server ip
    dbServerPort - sql server port
    dbName     - sql server database name
    dbUsername - sql server database login username
    dbPassword - sql server database login password
    */
    
    public SQLCycles(DBInfo dbInfo){ 
        super (dbInfo);
    } 
    /*
    Prints entire database. 
    */
    public void print(ArrayList<Cycle> data){
        for(int i = 0 ; i < data.size() ; i++){
            data.get(i).print();
        }
    }
    /*
    Returns entire database rows.
    dbo - database to return
    */
    public ArrayList<Cycle> getDataBase(String  dbo) {
        ArrayList<Cycle> data=new ArrayList<>();
        try{
             if(!connectedToDb) return null;
             selector = (Statement) connector.createStatement();
             rs       = selector.executeQuery("SELECT * FROM "+dbo);

            addToArrayList(rs,data);
        }catch(SQLException queryException)
        {
            System.out.println("SQLCycles.java - printByQuery() Exception - " + queryException.getMessage());
        }
        return data;
    }
    /*
    Returns data from dbo that is within the date range
    dbo            - database to fetch from
    dateStart      - start date to fetch from
    dateEnd        - end date to stop fetching
    */
    public boolean checkHours(String startHour,String endHour){
        Integer startH = new Integer(startHour.substring(0, 2));
        Integer endH = new Integer(endHour.substring(0, 2));
        Integer startM = new Integer(startHour.substring(3));
        Integer endM = new Integer(endHour.substring(3));
        if (startH > endH||(startH.equals(endH) &&startM>endM))
            return false;
        return true;
    }


    /*
    Returns data from dbo that is within the date range
    dbo            - database to fetch from
    dateColumnName - name of column that that has the date
    dateStart      - start date to fetch from
    dateEnd        - end date to stop fetching
    */
    public ArrayList<Cycle> getDataByDate(String dbo, String columnDateName, String dateStart, String dateEnd, String hourStart, String hourEnd) {
        ArrayList<Cycle> dataByDate = new ArrayList<>();
        try {
            if (!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            if (checkHours(hourStart,hourEnd))
                rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + (
                        ("CAST (StartCycle as date )" + " >='" + dateStart + "' AND (" + "CAST (StartCycle as date ) <='" + dateEnd + "')") +
                                " AND " + (" CAST (StartCycle as time )" + " >='" + hourStart + "' AND (" + " CAST (StartCycle as time )" + " <='" + hourEnd + "')")) + " ORDER BY " + "StartCycle");
            else {
                String nextDay=getNextDate(dateEnd);
                rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + ("CAST (StartCycle as date )" + " >='" + dateStart + "'  AND " +
                        "(" + "CAST (StartCycle as date )" + " <= '" + dateEnd + "')" + " AND " +
                        "CAST (StartCycle as time)" + " NOT BETWEEN '" + hourEnd + "'  AND '" + hourStart+"'")+
                        " OR " +("CAST (StartCycle as date ) = '"+nextDay+"' AND "+  "CAST (StartCycle as time)" + " BETWEEN '" + "00:00" + "'  AND '" + hourEnd+"'") +" ORDER BY " + "StartCycle");
            }
            addToArrayList(rs, dataByDate);
        } catch (Exception grabDataException) {
            System.out.println("SQLCycles.java - getDataByDate() Exception - " + grabDataException.getMessage());
        }
        return dataByDate;
    }


    //return   Returns data from dbo by machineId
    public ArrayList<Cycle> getDataByMachineId(String dbo, String MachineID) {
        ArrayList<Cycle> dataByMachineID = new ArrayList<>();
        try {
            if (!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + "MachineId = " + MachineID + " ORDER BY " + "StartCycle");

            addToArrayList(rs, dataByMachineID);
        } catch (Exception grabDataException) {
            System.out.println("SqlServer.java - getDataByMachineId() Exception - " + grabDataException.getMessage());
        }
        return dataByMachineID;
    }

    //returns data from dbo by workerNumber
    public ArrayList<Cycle> getDataByWorker(String dbo, String workerNumber) {
        ArrayList<Cycle> dataByWorker = new ArrayList<>();
        try {
            if (!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + "WorkerNumber = " + workerNumber + " ORDER BY " + "StartCycle");
            addToArrayList(rs, dataByWorker);
        } catch (Exception grabDataException) {
            System.out.println("SQLCycles.java - getDataByWorker() Exception - " + grabDataException.getMessage());
        }
        return dataByWorker;

    }

    //returns data from dbo by workerNumber and within the date range
    public ArrayList<Cycle> getDataByDateAndWorkerNumber(String dbo, String dateStart, String dateEnd, String hourStart, String hourEnd, String workerNumber) {

        ArrayList<Cycle> dataByDateAndWorker = new ArrayList<>();
        try {
            if (!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            if (checkHours(hourStart,hourEnd))
                rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + "CAST (StartCycle as date )" + " >='" + dateStart + "'  AND (" + "CAST (StartCycle as date )" + " < ='" + dateEnd + "')" + " AND " +
                        ("CAST (StartCycle as time )" + " >='" + hourStart) + "'  AND (" + "CAST (EndCycle as time)" + " <= '" + hourEnd + "')" + " AND (WorkerNumber = " + workerNumber + ")" + " ORDER BY " + "StartCycle");
            else {
                String nextDay=getNextDate(dateEnd);
                rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + ("CAST (StartCycle as date )" + " >='" + dateStart + "'  AND " +
                        "(" + "CAST (StartCycle as date )" + " <= '" + dateEnd + "')" + " AND " +
                        "CAST (StartCycle as time)" + " NOT BETWEEN '" + hourEnd + "'  AND '" + hourStart+"' AND (WorkerNumber = " + workerNumber + ")")
                        +" OR " +("CAST (StartCycle as date ) = '"+nextDay+"' AND "+  "CAST (StartCycle as time)" + " BETWEEN '" + "00:00" + "'  AND '" + hourEnd+"'")
                        + " ORDER BY " + "StartCycle");
            }
            addToArrayList(rs, dataByDateAndWorker);
        } catch (Exception grabDataException) {
            System.out.println("SQLCycles.java - getDataByDateAndWorkerNumber() Exception - " + grabDataException.getMessage());
        }
        return dataByDateAndWorker;
    }

    //returns data from dbo by machineID and within the date range
    public ArrayList<Cycle> getDataByDateAndHoursAndMachineID(String dbo, String dateStart, String dateEnd, String hourStart, String hourEnd, String MachineID) {

        ArrayList<Cycle> dataByDateAndMachineID = new ArrayList<>();
        try {
            if (!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            if (checkHours(hourStart,hourEnd))
                rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + "CAST (StartCycle as date )" + " >='" + dateStart + "'  AND " +
                        "(" + "CAST (StartCycle as date )" + " <= '" + dateEnd + "')" + " AND " +
                        ("CAST (StartCycle as time)" + " BETWEEN '" + hourStart + "'  AND '" + hourEnd + "'" + " AND (MachineId = " + MachineID + ")" + " ORDER BY " + "StartCycle"));
            else {

                String nextDay=getNextDate(dateEnd);
                rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + ("CAST (StartCycle as date )" + " >='" + dateStart + "'  AND " +
                        "(" + "CAST (StartCycle as date )" + " <= '" + dateEnd + "')" + " AND " +
                        (("CAST (StartCycle as time)" + " NOT BETWEEN '" + hourEnd + "'  AND '" + hourStart+"'")+" AND (MachineId = " + MachineID + ")")+" OR "
                        +"CAST (StartCycle as date ) = '"+nextDay+"' AND "+  ("CAST (StartCycle as time)" + " BETWEEN '" + "00:00" + "'  AND '" + hourEnd+"'") +
                        " ORDER BY " + "StartCycle"));
            }
            addToArrayList(rs, dataByDateAndMachineID);
        } catch (Exception grabDataException) {
            System.out.println("SQLCycles.java - getDataByDateAndHoursAndMachineID() Exception - " + grabDataException.getMessage());
        }
        return dataByDateAndMachineID;
    }

    //returns data from dbo by machineID and within the date range
    public ArrayList<Cycle> getDataByDateAndMachineIDAndWorker(String dbo, String dateStart, String dateEnd, String hourStart, String hourEnd, String MachineID, String workerNumber) {

        ArrayList<Cycle> dataByDateAndMachineID = new ArrayList<>();
        try {
            if (!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            if (checkHours(hourStart,hourEnd))
                rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + "CAST (StartCycle as date )" + " >'" + dateStart + "'  AND (" + "CAST (StartCycle as date )" + " < '" + dateEnd + "')" + " AND " +
                        ("CAST (StartCycle as time)" + " >='" + hourStart) + "'  AND (" + "CAST (EndCycle as time)" + " <= '" + hourEnd + "')" +
                        " AND (MachineId = " + MachineID + ")" + " AND (WorkerNumber = " + workerNumber + ")" + " ORDER BY " + "StartCycle");
            else {
                String nextDay=getNextDate(dateEnd);
                rs = selector.executeQuery("SELECT * FROM " + dbo + " WHERE " + ("CAST (StartCycle as date )" + " >='" + dateStart + "'  AND " +
                        "(" + "CAST (StartCycle as date )" + " <= '" + dateEnd + "')" + " AND " +
                        (("CAST (StartCycle as time)" + " NOT BETWEEN '" + hourEnd + "'  AND '" + hourStart+"' AND (MachineId = " + MachineID + ")"+ " AND (WorkerNumber = " + workerNumber + ")")
                                +" OR " +"CAST (StartCycle as date ) = '"+nextDay+"' AND "+  ("CAST (StartCycle as time)" + " BETWEEN '" + "00:00" + "'  AND '" + hourEnd+"'") + " ORDER BY " + "StartCycle")));
            }
            addToArrayList(rs, dataByDateAndMachineID);
        } catch (Exception grabDataException) {
            System.out.println("SQLCycles.java - getDataByDateAndMachineIDAndWorker() Exception - " + grabDataException.getMessage());
        }
        return dataByDateAndMachineID;
    }
    private void addToArrayList(ResultSet rs,ArrayList<Cycle> data) {
        try{
        while (rs.next()){
              Cycle currentRow=new Cycle(rs.getString("StartCycle"),rs.getString("EndCycle"),rs.getString("MachineId"),
                    rs.getString("WorkerNumber"),rs.getInt("Twork"),rs.getInt("AverageSpeed"),rs.getInt("TotalLength")
                    ,rs.getInt("BendingCounter"),rs.getInt("TotalWeight"),rs.getInt("CutCounter"),rs.getInt("WireDeameter"),
                    rs.getInt("WireAmount"),rs.getInt("WeightPerMeter"),rs.getDouble("test9"));
              data.add(currentRow);
        }
        }catch(Exception addException){
            System.out.println("SQLCycles.java - addToArrayList() Exception - " + addException.getMessage());
            addException.printStackTrace();
        }
    }
    public String getNextDate(String curDate) throws  ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(curDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return format.format(calendar.getTime());
    }
}
