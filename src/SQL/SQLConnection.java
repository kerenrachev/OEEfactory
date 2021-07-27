package SQL;

import Objects.DBInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

public class SQLConnection  {

        protected String      dbServerIp;
        protected String      dbServerPort;
	protected String      dbUsername; 
	protected String      dbPassword;
	protected String      dbName;
	    
	protected Connection  connector;
	protected Statement   selector;
	protected ResultSet   rs;
	    
	protected boolean     connectedToDb      = false;
	protected boolean     finishedConnecting = false; 
        
        protected boolean     isWindowsSqlAuth   = false;
	    
	    /*
	    Constructor
	    dbServerIp   - sql server ip
	    dbServerPort - sql server port
	    dbName       - sql server database name
	    dbUsername   - sql server database login username
	    dbPassword   - sql server database login password
	    */ 
	    public SQLConnection(DBInfo dbInfo){
	        this.dbServerIp   = dbInfo.getIP();
	        this.dbServerPort = dbInfo.getPort();
	        this.dbName       = dbInfo.getDbName(); 
                isWindowsSqlAuth  = dbInfo.getIsWindowsAuth(); 
                this.dbUsername   = dbInfo.getUser();
	        this.dbPassword   = dbInfo.getPass();
	        
	        connect();
	    } 
	    public boolean hasConnectedToDb(){
	        return connectedToDb;
	    }
	    public boolean finishedConnection(){
	        return finishedConnecting;
	    }  
	    /* Connects to the database. */
	    private void connect()
	    {
	        try{
	             DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	             DriverManager.setLoginTimeout(10);
	             //add sqljdbc_auth.dll in your C:/windows/System32 folder.
	             String connectionUrl = "";
                     if(!isWindowsSqlAuth)
                         connectionUrl = "jdbc:sqlserver://"+dbServerIp+":"+dbServerPort+";databaseName="+dbName+";user="+dbUsername+";password="+dbPassword+";"; 
                     else
                         connectionUrl = "jdbc:sqlserver://"+dbServerIp+":"+dbServerPort+";databaseName="+dbName+";user="+dbUsername+";integratedSecurity=true;";
	             connector     = DriverManager.getConnection(connectionUrl); 
	             connectedToDb = true;
	        }catch(SQLException connectionException){
	            System.out.println("SQLConnection.java - connect() Exception - " + connectionException.getMessage());
	            connectionException.printStackTrace();
	            connectedToDb = false;
	        }
	        finishedConnecting = true;
	    }     
            
	    
}
