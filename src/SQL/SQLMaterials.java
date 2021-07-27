/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;
 
 
import Objects.DBInfo;
import Objects.Material;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList; 

public class SQLMaterials extends SQLConnection{
	
	
    public SQLMaterials(DBInfo dbInfo){
        
        super (dbInfo);
    } 
    public ArrayList<Material> getDataBase(String  dbo) {
        ArrayList<Material> data=new ArrayList<>();
        try{
             if(!connectedToDb) return null;
             selector = (Statement) connector.createStatement();
             rs       = selector.executeQuery("SELECT * FROM "+dbo);
             addToArrayList(rs,data);
        }catch(SQLException queryException)
        {
            System.out.println("SQLMaterials.java - getDataBase() Exception - " + queryException.getMessage());
        }
        return data;
    } 
    private void addToArrayList(ResultSet rs,ArrayList<Material> data) {
        try{
        while (rs.next()){
              Material currentRow=new Material(rs.getFloat("diameterMM"),rs.getFloat("weightMeterPerKilo"),rs.getFloat("numOfWiresFormatS1"),rs.getFloat("numOfWiresFormatS2"),rs.getFloat("numOfWiresFormat14")
            		  ,rs.getFloat("numOfWiresFormat16"),rs.getFloat("MiniSyntax"),rs.getFloat("Planet20"),rs.getFloat("EvgPbc"),rs.getFloat("RaXa"));
              data.add(currentRow);
        }
        }catch(Exception addException){
            System.out.println("SQLMaterials.java - addToArrayList() Exception - " + addException.getMessage());
            addException.printStackTrace();
        }
    }  
    /*
     * Add new material by  
     */
    public boolean addMaterial (String dbo, float diameter,  float weight, float s1,  float s2,  float w14,  float w16,  float syntax,  float planet,  float EP,  float Ra) {
    	try{
            if(!connectedToDb) return false;
            selector = (Statement) connector.createStatement();
            selector.executeUpdate("INSERT INTO "+ dbo +"(diameterMM ,weightMeterperKilo, numOfWiresFormatS1, numOfWiresFormatS2, numOfWiresFormat14, numOfWiresFormat16,"
            		+ "MiniSyntax,Planet20,EvgPbc,RaXa) VALUES ("+
            		"'"+ diameter +"',"+weight +","+s1  +","+s2  +","+w14+","+w16+","+syntax+","+planet+","+EP +","+Ra+")");
       }catch(SQLException addMaterialException)
       {
           System.out.println("SQLMaterials.java - addMaterial() Exception - " + addMaterialException.getMessage());
           return false;
       }
       return true;
    } 
    /*
     * Delete material by diameter
     */
    public boolean deleteMaterial (String dbo , float diameterMM) {
    	try{
            if(!connectedToDb) return false;
            selector = (Statement) connector.createStatement(); 
            selector.executeUpdate("delete from "+ dbo +" where diameterMM =" + diameterMM);
       }catch(SQLException deleteMaterialException)
       {
          
           System.out.println("SQLMaterials.java - deleteMaterial() Exception - " + deleteMaterialException.getMessage());
            return false;
       }
    	   return true;
    } 
    /*Responsible for changing all float elements in material
     * inside "whatToChane" put the name of column you want to change the element in,
     * as shown in " SQL server management studio"
     */
    public boolean changeMachineFloatSetting(String dbo, int id, double newValue, String whatToChange) {
    	try{
            if(!connectedToDb) return false;
            selector = (Statement) connector.createStatement();
            selector.executeUpdate("UPDATE "+ dbo+ " SET "+whatToChange+"="+ newValue+" WHERE IdNum=" +id );
       }catch(SQLException changeMaterialFloatElement)
       {
           System.out.println("SQLMaterials.java - changeMachineFloatSetting() Exception - " + changeMaterialFloatElement.getMessage());
           return false;
       }
    	   return true;
    }  
    /*
    Prints entire database. 
    */
    public void print(ArrayList<Material> data){
        for(int i = 0 ; i < data.size() ; i++){
            data.get(i).printMaterial();
        }
    }
}
