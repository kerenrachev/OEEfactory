 
package Handlers;
 
import Objects.DBInfo;
import Objects.Misc;
import Objects.Shift;
import SQL.SQLShifts; 
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class ShiftHandler {
    
    private ArrayList<Shift>          shifts             = new ArrayList<>();
    private final ArrayList<Double>   durationsOfBreaks  = new ArrayList<>();
    private final ArrayList<Double>   durationsOfShifts  = new ArrayList<>();
    
    private Shift               tempShift;
     
    private final DefaultTableModel   shiftsTableModel ;
    private       SQLShifts           sqlShiftConnector; 
    private final String              tableName;
    private final DBInfo              connectionInfo;
    
    public ShiftHandler(DefaultTableModel shiftsTableModel,DBInfo connectionInfo)
    {
        this.shiftsTableModel = shiftsTableModel;
        this.connectionInfo   = connectionInfo;
        this.tableName        = connectionInfo.getTableShifts(); 
    } 
    
    public ArrayList<Shift> getShifts(){
        if(!ready()) connect();  
        shifts             = sqlShiftConnector.getDataBase(tableName);   
        return shifts;
    }
    public int getNumberOfShifts(){
        return shifts.size();
    }
    
    public ArrayList<Double> getDurationsOfShifts(){
        return durationsOfShifts;
    }
     
    private double getTotalBreakInHours(String coffeBreak1,String coffeBreak2,String foodBreak1){
        double out = 0.0; 
        out = out + Misc.getDurationInHours(coffeBreak1);
        out = out + Misc.getDurationInHours(coffeBreak2);
        out = out + Misc.getDurationInHours(foodBreak1);  
        return out;
    }
   
    private boolean ready(){
        return (sqlShiftConnector != null &&  sqlShiftConnector.hasConnectedToDb());
    }
    private void connect(){   
        sqlShiftConnector = new SQLShifts(connectionInfo);  
    } 
    
    private void calculateShiftInfo(){
        shifts = getShifts();
        double totalBreaks = 0.0;
        for(int i = 0 ; i < shifts.size(); i++){ 
            tempShift = shifts.get(i);  
            if(tempShift.getType() != null){ 
            totalBreaks = (double)getTotalBreakInHours(tempShift.getCoffeBreak1(),tempShift.getCoffeBreak2(),tempShift.getFoodBreak1()); 
            durationsOfShifts.add((double)Misc.getDurationInHours(tempShift.getShiftStart()+"-"+tempShift.getShiftEnd())-totalBreaks);
            durationsOfBreaks.add(totalBreaks); 
            }
        }
    }
    public void addShiftsIntoTable(){ 
        
        shiftsTableModel.setRowCount(0);  
        calculateShiftInfo(); 
        
        for(int i = 0 ; i < shifts.size(); i++){ 
            tempShift = shifts.get(i);  
            if(tempShift.getType() != null) 
               shiftsTableModel.addRow(new Object[] {tempShift.getShiftStart(),tempShift.getShiftEnd(),tempShift.getCoffeBreak1(),tempShift.getCoffeBreak2(),tempShift.getFoodBreak1(),durationsOfBreaks.get(i),durationsOfShifts.get(i),tempShift.getType()});
        }
    }
}
