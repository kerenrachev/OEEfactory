package Objects;
 

public class Employee { 
    private int    id;
    private int    numOfEmployee;
    private String nameOfEmployee;
    
    
    //constructor for employee rows
    public Employee (int id, int num, String name) {
    	this.id=id;
    	this.numOfEmployee=num;
    	this.nameOfEmployee=name;
    }
    public int getId(){
    	return id;
    }
    public int getNumOfEmployee() {
    	return numOfEmployee;
    }
    public String getNameOfEmployee() {
    	return nameOfEmployee;
    }  
    public void printEmployeesTable(){
        System.out.println(" "+id+" "+numOfEmployee+" "+nameOfEmployee ); 
    }
}
