package SQL;
 
import Objects.DBInfo;
import Objects.Machine;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList; 
 

public class SQLMachine extends SQLConnection {
	
	
    public SQLMachine(DBInfo dbInfo){ 
        super (dbInfo);
    } 
    
    public ArrayList<Machine> getDataBase(String  dbo) {
        ArrayList<Machine> data=new ArrayList<>();
        try{
             if(!connectedToDb) return null;
             selector = (Statement) connector.createStatement();
             rs       = selector.executeQuery("SELECT * FROM "+dbo);
            addToArrayList(rs,data);
        }catch(SQLException queryException)
        {
            System.out.println("SQLMachine.java - getDataBase() Exception - " + queryException.getMessage());
        }
        return data;
    }
     
    private void addToArrayList(ResultSet rs,ArrayList<Machine> data) {
        try{
        while (rs.next()){
              Machine currentRow = new Machine(rs.getInt("IdNumber"),rs.getInt("machineNum"),rs.getString("machineName"),rs.getInt("SpeedMeterPerSec"),rs.getFloat("CutTimeSec"), rs.getFloat("BendingTimeSec"),rs.getInt("ChargingTimeSec"));
              data.add(currentRow);
        }
        }catch(Exception addException){
            System.out.println("SQLMachine.java - addToArrayList() Exception - " + addException.getMessage());
            addException.printStackTrace();
        }
    }
    
    
 
    public ArrayList<String> getMachineNames(String dbo){
    	ArrayList <String> currentMachineTypes = new ArrayList();
    	try {
    		if(!connectedToDb) return null;
                        selector = (Statement) connector.createStatement();
			rs       = selector.executeQuery("SELECT * FROM "+dbo );
	
			while(rs.next()) 
				currentMachineTypes.add(rs.getString("machineName")); 
		} catch (SQLException e) {
			System.out.println("SQLMachine.java - getMachineTypes() adding to array Exception - " + e.getMessage());
		}
    	return currentMachineTypes;
    }
    
    /*
     * Delete machine by IdNumber
     */
    public boolean deleteMachine (String dbo , int machineNum) {
    	try{
            if(!connectedToDb) return false;
            selector = (Statement) connector.createStatement();
            selector.executeUpdate("delete from "+ dbo +" where machineNum =" + machineNum);
       }catch(SQLException deleteMachineException)
       {
           System.out.println("SQLMachine.java - deleteMachine() Exception - " + deleteMachineException.getMessage());
           return false;
       }
    	   return true;
    }
    
    /*
     * Add new machine by IdNumber
     */
    public boolean addMachine (String dbo , String name, int machineNum, int speed, double cut, double bend, int charge) {
    	try{
            if(!connectedToDb) return false;
            selector = (Statement) connector.createStatement();
            selector.executeUpdate("INSERT INTO "+ dbo +"(machineName ,machineNum, SpeedMeterPerSec, CutTimeSec, BendingTimeSec, ChargingTimeSec) VALUES ("+
            		"'"+ name +"',"+machineNum +","+speed  +","+cut  +","+bend+","+charge+")");
       }catch(SQLException addMachineException)
       {
           System.out.println("SQLMachine.java - addMachine() Exception - " + addMachineException.getMessage());
           return false;
       }
    	    return true;
    } 
    public boolean changeMachineFloatSetting(String dbo, String machineName, String newValue, String whatToChange) {
    	try{
            if(!connectedToDb) return false;
            selector = (Statement) connector.createStatement(); 
            selector.executeUpdate("UPDATE "+ dbo+ " SET "+whatToChange+"='"+ newValue+"' WHERE machineName='" +machineName+"'" );
       }catch(SQLException changMachineName)
       {
           System.out.println("SQLMachine.java - changeMachineFloatSetting() Exception - " + changMachineName.getMessage());
           return false;
       }
    	   return true;
    }
    /*
    Prints entire database. 
    */
    public void print(ArrayList<Machine> data){
        for(int i = 0 ; i < data.size() ; i++){
            data.get(i).printMachine();
        }
    } 
}
