package reports;
 
import Objects.Cycle;
import Objects.DBInfo;
import Objects.Stop;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class monthlyReportByMachine extends abstractReportByMachine {
    private int    add=0;
    private String monthStart;
    private String yearStart;
    int            rowNumber = 0;
 
 
    public monthlyReportByMachine(DBInfo dbInfo,String machineID, String startDate, String endDate, String startHour, String endHour,double workHours,double morningWorkHours,double eveningWorkHours, boolean divideByShifts
            ,String morningStart,String morningEnd,String eveningStart,String eveningEnd,double fridayWorkHours,double saturdayWorkHours,int typeReport) throws ParseException {
        super(dbInfo,machineID,startDate,endDate,startHour,endHour,workHours,morningWorkHours,eveningWorkHours,divideByShifts,morningStart,morningEnd,eveningStart,eveningEnd,fridayWorkHours,saturdayWorkHours,3);
        
         int numberOfMonths=getNumberOfMonths(startDate,endDate);
        int numberOfColumn=12;
        monthStart=startDate.substring(5,7);
         yearStart=startDate.substring(0,4);
        if (divideByShifts) {//if is divided by shifts get morning report
            numberOfMonths = numberOfMonths * 2;
            numberOfColumn = 13;
            isMorning = true;
            add = 1;
        }
        tableData = new Object[numberOfMonths][numberOfColumn];
        String[] divideByShiftColumn = {"יעילות מכונה %","ניצול תפוקת מכונה %", "ניצול זמן מכונה %", "תפוקת טון לשעה", "משקל כללי KG", "מס יחידות", "זמן שירות שעות", "מספר הטענות יחידות", "זמן הטענות שעות", "מכונה בעבודה שעות", "זמינות מכונה שעות", "משמרת", "חודש"};
        String[] column = {"יעילות מכונה %","ניצול תפוקת מכונה %", "ניצול זמן מכונה %", "תפוקת טון לשעה", "משקל כללי KG", "מס יחידות", "זמן שירות שעות", "מספר הטענות יחידות", "זמן הטענות שעות", "מכונה בעבודה שעות", "זמינות מכונה שעות", "חודש"};
        analyseData(isMorning);
        if (divideByShifts){//get evening report
            cyclesTableInfo = cycle.getDataByDateAndHoursAndMachineID(dbInfo.getTableCycles(), startDate, endDate,eveningStart,eveningEnd, machineID);
            stopTableInfo = stop.getDataByDateAndHoursMachineID(dbInfo.getTableStops(), startDate, endDate,eveningStart,eveningEnd, machineID);
            this.workHours=eveningWorkHours;
            analyseData(false);
        }
        if (divideByShifts)
            tableModel = new DefaultTableModel(tableData, divideByShiftColumn);
        else tableModel = new DefaultTableModel(tableData, column);
        //remove empty rows from table
        deleteEmptyRows(numberOfMonths,tableData,tableModel,8,add);
        table = new JTable(tableModel);
        JScrollPane jScrollPane = new JScrollPane(table);
        table.setAutoCreateRowSorter(true);
        setLayout(new BorderLayout());
        add(jScrollPane, BorderLayout.CENTER);
    }
    /*
    public static void main(String[] args) throws ParseException {
        monthlyReportByMachine report=new monthlyReportByMachine("33", "2020-09-01", "2020-09-30", "06:30", "19:00",11.5, true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(report);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
    */
     public JTable getTable(){
        return table;
    }
    /*analyse the Cycle and stop data by
    -if the shift is a morning  shift only by different months
    -if the shift is evening shift by different month and by hours
    meaning the data of the night hours of 01.12.20 will be saved to november - done by the checkHour function
    in addition the night hours of the first day (if the first day is 01) of first month  will not be saved
    for ex if the first month is august the hours between 00:00-06:30 in 01.08 will not be saved -done by a boolean check and checkHour function
     */
    @Override
    public void analyseData( boolean isMorning) throws ParseException {
        String currentMonth=monthStart;
        String currentYear=yearStart;
        if (cyclesTableInfo.size()>0) {
            currentMonth = cyclesTableInfo.get(0).getMonth();
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
                cycleFirstDayDivided=checkHour(cyclesTableInfo.get(0).getHour(),morningEnd)&&cyclesTableInfo.get(0).getDate().substring(8).equals("01");
            if (stopTableInfo.size()>0&&!morning)
                stopFirstDayDivided=checkHour(stopTableInfo.get(0).getHour(),morningEnd)&&stopTableInfo.get(0).getDate().substring(8).equals("01");
        }for (int i = 0; i < cyclesTableInfo.size(); i++) {
            Cycle curr = cyclesTableInfo.get(i);
            boolean check=!morning&&!checkHour(curr.getHour(),morningEnd);
            String prevDay=getPrevDay(curr.getDate());
            String prevMonth=getPrevMonth(prevDay);
            if ((!(currentMonth.equals(curr.getMonth()))&&(morning||check||!morning&&!currentMonth.equals(prevMonth)))||(cycleFirstDayDivided&&check) || i + 1 == cyclesTableInfo.size()) {
                boolean nightH=checkHour(curr.getHour(), morningEnd);//return true if night hours
                if (!cycleFirstDayDivided) {//check if first day in evening shift
                    if (i + 1 == cyclesTableInfo.size()) {//if last day
                        if (currentMonth.equals(curr.getMonth())||currentMonth.equals(prevMonth)&&nightH){
                            test=test+curr.getMachineOutputUsage()*curr.getTWork();
                            numberOfSeconds = numberOfSeconds + curr.getTWork();
                        cutCounter = cutCounter + curr.getCutCounter();
                        totalWeight = totalWeight + curr.getTotalWeight();
                    }else{
                            addDataAboutCycle(count,currentMonth,currentYear,morning,numberOfSeconds,cutCounter,totalWeight,test);
                            currentMonth=curr.getMonth();
                            currentYear=curr.getYear();
                            if (!morning&&nightH){//check if is night hours
                                    currentMonth = prevMonth;
                                    currentYear=getYear(prevDay);
                                }
                            test=curr.getMachineOutputUsage()*curr.getTWork();
                            numberOfSeconds = curr.getTWork();
                            cutCounter = curr.getCutCounter();
                            totalWeight = curr.getTotalWeight();
                        }
                    }

                   addDataAboutCycle(count,currentMonth,currentYear,morning,numberOfSeconds,cutCounter,totalWeight,test);
                    count++;
                }
                //initialize the data--we moved to different day or shift
                cycleFirstDayDivided=false;
                test=curr.getMachineOutputUsage()*curr.getTWork();
                numberOfSeconds = curr.getTWork();
                cutCounter = curr.getCutCounter();
                totalWeight = curr.getTotalWeight();
                currentMonth=curr.getMonth();
                currentYear=curr.getYear();
            }
            else if (!cycleFirstDayDivided){
                test=test+curr.getMachineOutputUsage()*curr.getTWork();
                numberOfSeconds = numberOfSeconds + curr.getTWork();
                cutCounter = cutCounter + curr.getCutCounter();
                totalWeight = totalWeight + curr.getTotalWeight();
            }
        }
        if (stopTableInfo.size()>0) {
            currentMonth = stopTableInfo.get(0).getMonth();
            currentYear = stopTableInfo.get(0).getYear();
        }
        int countLoading = 0;
        long timeLoading = 0;
        long timeServes = 0;
        for (int i = 0; i < stopTableInfo.size(); i++) {
            Stop current = stopTableInfo.get(i);
            String prevDay=getPrevDay(current.getDate());
            String prevMonth=getPrevMonth(prevDay);
            boolean check=!morning&&!checkHour(current.getHour(),morningEnd)&&currentMonth.equals(getPrevMonth(getPrevDay(current.getDate())));
            if ((!(currentMonth.equals(current.getMonth()))&&(morning||check||!morning&&!currentMonth.equals(prevMonth)))
                    ||(stopFirstDayDivided&&check)|| i + 1 == stopTableInfo.size()){
                if (!stopFirstDayDivided) {//check if first day in evening shift
                    if (i + 1 == stopTableInfo.size()) {
                        boolean nightH=checkHour(current.getHour(), morningEnd);//return true if night hours
                        if (currentMonth.equals(current.getMonth())||currentMonth.equals(prevMonth)&&nightH) {
                            if (current.getIdleCause() == 2) {
                                countLoading = countLoading + 1;
                                timeLoading = timeLoading + getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                            } else if (current.getIdleCause() == 4)
                                timeServes = timeServes + getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                        }
                        else {
                            count=addDataAboutStop(count,currentMonth,currentYear,morning,timeLoading,countLoading,timeServes);
                            currentMonth=current.getMonth();
                            currentYear=current.getYear();
                            if (!morning&&nightH){
                                        currentMonth = prevMonth;
                                        currentYear=getYear(prevDay);
                                    }
                            count++;
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
                    }
                count=addDataAboutStop(count,currentMonth,currentYear,morning,timeLoading,countLoading,timeServes);
                }
                stopFirstDayDivided=false;
                //initialize the data--we moved to different day or shift
                currentMonth=current.getMonth();
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

    public int getNumberOfMonths(String startDate,String endDate){
        long monthsBetween = ChronoUnit.MONTHS.between(YearMonth.from(LocalDate.parse(startDate)), YearMonth.from(LocalDate.parse(endDate)));
        return (int)monthsBetween+1;
    }
    public int getNumberOfDaysInMonth(String month,String year){
        Integer month1=new Integer(month);
        Integer year1=new Integer(year);
        YearMonth yearMonthObject = YearMonth.of(year1, month1);
       return yearMonthObject.lengthOfMonth();
    }

    public double getEstimatedWorkHourByMonth(String month,String year,boolean morning){
       Integer month1=new Integer(month);
       Integer year1=new Integer(year);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, month1-1);
        c.set(Calendar.YEAR, year1);
        int fr = 0;
        int st = 0;
        int maxDayInMonth =getNumberOfDaysInMonth(month,year);
        for (int d = 1;  d <= maxDayInMonth;  d++) {
            c.set(Calendar.DAY_OF_MONTH, d);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (Calendar.FRIDAY == dayOfWeek) {
               fr++;
            }
            else if (Calendar.SATURDAY==dayOfWeek){
                st++;
            }
        }
        int weekWorkDays=maxDayInMonth-fr-st;
        if (divideByShifts) {
            if (morning)
                return weekWorkDays * workHours + fr * fridayWorkHours;
            else return weekWorkDays*workHours+st*saturdayWorkHours;
        }else   return weekWorkDays*workHours+fr*fridayWorkHours+st*saturdayWorkHours;

    }
    public void addDataAboutCycle(int count,String currentMonth,String currentYear,boolean morning,long numberOfSeconds,int cutCounter,int totalWeight,double test){
        //add all data
        tableData[count][11 + add] =currentMonth;
        tableData[count][10] =getEstimatedWorkHourByMonth(currentMonth,currentYear,morning);
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
    public String getPrevMonth(String currentDate){
        Integer month=new Integer(currentDate.substring(5,7));
        if (month<10)
            return "0"+month;
        else return ""+month;
    }
    public String getYear(String currentDate){
       return currentDate.substring(4);
    }
    public int addDataAboutStop(int count,String currentMonth,String currentYear,boolean morning,long timeLoading,int countLoading,long timeServes) {
        int date;
        //find the date in the table
        if (divideByShifts)
            date = findDateAndShift( currentMonth, morning,12);
        else date = findDate( currentMonth,11);
        if (date == -1) {
            date = count;
            tableData[count][11 + add] = currentMonth;
            tableData[count][10] = getEstimatedWorkHourByMonth(currentMonth,currentYear,morning);
            if (divideByShifts) {
                if (!morning) {
                    tableData[count][11] = "ערב";
                } else tableData[count][11] = "בוקר";

            }
            //add data
            tableData[count][5] = 0;
            tableData[count][4] = 0;
            tableData[count][9] = 0.0;
            count++;
        }
        tableData[date][8] =getHour(timeLoading);
        tableData[date][7] = countLoading;
        tableData[date][6] =getHour(timeServes);
        return count;

    }
    }
