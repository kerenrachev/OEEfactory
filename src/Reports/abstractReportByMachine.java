package reports; 

import Objects.Cycle;
import Objects.DBInfo;
import Objects.Stop;
import SQL.SQLCycles;
import SQL.SQLStops;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class abstractReportByMachine extends JPanel {
   protected  String endDate;
    protected Object [][] tableData;
    protected double workHours;
    protected SQLStops  stop;
    protected SQLCycles cycle;
    protected ArrayList<Cycle> cyclesTableInfo;
    protected ArrayList<Stop>   stopTableInfo;
    protected Boolean divideByShifts;
    protected String hourStart;
    protected String hourEnd;
    protected String startDate;
    protected String currentDate;
    protected String morningStart;
    protected String morningEnd;
    protected String eveningStart;
    protected String eveningEnd    ;
    protected double morningWorkHours;
    protected  double eveningWorkHours;
    protected double fridayWorkHours;
    protected double saturdayWorkHours ;
    protected boolean isMorning;
    protected DefaultTableModel tableModel;
  
    protected JTable table;
    protected int typeReport; //1 daily, 2 YEARLY, 3 MONTHLY
    public abstractReportByMachine(DBInfo dbInfo,String machineID, String startDate, String endDate, String startHour, String endHour, double workHours,double morningWorkHours,double eveningWorkHours,
                                   boolean divideByShifts,String morningStart,String morningEnd , String eveningStart, String eveningEnd,double fridayWorkHours,double saturdayWorkHours
    ,int typeReport) {
        stop  = new SQLStops (dbInfo);
        cycle = new SQLCycles(dbInfo);
        currentDate = startDate;
        this.startDate = startDate;
        this.endDate=endDate;
        this.hourEnd = endHour;
        this.hourStart = startHour;
        this.divideByShifts = divideByShifts;
        this.workHours = workHours;
        this.morningStart=morningStart;
        this.morningEnd=morningEnd;
        this.eveningStart=eveningStart;
        this.eveningEnd=eveningEnd;
        this.eveningWorkHours=eveningWorkHours;
        this.morningWorkHours=morningWorkHours;
        this.fridayWorkHours=fridayWorkHours;
        this.saturdayWorkHours=saturdayWorkHours;
        this.typeReport = typeReport;
        isMorning = isMorningHours(startHour, endHour);//check the hours
        if (divideByShifts) {//if is divided by shifts get morning report
            cyclesTableInfo = cycle.getDataByDateAndHoursAndMachineID(dbInfo.getTableCycles(), startDate, endDate, morningStart, morningEnd, machineID);
            stopTableInfo = stop.getDataByDateAndHoursMachineID(dbInfo.getTableStops(), startDate, endDate, morningStart, morningEnd, machineID);
            this.workHours=morningWorkHours;
        } else {
            cyclesTableInfo = cycle.getDataByDateAndHoursAndMachineID(dbInfo.getTableCycles(), startDate, endDate, startHour, endHour, machineID);
            stopTableInfo = stop.getDataByDateAndHoursMachineID(dbInfo.getTableStops(), startDate, endDate, startHour, endHour, machineID); 
        }
    }  
    public JTable getTable(){
        return table;
    }
    public int getTypeReport(){
        return typeReport;
    }
    public boolean isDevidedByShift(){
        return divideByShifts;
    }
    abstract public void analyseData( boolean isMorning) throws ParseException;

    public String getPrevDay(String curDate) throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = format.parse(curDate);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return format.format(calendar.getTime());
    }
    //get number of seconds between two dates and hours
    public long getNumberOfSeconds(String startDate, String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(startDate);
            d2 = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.MILLISECONDS.toSeconds(diff);
    }


    //convert seconds to hours and minutes
    public Double getHour(long seconds) {
        double hours =seconds/3600.0;
      return (double) Math.round(hours*100)/100;
    }

    //check if the hour is of the night shift
    public boolean checkHour(String inputHour, String morningEnd) {
        Integer inputH = new Integer(inputHour.substring(0, 2));
        Integer inputM = new Integer(inputHour.substring(3));
        Integer hourE = new Integer(morningEnd.substring(0, 2));
        Integer minE = new Integer(morningEnd.substring(3));
        if (inputH< hourE || inputH.equals(hourE) && inputM <= minE) {
            return true;
        }
        return false;
    }

    //check if the hours is night hours
    public boolean isMorningHours(String startHour, String endHour) {
        Integer startH = new Integer(startHour.substring(0, 2));
        Integer endH = new Integer(endHour.substring(0, 2));
        Integer startM = new Integer(startHour.substring(3));
        Integer endM = new Integer(endHour.substring(3));
        if (startH > endH||(startH.equals(endH) &&startM>endM))
            return false;
        return true;
    }

    public void deleteEmptyRows(int numberOfRows, Object[][] tableData, DefaultTableModel tableModel, int columnNumber, int add) {
        int numberOfDeletedRows = 0;
        for (int i = 0; i < numberOfRows; i++) {
            if (tableData[i][columnNumber + add] == null) {
                tableModel.removeRow(i - numberOfDeletedRows);
                numberOfDeletedRows++;
            }
        }
    }

    public int findDate( String date, int columnNumber) {
        for (int i = 0; i < tableData.length; i++) {
            if (tableData[i][columnNumber] != null && tableData[i][columnNumber].equals(date))
                return i;
        }
        return -1;
    }

    public int findDateAndShift( String date, boolean morning, int columnNumber) {
        String shift;
        if (morning)
            shift = "בוקר";
        else shift = "ערב";
        for (int i = 0; i < tableData.length; i++) {
            if (tableData[i][columnNumber] != null && (tableData[i][columnNumber].equals(date) && tableData[i][11] != null && tableData[i][11].equals(shift)))
                return i;
        }
        return -1;
    }

    public void setTonPerHour(int count) {
            Double efficiency = ((Double) (Math.abs((Integer) tableData[count][4] /(Double) tableData[count][9])) / 1000);
            if (efficiency.isNaN())
                efficiency = 0.0;
            else {
                efficiency = (double) Math.round(efficiency * 100) / 100;
            }
         tableData[count][3]=efficiency;
    }

    public void setMachineTimeEfficiency(int count) {
        tableData[count][2]=(int) (Math.abs((Double) tableData[count][9]/ ((Double) tableData[count][10])) * 100);
        }
    public void setMachineEfficiency(int count) {
        tableData[count][0]=(int) (Math.abs((int)tableData[count][2]* ((Double) tableData[count][1])) / 100);
    }

    public DefaultTableModel getTableModel(){
        return tableModel;
    }
    public Object [][]getTableData(){
        return tableData;
    }
}
