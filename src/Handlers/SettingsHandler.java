 
package Handlers;
 
import Objects.DBInfo;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths; 
import java.nio.file.StandardOpenOption;
import java.util.List;

 
public class SettingsHandler implements Runnable{
    private final String            filePath;
    private List<String>            fileLines; 
    private File                    fileHandle;
    
    private boolean hasLoadedSettings = true; 
    
    private String dbIP;
    private String dbPort;
    private String dbName;
    private String dbUser;
    private String dbPass;
    
    private DBInfo dbInfo;
    
    private String tableMachines;
    private String tableWorkers;
    private String tableStops;
    private String tableMaterials;
    private String tableShifts;
    private String tableCycles;
    
    private boolean windowsSQLAuth = false;
    
    public SettingsHandler(String filePath, DBInfo dbInfo){
        this.filePath = filePath;
        this.dbInfo   = dbInfo; 
        new Thread(this).start();
    }
    public void saveFile(){
        try{ 
               Files.write(Paths.get(filePath), ("SqlServerParser Config|\n" +"-----------------------\n" +"\n" +"DBIP="+dbInfo.getIP()+"\n" +"DBPort="+dbInfo.getPort()+"\n" +"DBName="+dbInfo.getDbName()+"\n" +"DBUsername="+dbInfo.getUser()+"\n" +"DBPassword="+dbInfo.getPass()+"\n" +"TableWorkers="+dbInfo.getTableWorkers()+"\n" +"TableMachines="+dbInfo.getTableMachines()+"\n" +"TableStops="+dbInfo.getTableStops()+"\n" +"TableMaterials="+dbInfo.getTableMaterials()+"\n" +"TableCycles="+dbInfo.getTableCycles()+"\n"  +"TableShifts="+dbInfo.getTableShifts()).getBytes(),StandardOpenOption.CREATE);
        }catch(Exception saveFileException){
            saveFileException.printStackTrace();
        }
    }   
    @Override
    public void run() {
       try{
           fileHandle = new File(filePath);
           if(fileHandle.exists()){ 
              fileLines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
              for(int i = 0 ; i < fileLines.size(); i++){
                String data = fileLines.get(i);
                if(data.contains("DBIP="))
                    dbIP   = data.split("=")[1];
                else if(data.contains("DBPort="))
                    dbPort = data.split("DBPort=")[1];
                else if(data.contains("DBName="))
                    dbName = data.split("DBName=")[1];
                    else if(data.contains("DBUsername="))
                    dbUser = data.split("DBUsername=")[1];
                else if(data.contains("DBPassword="))
                    dbPass = data.split("DBPassword=")[1]; 
                else if(data.contains("TableWorkers="))
                    tableWorkers = data.split("TableWorkers=")[1];
                else if(data.contains("TableMachines="))
                    tableMachines = data.split("TableMachines=")[1];
                else if(data.contains("TableStops="))
                    tableStops    = data.split("TableStops=")[1];
                else if(data.contains("TableMaterials="))
                    tableMaterials = data.split("TableMaterials=")[1];
                else if(data.contains("TableShifts="))
                    tableShifts    = data.split("TableShifts=")[1];
                else if(data.contains("TableCycles="))
                    tableCycles    = data.split("TableCycles=")[1];
                else if(data.contains("WindowsAuth=")){
                    String temp = data.split("=")[1];
                    if(temp.equals("true")) windowsSQLAuth = true; else windowsSQLAuth = false;
                }
              } 
           }else
              fileHandle.createNewFile();
           
       } catch(IOException loadSettingsException){
           System.out.println("SettingsHandler.java run1() Exception - " + loadSettingsException.getMessage());
           loadSettingsException.printStackTrace();
           hasLoadedSettings = false;
       }
       if(fileLines == null || fileLines.size() == 0)
           hasLoadedSettings = false; 
       try{
       if(!hasLoadedSettings){
          Files.write(Paths.get(filePath), ("SqlServerParser Config|\n" +
           "-----------------------\n" +
           "\n" +
          "DBIP=localhost\n" +
          "DBPort=1433\n" +
          "DBName=OEE\n" +
          "DBUsername=admin\n" +
          "DBPassword=admin\n" +
          "TableWorkers=employees\n" +
          "TableMachines=machines\n" +
          "TableStops=stopInfo\n" +
          "TableMaterials=materials\n" +
          "TableShifts=shifts\n"+ "WindowsAuth=false" ).getBytes(),StandardOpenOption.CREATE);
          
          dbIP    = "localhost";
          dbPort  = "1433";
          dbUser  = "admin";
          dbPass  = "admin"; 
          dbName  = "OEE";
          tableWorkers   = "employees";
          tableMachines  = "machines";
          tableShifts    = "shifts";
          tableStops     = "stopInfo";
          tableMaterials = "materials"; 
       } 
       }catch(IOException writeFileException){
           System.out.println("SettingsHandler.java run2() Exception - " + writeFileException.getMessage());
           writeFileException.printStackTrace();
       } 
       dbInfo.setDBSettings(dbIP, dbPort,dbName, dbUser, dbPass,tableMachines,tableWorkers,tableStops,tableMaterials,tableShifts,tableCycles,windowsSQLAuth); 
    }
}
