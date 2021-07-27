 
package SQL;
  
import Objects.DBInfo;
import Objects.Shift;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLShifts extends SQLConnection {

    public SQLShifts(DBInfo dbInfo) {
        super(dbInfo);
    }

    public ArrayList<Shift> getDataBase(String dbo) {
        ArrayList<Shift> data = new ArrayList<>();
        try {
            if (!connectedToDb) return null;
            selector = (Statement) connector.createStatement();
            rs = selector.executeQuery("SELECT * FROM " + dbo);
            addToArrayList(rs, data);
        } catch (SQLException queryException) {
            System.out.println("SQLShifts.java - getDataBase() Exception - " + queryException.getMessage());
        }
        return data;
    }

    private void addToArrayList(ResultSet rs, ArrayList<Shift> data) {
        try {
            while (rs.next()) {
                Shift currentRow = new Shift(rs.getString("shiftType"), rs.getString("shiftStart"), rs.getString("shiftEnd"), rs.getString("coffeBreak1"), rs.getString("coffeBreak2"), rs.getString("foodBreak1"));
                if (currentRow.getType() != null)
                    data.add(currentRow);
            }
        } catch (Exception addException) {
            System.out.println("SQLShifts.java - addToArrayList() Exception - " + addException.getMessage());
            addException.printStackTrace();
        }
    }

    public boolean addShift(String dbo, String shiftType, String shiftStart, String shiftEnd, String coffeBreak1, String coffeBreak2, String foodBreak1) {
        try {
            if (!connectedToDb) return false;
            selector = (Statement) connector.createStatement();
            selector.executeUpdate("INSERT INTO " + dbo + "(shiftType ,shiftStart, shiftEnd, coffeBreak1, coffeBreak2, foodBreak1) VALUES (" +
                    "'" + shiftType + "'," + shiftStart + "," + shiftEnd + "," + coffeBreak1 + "," + coffeBreak2 + "," + foodBreak1 + ")");
        } catch (SQLException addMaterialException) {
            System.out.println("SQLShifts.java - addMaterial() Exception - " + addMaterialException.getMessage());
            return false;
        }
        return true;
    }

    public double getWorkHours(String dbo, String hourStart, String hourEnd) {
        double workHours = 0.0;
        System.out.println(hourEnd);
        try {
            if (!connectedToDb) return workHours;
            selector = (Statement) connector.createStatement();
            rs = selector.executeQuery("SELECT workHours FROM " + dbo + " WHERE CAST (shiftStart as time(0) )= '" + hourStart + "' AND cast (shiftEnd as time(0) )= '" + hourEnd + "'");
            while (rs.next()) {
                workHours = rs.getDouble("workHours");
            }
        } catch (SQLException addMaterialException) {
            System.out.println("SQLShifts.java - getWorkHours Exception - " + addMaterialException.getMessage());

        }
        return workHours;
    }

    public double getFridayWorksHours(String dbo) {
        double workHours = 0.0;
        try {
            if (!connectedToDb) return workHours;
            selector = (Statement) connector.createStatement(); 
            rs = selector.executeQuery("SELECT workHours FROM " + dbo + " WHERE shiftType =N'יום שישי'"); 
            while (rs.next()) { 
                workHours = rs.getFloat("workHours"); 
            }
        } catch (SQLException addMaterialException) {
            System.out.println("SQLShifts.java - getWorkHours Exception - " + addMaterialException.getMessage());

        }
        return workHours;
    }
    public double getSaturdayWorksHours(String dbo) {
        double workHours = 0.0;
        try {
            if (!connectedToDb) return workHours;
            selector = (Statement) connector.createStatement();
            rs = selector.executeQuery("SELECT workHours FROM " + dbo + " WHERE shiftType =N'מוצאי שבת'");
            while (rs.next()) {
                workHours = rs.getFloat("workHours");
            }
        } catch (SQLException addMaterialException) {
            System.out.println("SQLShifts.java - getWorkHours Exception - " + addMaterialException.getMessage());

        }
        return workHours;
    }
}
