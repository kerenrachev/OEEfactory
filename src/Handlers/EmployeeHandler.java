 
package Handlers;

import Objects.DBInfo;
import Objects.Employee;
import SQL.SQLEmployee;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

 
public class EmployeeHandler {
    
    private final DefaultTableModel employeeTableModel;
    private final DBInfo            connectionInfo;
    private final String            tableName;
    
    //Used to connect into the employee database
    private SQLEmployee       sqlEmployeeConnector;
    
    public EmployeeHandler(DefaultTableModel shiftsTableModel,DBInfo connectionInfo)
    {
        this.employeeTableModel = shiftsTableModel;
        this.connectionInfo     = connectionInfo;
        this.tableName          = connectionInfo.getTableWorkers();
    } 
    public void addEmploy(int workerId, String workerName){
        if(sqlEmployeeConnector.addEmployee(tableName,workerId,workerName)){ 
        grabEmployeesToTable();
        JOptionPane.showMessageDialog(null,"Success!");
        }else 
        JOptionPane.showMessageDialog(null,"Failed!");
    }
    
    public boolean updateEmployeeId(String name, String newId){
        return sqlEmployeeConnector.changeNumOfEmployee(tableName,name, Integer.parseInt(newId)); 
    }
    public boolean updateEmployeeName(String name, String newName){
        return sqlEmployeeConnector.changeName(tableName,name, newName); 
    }
    public void deleteEmploy(int index)
    {
        String    workerName = (String)employeeTableModel.getValueAt(index,0);
        int       workerId   = (Integer)employeeTableModel.getValueAt(index,1);
        if(workerName.length() > 0  ){
             for (Employee employee : employees) {
                 tempEmployee = employee;
                 if(tempEmployee.getNumOfEmployee() == (workerId) && workerName.equals(tempEmployee.getNameOfEmployee()))
                 {
                     if(sqlEmployeeConnector.deleteEmploye(tableName,workerId)){
                         JOptionPane.showMessageDialog(null,"Success!");
                         grabEmployeesToTable();
                     }else JOptionPane.showMessageDialog(null,"Failed!");
                     break;
                 }
             }
        }
    } 
    /* Writes the employees that are in the SQL database into the table */ 
    private ArrayList<Employee> employees ;
    private Employee            tempEmployee;
    
    public boolean ready(){
          return (sqlEmployeeConnector != null &&  sqlEmployeeConnector.hasConnectedToDb());
    }
    public void connect(){
         sqlEmployeeConnector = new SQLEmployee(connectionInfo);  
    }
    public void grabEmployeesToTable(){
        if(!ready()) connect();
        employeeTableModel.setRowCount(0);  
        employees            = sqlEmployeeConnector.getDataBase(tableName); 
        for (Employee employee : employees) {
            tempEmployee = employee;
            employeeTableModel.addRow(new Object[]  {tempEmployee.getNameOfEmployee(),tempEmployee.getNumOfEmployee()});
        } 
    }
    
}
