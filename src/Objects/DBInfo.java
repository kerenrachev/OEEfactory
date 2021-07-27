 
package Objects;

 
public class DBInfo {
    private String dbIP;
    private String dbPort;
    private String dbName;
    private String dbUser;
    private String dbPass;
    
    private String tableWorkers;
    private String tableStops;
    private String tableMaterials;
    private String tableShifts;
    private String tableMachines;
    private String tableCycles;
    
    private boolean windowsSQLAuth;
    
    public DBInfo(String dbIP,String dbPort,String dbName, String dbUser, String dbPass,String tableMachines,String tableWorkers,String tableStops,String tableMaterials,String tableShifts,String tableCycles,boolean windowsSQLAuth){
        this.dbIP   = dbIP;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        
        this.tableMachines = tableMachines;
        this.tableStops    = tableStops;
        this.tableWorkers  = tableWorkers;
        this.tableMaterials = tableMaterials;
        this.tableShifts    = tableShifts;
        this.tableCycles    = tableCycles;
        this.windowsSQLAuth = windowsSQLAuth;
    }
    
    public String getIP(){
        return dbIP;
    }
    public String getPort(){
        return dbPort;
    }
    public String getDbName(){
        return dbName;
    } 
    public String getUser(){
        return dbUser;
    }
    public String getPass(){
        return dbPass;
    }
    public boolean getIsWindowsAuth(){
        return windowsSQLAuth;
    }
    public void setDBSettings(String dbIP,String dbPort,String dbName, String dbUser, String dbPass,String tableMachines,String tableWorkers,String tableStops,String tableMaterials,String tableShifts,String tableCycles,boolean windowsSQLAuth ){ 
         this.dbIP   = dbIP;
         this.dbPort = dbPort;
         this.dbName = dbName;
         this.dbUser = dbUser;
         this.dbPass = dbPass; 
         this.tableWorkers   = tableWorkers;
         this.tableMachines  = tableMachines;
         this.tableShifts    = tableShifts;
         this.tableStops     = tableStops;
         this.tableMaterials = tableMaterials;
         this.tableCycles    = tableCycles;
         this.windowsSQLAuth = windowsSQLAuth;
    } 
     
    public String getTableCycles(){
        return tableCycles;
    }
    public String getTableWorkers(){
        return tableWorkers;
    }
    public String getTableShifts(){
        return tableShifts;
    }
    public String getTableMaterials(){
        return tableMaterials;
    }
    public String getTableStops(){
        return tableStops;
    }
    public String getTableMachines(){
        return tableMachines;
    }
    
    public void setTableWorkers(String tableWorkers){
        this.tableWorkers = tableWorkers;
    }
    public void setTableShifts(String tableShifts){
        this.tableShifts = tableShifts;
    }
    public void setTableMaterials(String tableMaterials){
        this.tableMaterials = tableMaterials;
    }
    public void setTableStops(String tableStops){
        this.tableStops    = tableStops;
    }
    public void setTableMachines(String tableMachines){
        this.tableMachines = tableMachines;
    }
}
