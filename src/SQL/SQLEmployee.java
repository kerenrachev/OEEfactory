package SQL;
 
import Objects.DBInfo;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Objects.Employee;  
import java.sql.SQLException; 

public class SQLEmployee extends SQLConnection {
	
    private ArrayList<Employee> data;
    public SQLEmployee(DBInfo dbInfo){ 
        super (dbInfo);
    }
    public ArrayList<Employee> getDataBase(String  dbo) {
        data=new ArrayList<>();
        try{
             if(!connectedToDb) return null;
             selector = (Statement) connector.createStatement();
             rs       = selector.executeQuery("SELECT * FROM "+dbo);
            addToArrayList(rs,data);
        }catch(SQLException queryException)
        {
            System.out.println("SQLEmployee.java - getDataBase() Exception - " + queryException.getMessage());
        }
        return data;
    }
    
    private void addToArrayList(ResultSet rs,ArrayList<Employee> data) {
        try{
        while (rs.next()){
              Employee currentRow=new Employee(rs.getInt("id"),rs.getInt("numOfEmployee"),rs.getString("nameOfEmployee"));
              data.add(currentRow);
        }
        }catch(SQLException addException){
            System.out.println("SQLEmployee.java - addToArrayList() Exception - " + addException.getMessage());
            addException.printStackTrace();
        }
    }
    public boolean deleteEmploye (String dbo , int numOfEmployee) {
    	try{
            if(!connectedToDb) return false;
            selector = (Statement) connector.createStatement();
            selector.executeUpdate("delete from "+ dbo +" where numOfEmployee =" + numOfEmployee);
       }catch(SQLException deleteWorkerException)
       {
           System.out.println("SQLEmployee.java - deleteEmploye() Exception - " + deleteWorkerException.getMessage());
       }
    	   return true;
    }
    public boolean addEmployee (String dbo , int numOfWorker, String name) {
    	try{
            if(!connectedToDb) return false;
            selector = (Statement) connector.createStatement();
            selector.executeUpdate("INSERT INTO "+ dbo +"(numOfEmployee,nameOfEmployee) VALUES ("+
            		numOfWorker+","+
            		"N'"+name+"')"); 
       }catch(SQLException addEmployeException)
       {
           System.out.println("SQLEmployee.java - addEmployee() Exception - " + addEmployeException.getMessage());
       }
    	   return true;
    }
    public boolean changeName(String dbo, String nameOfEmployee, String newName) {
    	try{
            if(!connectedToDb) return false;
            selector = (Statement) connector.createStatement();
            selector.executeUpdate("UPDATE "+ dbo+ " SET nameOfEmployee='"+newName+"' WHERE nameOfEmployee='" +nameOfEmployee+"'" );
       }catch(SQLException changEmployeesName)
       {
           System.out.println("SQLEmployee.java - changeName() Exception - " + changEmployeesName.getMessage());
       }
    	   return true;
    }
    public boolean changeNumOfEmployee(String dbo, String nameOfEmployee,int newEmployeeNum) {
    	try{
            if(!connectedToDb) return false;
            selector = (Statement) connector.createStatement(); 
            selector.executeUpdate("UPDATE "+ dbo+ " SET numOfEmployee='"+newEmployeeNum+"' WHERE nameOfEmployee='" +nameOfEmployee+"'");
       }catch(SQLException addEmployeException)
       {
           System.out.println("SQLEmployee.java - changeNumOfEmployee() Exception - " + addEmployeException.getMessage());
       }
    	  return true;
    }   
    /*
    Prints entire database. 
    */
    public void print(ArrayList<Employee> data){
        for(int i = 0 ; i < data.size() ; i++){
            data.get(i).printEmployeesTable();
        }
    }  
}
