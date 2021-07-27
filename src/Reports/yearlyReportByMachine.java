package reports;  

import Objects.Cycle;
import Objects.DBInfo;
import Objects.Stop;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate; 

public class yearlyReportByMachine extends abstractReportByMachine {

    private int    add = 0      ;
    private String yearStart    ;
    int            rowNumber = 0;
     
    
    public yearlyReportByMachine(DBInfo dbInfo,String machineID, String startDate, String endDate, String startHour, String endHour,double workHours,double morningWorkHours,double eveningWorkHours, boolean divideByShifts
            ,String morningStart,String morningEnd,String eveningStart,String eveningEnd,double fridayWorkHours,double saturdayWorkHours,int typeReport) throws ParseException {
        super(dbInfo,machineID,startDate,endDate,startHour,endHour,workHours,morningWorkHours,eveningWorkHours,divideByShifts,morningStart,morningEnd,eveningStart,eveningEnd,fridayWorkHours,saturdayWorkHours,typeReport);
         int numberOfYears=getNumberOfYears(startDate,endDate);
        int numberOfColumn=12;
        yearStart=startDate.substring(0,4);
        if (divideByShifts) {//if is divided by shifts get morning report
            numberOfYears = numberOfYears * 2;
            numberOfColumn = 13;
            this.workHours=eveningWorkHours;
            isMorning = true;
            add = 1;
        }
        tableData = new Object[numberOfYears][numberOfColumn];
        String[] divideByShiftColumn = {"יעילות מכונה %","ניצול תפוקת מכונה %", "ניצול זמן מכונה %", "תפוקת טון לשעה", "משקל כללי KG", "מס יחידות", "זמן שירות שעות", "מספר הטענות יחידות", "זמן הטענות שעות", "מכונה בעבודה שעות", "זמינות מכונה שעות", "משמרת", "שנה"};
        String[] column = {"יעילות מכונה %","ניצול תפוקת מכונה %", "ניצול זמן מכונה %", "תפוקת טון לשעה", "משקל כללי KG", "מס יחידות", "זמן שירות שעות", "מספר הטענות יחידות", "זמן הטענות שעות", "מכונה בעבודה שעות", "זמינות מכונה שעות", "שנה"};
        analyseData(isMorning);
        if (divideByShifts){//get evening report
            cyclesTableInfo = cycle.getDataByDateAndHoursAndMachineID(dbInfo.getTableCycles(), startDate, endDate,eveningStart,eveningEnd, machineID);
            stopTableInfo = stop.getDataByDateAndHoursMachineID(dbInfo.getTableStops(), startDate, endDate,eveningStart,eveningEnd, machineID);
            this.workHours=10.5;
            analyseData(false);
        }
        if (divideByShifts)
            tableModel = new DefaultTableModel(tableData, divideByShiftColumn);
        else tableModel = new DefaultTableModel(tableData, column);
        //remove empty rows from table
        deleteEmptyRows(numberOfYears,tableData,tableModel,8,add);

        table = new JTable(tableModel);
        JScrollPane jScrollPane = new JScrollPane(table);
        table.setAutoCreateRowSorter(true);
        setLayout(new BorderLayout());
        add(jScrollPane, BorderLayout.CENTER);
    } 
     
