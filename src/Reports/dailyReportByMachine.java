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

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;  

public class dailyReportByMachine extends abstractReportByMachine {
    private int numberOfDays;
    int rowNumber = 0;
    int add = 0; 
    
    public dailyReportByMachine(DBInfo dbInfo,String machineID, String startDate, String endDate, String startHour, String endHour, double workHours,double morningWorkHours,double eveningWorkHours, boolean divideByShifts,String morningStart
            ,String morningEnd,String eveningStart,String eveningEnd ,double fridayWorkHours,double saturdayWorkHours,int typeReport) throws ParseException {
        
        super(dbInfo,machineID, startDate, endDate, startHour, endHour, workHours,morningWorkHours,eveningWorkHours,divideByShifts,morningStart,morningEnd,eveningStart,eveningEnd,fridayWorkHours,saturdayWorkHours,typeReport);
        numberOfDays = getNumberOfDays(startDate, endDate);
        int numberOfColumn = 13;
        if (divideByShifts) {//if is divided by shifts get morning report
            numberOfDays = numberOfDays * 2;
            numberOfColumn = 14;
            isMorning = true;
            add = 1;
        }
        tableData = new Object[numberOfDays][numberOfColumn];
        String[] divideByShiftColumn = {"יעילות מכונה %","ניצול תפוקת מכונה %", "ניצול זמן מכונה %", "תפוקת טון לשעה", "משקל כללי KG ", "מס יחידות", "זמן שירות שעות", "מספר הטענות יחידות", "זמן הטענות שעות", "מכונה בעבודה שעות", "זמינות מכונה שעות", "משמרת", "יום בשבוע", "תאריך"};
        String[] column = {"יעילות מכונה %","ניצול תפוקת מכונה %", "ניצול זמן מכונה %", "תפוקת טון לשעה", "משקל כללי ", "מס יחידות", "זמן שירות שעות", "מספר הטענות יחידות", "זמן הטענות שעות", "מכונה בעבודה שעות", "זמינות מכונה שעות", "יום בשבוע", "תאריך"};
        analyseData( isMorning);
        if (divideByShifts) {//get evening report
            cyclesTableInfo = cycle.getDataByDateAndHoursAndMachineID(dbInfo.getTableCycles() , startDate, endDate, eveningStart, eveningEnd, machineID);
            stopTableInfo   = stop.getDataByDateAndHoursMachineID    (dbInfo.getTableStops(), startDate, endDate, eveningStart, eveningEnd, machineID);
            this.workHours=eveningWorkHours;
            analyseData(false);

        }
        if (divideByShifts)
            tableModel = new DefaultTableModel(tableData, divideByShiftColumn);
        else tableModel = new DefaultTableModel(tableData, column);
        //remove empty rows from table
        deleteEmptyRows(numberOfDays, tableData, tableModel, 9, add);
        table = new JTable(tableModel);
        JScrollPane jScrollPane = new JScrollPane(table);
        table.setAutoCreateRowSorter(true);
        setLayout(new BorderLayout());
        add(jScrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    } 
    //public static void main(String[] args) throws ParseException {
    //    JFrame frame = new JFrame();
    //    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    dailyReportByMachine report = new dailyReportByMachine("33", "2020-08-16", "2020-12-20", "00:10", "00:15", 22, true);
    //    frame.setContentPane(report);
     //   frame.setSize(400, 400);
     //   frame.setVisible(true);
//

   // }

    //get number of days between specific range
    public int getNumberOfDays(String startDate, String endDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(startDate, dtf);
        LocalDate date2 = LocalDate.parse(endDate, dtf);
        long daysBetween = ChronoUnit.DAYS.between(date1, date2);
        return (int) daysBetween + 1;
    }
 
    //return day number of the week
    public String getDayNumber(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(date, dtf);
        DayOfWeek day = date1.getDayOfWeek();
        int dayValue = day.getValue();
        if (dayValue == 1)
            return "ב";
        else if (dayValue == 2)
            return "ג";
        else if (dayValue == 3)
            return "ד";
        else if (dayValue == 4)
            return "ה";
        else if (dayValue == 5)
            return "ו";
        else if (dayValue == 6)
            return "ש";
        else return "א";
    }


    /*analyse the Cycle and stop data by
    -if the shift is a morning  shift only by different dates
    -if the shift is evening shift by different dates and by hours
    meaning night hours will be saved to the prev date- done by the checkHour function
    in addition night hours of the first day will not be saved-done by a boolean check and checkHour function

     */
    public void analyseData( boolean isMorning) throws ParseException {
        currentDate = startDate;
        if (cyclesTableInfo.size()>0) {
            currentDate = cyclesTableInfo.get(0).getDate();
        }
        long numberOfSeconds = 0;
        int cutCounter = 0;
        int totalWeight = 0;
        double test=0;
        int count = rowNumber;
        boolean morning = isMorning;
        boolean cycleFirstDayDivided = false;
        boolean stopFirstDayDivided = false;
        if (divideByShifts||!morning) {
            if (cyclesTableInfo.size() > 0 && !morning)
                cycleFirstDayDivided = checkHour(cyclesTableInfo.get(0).getHour(), morningEnd)&&cyclesTableInfo.get(0).getDate().equals(startDate);
            if (stopTableInfo.size() > 0 && !morning)
                stopFirstDayDivided = checkHour(stopTableInfo.get(0).getHour(), morningEnd)&&cyclesTableInfo.get(0).getDate().equals(startDate);
        }
        for (int i = 0; i < cyclesTableInfo.size(); i++) {
            Cycle curr = cyclesTableInfo.get(i);
            boolean check=!morning && !checkHour(curr.getHour(), morningEnd);
            System.out.println(curr.getStartCycle());
            if ((!(currentDate.equals(curr.getDate())) && (morning || check ||!morning&&!currentDate.equals(getPrevDay(curr.getDate())))) || (cycleFirstDayDivided && check) || i + 1 == cyclesTableInfo.size()) {
                boolean nightH=checkHour(curr.getHour(), morningEnd);//return true if night hours
                String prevDate=getPrevDay(curr.getDate());
                if (!cycleFirstDayDivided) {//check if first day in evening shift
                    if (i + 1 == cyclesTableInfo.size()) {//if last day
                        if (currentDate.equals(curr.getDate())||(currentDate.equals(prevDate)&&nightH)) {
                            test=test+curr.getMachineOutputUsage()*curr.getTWork();
                            numberOfSeconds = numberOfSeconds + curr.getTWork();
                            cutCounter = cutCounter + curr.getCutCounter();
                            totalWeight = totalWeight + curr.getTotalWeight();
                        }
                        else {
                            addDataAboutCycle(count,currentDate,morning,numberOfSeconds,cutCounter,totalWeight,test);
                            currentDate = curr.getDate();
                            if (!morning&&nightH)
                                 currentDate=prevDate;
                            test=curr.getMachineOutputUsage()*curr.getTWork();
                            numberOfSeconds = curr.getTWork();
                            cutCounter = curr.getCutCounter();
                            totalWeight = curr.getTotalWeight();
                            count++;
                        }
                    }
                    //add all data
                      addDataAboutCycle(count,currentDate,morning,numberOfSeconds,cutCounter,totalWeight,test);
                        count++;

                }
                //initialize the data--we moved to different day or shift
                cycleFirstDayDivided = false;
                test=curr.getMachineOutputUsage()*curr.getTWork();
                numberOfSeconds = curr.getTWork();
                cutCounter = curr.getCutCounter();
                totalWeight = curr.getTotalWeight();
                currentDate = curr.getDate();
                if (!morning&&nightH){
                    currentDate=prevDate;
                }
            }
                else if (!cycleFirstDayDivided) {
                test=test+curr.getMachineOutputUsage()*curr.getTWork();
                numberOfSeconds = numberOfSeconds + curr.getTWork();
                cutCounter = cutCounter + curr.getCutCounter();
                totalWeight = totalWeight + curr.getTotalWeight();
            }
        }
        currentDate = startDate;
        if (stopTableInfo.size()>0) {
            currentDate = stopTableInfo.get(0).getDate();
        }
        int countLoading = 0;
        long timeLoading = 0;
        long timeServes = 0;
        for (int i = 0; i < stopTableInfo.size(); i++) {
            Stop current = stopTableInfo.get(i);
            boolean check=!morning && !checkHour(current.getHour(), morningEnd);
            if (!currentDate.equals(current.getDate()) && (morning || check||!morning&&!currentDate.equals(getPrevDay(current.getDate())))
                    || (stopFirstDayDivided && check) || i + 1 == stopTableInfo.size()) {
                boolean nightH=checkHour(current.getHour(), morningEnd);//return true if night hours
                String prevDate=getPrevDay(current.getDate());
                if (!stopFirstDayDivided) {//check if first day in evening shift
                    if (i + 1 == stopTableInfo.size()) {
                        if (currentDate.equals(current.getDate()) || (currentDate.equals(prevDate)&&nightH)){
                            if (current.getIdleCause() == 2) {
                                countLoading = countLoading + 1;
                                timeLoading = timeLoading + getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                            } else if (current.getIdleCause() == 4)
                                timeServes = timeServes + getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                        } else {
                           count= addDataAboutStop(count,currentDate,morning,timeLoading,countLoading,timeServes);
                            currentDate = current.getDate();
                            if (!morning&&nightH)
                              currentDate=prevDate;
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
                  count=addDataAboutStop(count,currentDate,morning,timeLoading,countLoading,timeServes);
                } stopFirstDayDivided = false;
                //initialize the data--we moved to different day or shift
                currentDate = current.getDate();
                if (!morning&&nightH) {
                    currentDate = prevDate;
                }
                timeLoading = 0;
                countLoading = 0;
                timeServes = 0;
                if (current.getIdleCause() == 2) {
                    countLoading = 1;
                    timeLoading = getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());

                } else if (current.getIdleCause() == 4) {
                    timeServes = getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                }
            } else if (!stopFirstDayDivided) {
                if (current.getIdleCause() == 2) {
                    countLoading = countLoading + 1;
                    timeLoading = timeLoading + getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
                } else if (current.getIdleCause() == 4)
                    timeServes = timeServes + getNumberOfSeconds(current.getStartIdle(), current.getEndIdle());
            }
        }
        rowNumber = count;

    }


    public double getWorkHours(String day) {
        if (day.equals("ו")){
            if (fridayWorkHours<workHours)
            return fridayWorkHours;
        }
        else if (day.equals("ש")) {
            if (saturdayWorkHours<workHours)
            return saturdayWorkHours;
        }
         return workHours;
    }
    public void addDataAboutCycle(int count,String currentDate,boolean morning,long numberOfSeconds,int cutCounter,int totalWeight,double test){
        //add all data
        String day = getDayNumber(currentDate);
        tableData[count][12 + add] = currentDate;
        tableData[count][11 + add] = day;
        tableData[count][10] = getWorkHours(day);
        if (divideByShifts) {
            if (!morning) {
                tableData[count][11] = "ערב";
            } else tableData[count][11] = "בוקר";

        }
        tableData[count][9] = getHour(numberOfSeconds);
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
    public int  addDataAboutStop(int count,String currentDate,boolean morning,long timeLoading,int countLoading,long timeServes){
        int date;
        //find the date in the table
        if (divideByShifts)
            date = findDateAndShift(currentDate, morning, 13);
        else date = findDate( currentDate, 12);
        if (date == -1) {
            if (timeLoading!=0||countLoading!=0||timeServes!=0) {
                date = count;
                tableData[count][12 + add] = currentDate;
                String day = getDayNumber(currentDate);
                tableData[count][11 + add] = day;
                tableData[count][10] = getWorkHours(day);
                if (divideByShifts) {
                    if (!morning) {
                        tableData[count][11] = "ערב";
                    } else tableData[count][11] = "בוקר";
                }


                //add data
                tableData[count][4] = 0;
                tableData[count][5] = 0;
                tableData[count][9] = 0.0;
                tableData[count][1] = 0;
                tableData[count][2]=0;

                count++;
            }else return count;
        }
        tableData[date][8] = getHour(timeLoading);
        tableData[date][7] = countLoading;
        tableData[date][6] = getHour(timeServes);
        return count;
    }
}