    /*
    public static void main(String[] args) throws ParseException {
        yearlyReportByMachine report=new yearlyReportByMachine("33", "2020-01-01", "2021-12-31", "06:30", "19:00",11.5, true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(report);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
    */
    /*analyse the Cycle and stop data by
   -if the shift is a morning  shift only by different years
   -if the shift is evening shift by different years and by hours
   meaning the data of the night hours of 01.01.20 will be saved to 31.12.19- done by the checkHour function
   in addition night hours of the first day in the first year will not be saved(for example if we star in 2019 the data between 00:00-06:30 of 01.01.19
   will not be saved done by a boolean check and checkHour function

 */
    @Override
    public void analyseData( boolean isMorning) throws ParseException {
        String currentYear=yearStart;
        if (cyclesTableInfo.size()>0) {
            currentYear = cyclesTableInfo.get(0).getYear();
        }
        long numberOfSeconds = 0;
        int cutCounter = 0;
        int totalWeight = 0;
        double test=0;
        int count=rowNumber;
        boolean morning=isMorning;
        boolean cycleFirstDayDivided=false;
        boolean stopFirstDayDivided=false;
        if (divideByShifts||!morning) {
            if (cyclesTableInfo.size()>0&&!morning)
                cycleFirstDayDivided=checkHour(cyclesTableInfo.get(0).getHour(),morningEnd)&&cyclesTableInfo.get(0).getDate().substring(5).equals("01-01");
            if (stopTableInfo.size()>0&&!morning)
                stopFirstDayDivided=checkHour(stopTableInfo.get(0).getHour(),morningEnd)&&stopTableInfo.get(0).getDate().substring(5).equals("01-01");
        }
        for (int i = 0; i < cyclesTableInfo.size(); i++) {
            Cycle curr = cyclesTableInfo.get(i);
            boolean check=!morning&&!checkHour(curr.getHour(),morningEnd);
            String prevYear=getPrevDay(curr.getDate()).substring(4);
            if ((!(currentYear.equals(curr.getYear()))&&(morning||check||!morning&&currentYear.equals(prevYear)))||(cycleFirstDayDivided&&check) || i + 1 == cyclesTableInfo.size()) {
                if (!cycleFirstDayDivided) {//check if first day in evening shift
                    if (i + 1 == cyclesTableInfo.size()) {//if last day
                        boolean nightH=checkHour(curr.getHour(), morningEnd);//return true if night hours
                        if (currentYear.equals(curr.getYear()) || currentYear.equals(prevYear) && nightH) {
                            test=test+curr.getMachineOutputUsage()*curr.getTWork();
                            numberOfSeconds = numberOfSeconds + curr.getTWork();
                            cutCounter = cutCounter + curr.getCutCounter();
                            totalWeight = totalWeight + curr.getTotalWeight();
                        }
                        else {
                            addDataAboutCycle(count,currentYear,morning,numberOfSeconds,cutCounter,totalWeight,test);
                            currentYear=curr.getYear();
                            if (!morning&&nightH)
                             currentYear =prevYear;
                            count++;
                            test=curr.getMachineOutputUsage()*curr.getTWork();
                            numberOfSeconds =curr.getTWork();
                            cutCounter = curr.getCutCounter();
                            totalWeight = curr.getTotalWeight();

                        }
                    }
                    //add all data
                    addDataAboutCycle(count,currentYear,morning,numberOfSeconds,cutCounter,totalWeight,test);
                    count++;
                }
                //initialize the data--we moved to different day or shift
                cycleFirstDayDivided=false;
                test=curr.getMachineOutputUsage()*curr.getTWork();
                numberOfSeconds = curr.getTWork();
                cutCounter = curr.getCutCounter();
                totalWeight = curr.getTotalWeight();
                currentYear=curr.getYear();
            }
            else if (!cycleFirstDayDivided){
                test=test+curr.getMachineOutputUsage()*curr.getTWork();
                numberOfSeconds = numberOfSeconds + curr.getTWork();
                cutCounter = cutCounter + curr.getCutCounter();
                totalWeight = totalWeight + curr.getTotalWeight();
            }
        }
        currentYear=yearStart;
        if (stopTableInfo.size()>0) {
            currentYear = stopTableInfo.get(0).getYear();
        }
        int countLoading = 0;
        long timeLoading = 0;
        long timeServes = 0;
        for (int i = 0; i < stopTableInfo.size(); i++) {
            Stop current = stopTableInfo.get(i);
            boolean check=!morning&&!checkHour(current.getHour(),morningEnd)&&currentYear.equals(getPrevDay(current.getDate()));
            String prevDay=getPrevDay(current.getDate());
            String prevYear=prevDay.substring(4);
            if ((!(currentYear.equals(current.getYear()))&&(morning||check||!morning&&!currentYear.equals(prevYear)))
                    ||(stopFirstDayDivided&&check)|| i + 1 == stopTableInfo.size()) {
                if (!stopFirstDayDivided) {//check if first day in evening shift
                    if (i + 1 == stopTableInfo.size()) {
                        boolean nightH = checkHour(current.getHour(), morningEnd);//return true if night hours
                        if (currentYear.equals(current.getYear()) || currentYear.equals(prevDay) && nightH) {
                            if (current.getIdleCause() == 2) {
                                countLoading = countLoading + 1;
                                timeLoading = timeLoading + getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                            } else if (current.getIdleCause() == 4)
                                timeServes = timeServes + getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                        }else {
                            count=addDataAboutStop(count,currentYear,morning,timeLoading,countLoading,timeServes);
                            currentYear=current.getYear();
                            if (!morning&&nightH)
                                currentYear = prevYear;
                            timeLoading = 0;
                            countLoading = 0;
                            timeServes = 0;
                            if (current.getIdleCause() == 2) {
                                countLoading = 1;
                                timeLoading = getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());

                            } else if (current.getIdleCause() == 4) {
                                timeServes = getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                            }
                        }
                        count=addDataAboutStop(count,currentYear,morning,timeLoading,countLoading,timeServes);
                    }
                }
                stopFirstDayDivided=false;
                //initialize the data--we moved to different day or shift
                currentYear=current.getYear();
                timeLoading = 0;
                countLoading = 0;
                timeServes = 0;
                if (current.getIdleCause() == 2) {
                    countLoading = 1;
                    timeLoading = getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());

                } else if (current.getIdleCause() == 4) {
                    timeServes = getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                }
            }
            else if (!stopFirstDayDivided){
                if (current.getIdleCause() == 2) {
                    countLoading = countLoading + 1;
                    timeLoading = timeLoading + getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                } else if (current.getIdleCause() == 4)
                    timeServes = timeServes + getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
            }
        }
        rowNumber=count;

    }



    public int getNumberOfYears(String startYear,String endYear) {
     Integer yearS=new Integer(startYear.substring(0,4));
        Integer yearE=new Integer(endYear.substring(0,4));
        return yearE-yearS+1;
    }

    public double getEstimatedWorkHourYear(String year,boolean morning){
        LocalDate d = LocalDate.parse(year+"-12-31");
        Integer year1=new Integer(year);
        int dayInYear= d.lengthOfYear();
        int numberDaysOfFriday=52;
        int numberDaysOfSaturdays=52;
        if (LocalDate.of(year1, 1, 1).getDayOfWeek() == DayOfWeek.FRIDAY||dayInYear==366&&LocalDate.of(year1, 1, 2).getDayOfWeek() == DayOfWeek.FRIDAY){
            numberDaysOfFriday=53;
        }
        else if (LocalDate.of(year1, 1, 1).getDayOfWeek() == DayOfWeek.SATURDAY||dayInYear==366 &&LocalDate.of(year1, 1, 2).getDayOfWeek() == DayOfWeek.SATURDAY){
            numberDaysOfSaturdays=53;
        }
        int yearWorkDays=dayInYear-numberDaysOfFriday-numberDaysOfSaturdays;
        if (divideByShifts) {
            if (morning)
                return yearWorkDays * workHours + numberDaysOfFriday * fridayWorkHours;
            else return yearWorkDays*workHours+numberDaysOfSaturdays*saturdayWorkHours;
        }else
            return yearWorkDays*workHours+numberDaysOfFriday*fridayWorkHours+numberDaysOfSaturdays*saturdayWorkHours;

    }
    public void addDataAboutCycle(int count,String currentYear,boolean morning,long numberOfSeconds,int cutCounter,int totalWeight,double test){
        //add all data
        tableData[count][11 + add] = currentYear;
        tableData[count][10] =getEstimatedWorkHourYear(currentYear,morning);
        if (divideByShifts) {
            if (!morning) {
                tableData[count][11] = "ערב";
            } else tableData[count][11] = "בוקר";

        }
        tableData[count][9] =getHour(numberOfSeconds);
        tableData[count][5] = cutCounter;
        tableData[count][4] = totalWeight/1000;
        tableData[count][6] = 0;
        tableData[count][7] = 0;
        tableData[count][8] = 0;
        tableData[count][1]=(double) Math.round((test/numberOfSeconds) * 100) / 100;
        setMachineTimeEfficiency(count);
        setTonPerHour(count);
        setMachineEfficiency(count);
    }
    public int addDataAboutStop(int count,String currentYear,boolean morning,long timeLoading,int countLoading,long timeServes) {
        int date;
        //find the date in the table
        if (divideByShifts)
            date = findDateAndShift(currentYear, morning,12);
        else date = findDate( currentYear,11);
        if (date == -1) {
            date = count;
            tableData[count][11 + add] = countLoading;
            tableData[count][10] = getEstimatedWorkHourYear(currentYear,morning);
            if (divideByShifts) {
                if (!morning) {
                    tableData[count][11] = "ערב";
                } else tableData[count][11] = "בוקר";

            }
            //add data
            tableData[count][4] = 0;
            tableData[count][5] = 0;
            tableData[count][9] = 0.0;
            count++;
        }
        tableData[date][8] =getHour(timeLoading);
        tableData[date][7] = countLoading;
        tableData[date][6] =getHour(timeServes);
        return count;

    }
}
