 
package GUI;
  
import Handlers.EmployeeHandler;
import Handlers.MachinesHandler;
import Handlers.MaterialsHandler; 

import Handlers.SettingsHandler;
import Handlers.ShiftHandler;

import Objects.DBInfo; 
import Objects.Misc;
import Objects.Shift; 

import java.awt.ComponentOrientation;
import java.awt.Toolkit;   
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat; 

import java.util.ArrayList;
import java.util.Date; 

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane; 
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class MainForm extends javax.swing.JFrame  implements ActionListener{
  
    /*
    Used db names when there is no config file
    */
    
    private final String                 cyclesTableName          = "Cycles";
    private final String                 workersTableName         = "employees";
    private final String                 machinesTableName        = "machines";
    private final String                 materialsTableName       = "materials";
    private final String                 shiftsTableName          = "shifts"; 
    private final String                 stopsTableName           = "stopInfo";
    
    //Object that holds the following database information : dbIP,dbPort,dbName,dbUser,dbPass
    private DBInfo                       databaseInfo = new DBInfo("localhost","1433","OEE","admin","admin",machinesTableName,workersTableName,stopsTableName,materialsTableName,shiftsTableName,cyclesTableName,false) ;
    
    
    //Display Form of setting db info
    private DBSettingsForm         dbSettingsForm; 
    //Settings Object
    private SettingsHandler        settings;
    //File name for settings
    private final String           settingsFileName = "Config.txt";
     
      
    //Holds all labels that shall have hovering effect
    private ArrayList<JLabel>      labelsToDoHoverEffectOn = new ArrayList<>();
    
    private ShiftHandler           shiftHandler;
    private EmployeeHandler        employeeHandler;
    private MachinesHandler        machinesHandler;
    private MaterialsHandler       materialsHandler;
    
        
    private DefaultTableModel      workersSetTableModel;
    private DefaultTableModel      machinesSetTableModel;
    private DefaultTableModel      materialsSetTableModel;  
    private DefaultTableModel      shiftsSetTableModel; 
    
    
    
    private SimpleDateFormat       reportDateFormatter = new SimpleDateFormat("yyyy-MM-dd");    
    
    //Report Vars
    private Date                   dateStart;
    private Date                   dateEnd; 
    private String                 dateStartReport    = "";
    private String                 dateEndReport      = "";
    private String                 hourStartReport    = "";
    private String                 hourEndReport      = "";  
    private boolean                splitIntoTwoShifts = false;
    
    
    //machineName for Machine Report
    private String                 machineName = "";
    
    
    private JPopupMenu workerPopup;
    private JPopupMenu machinePopup;
    
    //Popup actions
    @Override
    public void actionPerformed(ActionEvent event) {
        JMenuItem menuPressed = (JMenuItem) event.getSource();
        String textOfPressed = menuPressed.getText();
        if(textOfPressed.equals("שנה מספר עובד")){
            int selectedIndexWorkers = workersTable.getSelectedRow();
            
            String input = JOptionPane.showInputDialog(null,"בחר מספר עבור עובד");
            String name  = (String)workersTable.getValueAt(selectedIndexWorkers,0);
            
            if(input.length() > 0){
            if(employeeHandler.updateEmployeeId(name,input)){
                workersTable.setValueAt(input,selectedIndexWorkers,1); 
                JOptionPane.showMessageDialog(null,"Success!");
            }else
                JOptionPane.showMessageDialog(null,"Failed"); 
            }
        }else
        if(textOfPressed.equals("שנה שם עובד")){ 
           int selectedIndexWorkers = workersTable.getSelectedRow();
            
            String input = JOptionPane.showInputDialog(null,"בחר שם חדש עבור עובד");
            String oldWorkerName  = (String)workersTable.getValueAt(selectedIndexWorkers,0);
            
            if(input.length() > 0){
            if(employeeHandler.updateEmployeeName(oldWorkerName,input)){
                workersTable.setValueAt(input,selectedIndexWorkers,0); 
                JOptionPane.showMessageDialog(null,"Success!");
            }else
                JOptionPane.showMessageDialog(null,"Failed"); 
            }
        }else
        if(textOfPressed.equals("שנה שם מכונה")){ 
           int selectedIndexMachines = machinesTable.getSelectedRow();
         
           String input = JOptionPane.showInputDialog(null,"בחר שם חדש עבור עובד");
           String oldMachineName  = (String)machinesTable.getValueAt(selectedIndexMachines,5);
           
           if(input.length() > 0){
               if(machinesHandler.updateMachineName(oldMachineName,input)){
                  machinesTable.setValueAt(input,selectedIndexMachines,5); 
                  JOptionPane.showMessageDialog(null,"Success!");  
               }else
                    JOptionPane.showMessageDialog(null,"Failed!");
           }
        }else
        if(textOfPressed.equals("שנה זמן טעינה")){ 
           int selectedIndexMachines = machinesTable.getSelectedRow();
         
           String input = JOptionPane.showInputDialog(null,"בחר זמן טעינה");
           String machineName  = (String)machinesTable.getValueAt(selectedIndexMachines,5);
           
           if(machineName.length() > 0){
               if(machinesHandler.updateMachineLoadingTime(machineName,input)){
                  machinesTable.setValueAt(input,selectedIndexMachines,4); 
                  JOptionPane.showMessageDialog(null,"Success!");  
               }else
                    JOptionPane.showMessageDialog(null,"Failed!");
           }
        }else
           if(textOfPressed.equals("שנה זמן כיפוף")){ 
           int selectedIndexMachines = machinesTable.getSelectedRow();
         
           String input = JOptionPane.showInputDialog(null,"בחר זמן כיפוף");
           String machineName  = (String)machinesTable.getValueAt(selectedIndexMachines,5);
           
           if(machineName.length() > 0){
               if(machinesHandler.updateMachineBendingTime(machineName,input)){
                  machinesTable.setValueAt(input,selectedIndexMachines,3); 
                  JOptionPane.showMessageDialog(null,"Success!");  
               }else
                    JOptionPane.showMessageDialog(null,"Failed!");
           }
           }else
           if(textOfPressed.equals("שנה זמן חיתוך")){ 
           int selectedIndexMachines = machinesTable.getSelectedRow();
         
           String input = JOptionPane.showInputDialog(null,"בחר זמן חיתוך");
           String machineName  = (String)machinesTable.getValueAt(selectedIndexMachines,5);
           
           if(machineName.length() > 0){
               if(machinesHandler.updateMachineCutTime(machineName,input)){
                  machinesTable.setValueAt(input,selectedIndexMachines,2); 
                  JOptionPane.showMessageDialog(null,"Success!");  
               }else
                    JOptionPane.showMessageDialog(null,"Failed!");
           }
           }else
           if(textOfPressed.equals("שנה מהירות")){ 
           int selectedIndexMachines = machinesTable.getSelectedRow();
         
           String input = JOptionPane.showInputDialog(null,"בחר מהירות");
           String machineName  = (String)machinesTable.getValueAt(selectedIndexMachines,5);
           
           if(machineName.length() > 0){
               if(machinesHandler.updateMachineSpeed(machineName,input)){
                  machinesTable.setValueAt(input,selectedIndexMachines,1); 
                  JOptionPane.showMessageDialog(null,"Success!");  
               }else
                    JOptionPane.showMessageDialog(null,"Failed!");
           }
           }else
           if(textOfPressed.equals("שנה מספר מכונה")){ 
           int selectedIndexMachines = machinesTable.getSelectedRow(); 
           String input        = JOptionPane.showInputDialog(null,"בחר מספר מכונה");
           String machineName  = (String)machinesTable.getValueAt(selectedIndexMachines,5);
           
           if(machineName.length() > 0){
               if(machinesHandler.updateMachineNum(machineName,input)){
                  machinesTable.setValueAt(input,selectedIndexMachines,0); 
                  JOptionPane.showMessageDialog(null,"Success!");  
               }else
                    JOptionPane.showMessageDialog(null,"Failed!");
           }
           }
        
    }
    public MainForm() {
        initComponents       ();
        
        workerPopup = new JPopupMenu();
        JMenuItem changeIdItem = new JMenuItem("שנה מספר עובד");
        JMenuItem changeNameItem = new JMenuItem("שנה שם עובד"); 
        
        workerPopup.add(changeIdItem);
        workerPopup.add(changeNameItem);
        
        workersTable.setComponentPopupMenu(workerPopup);  
        
        machinePopup = new JPopupMenu();
        JMenuItem changeNameMachineItem = new JMenuItem("שנה שם מכונה");
        JMenuItem changeLoadingTimeMachineItem = new JMenuItem("שנה זמן טעינה");
        JMenuItem changeBendingTimeMachineItem = new JMenuItem("שנה זמן כיפוף");
        JMenuItem changeCuttingTimeMachineItem = new JMenuItem("שנה זמן חיתוך");
        JMenuItem changeSpeedTimeMachineItem   = new JMenuItem("שנה מהירות");
        JMenuItem changeNumMachineItem   = new JMenuItem("שנה מספר מכונה");
        
        machinePopup.add(changeBendingTimeMachineItem);
        machinePopup.add(changeCuttingTimeMachineItem);
        machinePopup.add(changeLoadingTimeMachineItem);
        machinePopup.add(changeSpeedTimeMachineItem);
        machinePopup.add(changeNumMachineItem);
        machinePopup.add(changeNameMachineItem);
         
        changeIdItem.addActionListener(this);
        changeNameItem.addActionListener(this); 
        changeNameMachineItem.addActionListener(this);
        changeLoadingTimeMachineItem.addActionListener(this);
        changeBendingTimeMachineItem.addActionListener(this);
        changeCuttingTimeMachineItem.addActionListener(this);
        changeSpeedTimeMachineItem.addActionListener(this);
        changeNumMachineItem.addActionListener(this);
         
        machinesTable.setComponentPopupMenu(machinePopup);
        
        setLocationRelativeTo(null);
        setTitle("Rachev - SQL Analysis Tool");  
        //Makes the menu text go from the left side to the right side
        menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
        //Icon of the program
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icons/icons8_sql_48px.png")));
        setResizable(false);    
         
        showSettingsPopup (false);
        showReportsPopup  (false); 
        hideCenteredPanels(); 
        /*Fetch saved settings from a file - settings such as : db ip, db name, db user, db pass etc.. */
        settings = new SettingsHandler(settingsFileName,databaseInfo);  
        
        /*Used for hovering effect over jlabels*/
        labelsToDoHoverEffectOn.add(jLabel1);
        labelsToDoHoverEffectOn.add(titlePanel);
        labelsToDoHoverEffectOn.add(jLabel3);
        labelsToDoHoverEffectOn.add(jLabel4);
        labelsToDoHoverEffectOn.add(jLabel5);
        labelsToDoHoverEffectOn.add(jLabel6);
        labelsToDoHoverEffectOn.add(jLabel7);
        labelsToDoHoverEffectOn.add(jLabel8);
        labelsToDoHoverEffectOn.add(jLabel10); 
        labelsToDoHoverEffectOn.add(jLabel13);
        labelsToDoHoverEffectOn.add(jLabel14); 
        labelsToDoHoverEffectOn.add(jLabel15);
        labelsToDoHoverEffectOn.add(removeWorkerLabel1);
        labelsToDoHoverEffectOn.add(jLabel17);
        labelsToDoHoverEffectOn.add(removeWorkerLabel);
        labelsToDoHoverEffectOn.add(jLabel18);
        labelsToDoHoverEffectOn.add(reportsPanelLabel); 
        labelsToDoHoverEffectOn.add(settingsPanelLabel); 
        labelsToDoHoverEffectOn.add(jLabel27);
        labelsToDoHoverEffectOn.add(jLabel28);
        labelsToDoHoverEffectOn.add(jLabel22);  
        
        labelsHoverEffect labelEffects = new  labelsHoverEffect(labelsToDoHoverEffectOn); 
        labelEffects.start(); 
        
        //Table Models 
        workersSetTableModel      = (DefaultTableModel) workersTable  .getModel();
        machinesSetTableModel     = (DefaultTableModel) machinesTable .getModel();
        materialsSetTableModel    = (DefaultTableModel) materialsTable.getModel(); 
        shiftsSetTableModel       = (DefaultTableModel) shiftsTable   .getModel();
       
        
        machinesTable  .setRowHeight(20);
        workersTable   .setRowHeight(20);
        materialsTable .setRowHeight(20);
        shiftsTable    .setRowHeight(20);
        
        enableShiftPickInReportByMachine(false);  
        
        //Handlers for the settings 
        shiftHandler     = new ShiftHandler   (shiftsSetTableModel    ,databaseInfo );
        employeeHandler  = new EmployeeHandler(workersSetTableModel   ,databaseInfo );
        machinesHandler  = new MachinesHandler(machinesSetTableModel  ,databaseInfo );
        materialsHandler = new MaterialsHandler(materialsSetTableModel,databaseInfo );
         
        //Default for report by machine
        jMonthChooser1.setVisible(false);
        jMonthChooser2.setVisible(false);
        jYearChooser1 .setVisible(false);
        jYearChooser2 .setVisible(false);   
        this.pack();
    }  
    
    //Displays reports popup panels
    public void showReportsPopup(boolean enable){
        if(enable) hideCenteredPanels();
        MachineEfficRepChoosePanel.setVisible(enable);
        MachineMalfRepChoosePanel .setVisible(enable);
        MachineRepChoosePanel     .setVisible(enable);
        MalfunctionRepChoosePanel .setVisible(enable);
        rawMatRepChoosePanel      .setVisible(enable);
        WorkerEfficRepChoosePanel .setVisible(enable);
        WorkerRepChoosePanel      .setVisible(enable);
        UtilRepChoosePanel        .setVisible(enable);
    } 
    //Displays settings popup panels
     public void showSettingsPopup(boolean enable){
        if(enable) hideCenteredPanels();
        shiftSettingsChoosePanel  .setVisible(enable);
        workerSettingsChoosePanel .setVisible(enable);
        matChooseSettingsPanel .setVisible(enable);  
        machineChooseSettingsPanel.setVisible(enable);
    } 
     //Hides all centered based panels
     public void hideCenteredPanels(){
         machineReportPanel.setVisible(false);
         workersSetPanel   .setVisible(false); 
         machinesSetPanel  .setVisible(false);
         materialsSetPanel .setVisible(false);
         shiftsSetPanel    .setVisible(false);
     } 
     public void enableShiftPickInReportByMachine(boolean show){
         jLabel42   .setVisible(show);
         jComboBox1 .setVisible(show);
         jLabel49   .setVisible(show);
         jComboBox2 .setVisible(show); 
         jTextField8.setVisible(!show);
         jTextField3.setVisible(!show);
         jLabel2    .setVisible(!show);
         jLabel9    .setVisible(!show);
     } 
     /* Used to indicate when the reports popup was closed. (true = closed) */
     private boolean showReportsPopup = true;  
     public void resetReportPopupVars(){
         showReportsPopup(false);
         showReportsPopup = true;   
     }
      /* Used to indicate when the settings popup was closed. (true = closed) */
     private boolean showSettingsPopup = true; 
     public void resetSettingsPopupVars(){
        showSettingsPopup(false);
        showSettingsPopup = true;   
    } 
     //For daily range
    public void enableFullDateChooseInReportByMachine(boolean show){
         dateLabel1   .setVisible(show);
         jDateChooser2.setVisible(show); 
         jDateChooser1.setVisible(show);
         dateLabel    .setVisible(show); 
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        reportsPanel = new javax.swing.JPanel();
        reportsPanelLabel = new javax.swing.JLabel();
        settingsPanel = new javax.swing.JPanel();
        settingsPanelLabel = new javax.swing.JLabel();
        machineReportPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        machineNameLabel = new javax.swing.JLabel();
        reportMachineTitle = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        dateLabel1 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        machinePickComBox = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jTextField3 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel49 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel50 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jYearChooser2 = new com.toedter.calendar.JYearChooser();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jMonthChooser2 = new com.toedter.calendar.JMonthChooser();
        breakInHoursLabel = new javax.swing.JLabel();
        breakInputTextField = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        shiftSettingsChoosePanel = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        workerSettingsChoosePanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        matChooseSettingsPanel = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        machineChooseSettingsPanel = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        workersSetPanel = new javax.swing.JPanel();
        workerSetTitle = new javax.swing.JLabel();
        removeWorkerLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        workersTable = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        numWorkerTextField = new javax.swing.JTextField();
        nameWorkerTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        machinesSetPanel = new javax.swing.JPanel();
        workerSetTitle1 = new javax.swing.JLabel();
        removeWorkerLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        machinesTable = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        materialsSetPanel = new javax.swing.JPanel();
        workerSetTitle2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        materialsTable = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        shiftsSetPanel = new javax.swing.JPanel();
        workerSetTitle3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        shiftsTable = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        MachineRepChoosePanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JLabel();
        WorkerRepChoosePanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        UtilRepChoosePanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        MachineEfficRepChoosePanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        WorkerEfficRepChoosePanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        MachineMalfRepChoosePanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        MalfunctionRepChoosePanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        rawMatRepChoosePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        backgroundPanel = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        databaseSettingsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reportsPanel.setBackground(new java.awt.Color(0, 0, 102));
        reportsPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        reportsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportsPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reportsPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reportsPanelMouseExited(evt);
            }
        });
        reportsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reportsPanelLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        reportsPanelLabel.setForeground(new java.awt.Color(255, 255, 255));
        reportsPanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reportsPanelLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_report_card_32px.png"))); // NOI18N
        reportsPanelLabel.setText("דוחות");
        reportsPanelLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportsPanelLabelMouseClicked(evt);
            }
        });
        reportsPanel.add(reportsPanelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 60));

        mainPanel.add(reportsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 20, 370, 60));

        settingsPanel.setBackground(new java.awt.Color(0, 0, 102));
        settingsPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        settingsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                settingsPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                settingsPanelMouseExited(evt);
            }
        });
        settingsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        settingsPanelLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        settingsPanelLabel.setForeground(new java.awt.Color(255, 255, 255));
        settingsPanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        settingsPanelLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_options_32px.png"))); // NOI18N
        settingsPanelLabel.setText("הגדרות");
        settingsPanelLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsPanelLabelMouseClicked(evt);
            }
        });
        settingsPanel.add(settingsPanelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 60));

        mainPanel.add(settingsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 380, 60));

        machineReportPanel.setBackground(new java.awt.Color(242, 242, 242));
        machineReportPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        machineReportPanel.setLayout(null);
        machineReportPanel.add(jSeparator1);
        jSeparator1.setBounds(0, 40, 930, 10);

        machineNameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        machineNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        machineNameLabel.setText("שם מכונה");
        machineReportPanel.add(machineNameLabel);
        machineNameLabel.setBounds(675, 120, 130, 30);

        reportMachineTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        reportMachineTitle.setText("דוח לפי מכונה");
        machineReportPanel.add(reportMachineTitle);
        reportMachineTitle.setBounds(360, -5, 170, 50);

        dateLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dateLabel.setText("עד");
        machineReportPanel.add(dateLabel);
        dateLabel.setBounds(460, 230, 60, 30);
        machineReportPanel.add(jDateChooser1);
        jDateChooser1.setBounds(290, 230, 130, 30);

        dateLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dateLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateLabel1.setText("מתאריך");
        machineReportPanel.add(dateLabel1);
        dateLabel1.setBounds(675, 230, 130, 20);
        machineReportPanel.add(jDateChooser2);
        jDateChooser2.setBounds(530, 230, 140, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("עד");
        machineReportPanel.add(jLabel2);
        jLabel2.setBounds(460, 290, 30, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("משעה");
        machineReportPanel.add(jLabel9);
        jLabel9.setBounds(675, 290, 130, 20);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_cnc_machine_16px.png"))); // NOI18N
        jLabel10.setText("צור דוח");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
        });
        machineReportPanel.add(jLabel10);
        jLabel10.setBounds(30, 500, 90, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("חתך");
        machineReportPanel.add(jLabel11);
        jLabel11.setBounds(675, 180, 130, 20);

        machinePickComBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machineReportPanel.add(machinePickComBox);
        machinePickComBox.setBounds(550, 120, 120, 30);

        jComboBox3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "יומי", "בוקר \\ ערב" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jComboBox3InputMethodTextChanged(evt);
            }
        });
        machineReportPanel.add(jComboBox3);
        jComboBox3.setBounds(530, 180, 140, 30);

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("12:30");
        machineReportPanel.add(jTextField3);
        jTextField3.setBounds(290, 290, 130, 30);

        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField8.setText("06:30");
        machineReportPanel.add(jTextField8);
        jTextField8.setBounds(530, 290, 140, 30);

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("משמרת בוקר");
        machineReportPanel.add(jLabel42);
        jLabel42.setBounds(675, 290, 130, 20);

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machineReportPanel.add(jComboBox1);
        jComboBox1.setBounds(420, 360, 240, 30);

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel49.setText("משמרת ערב");
        machineReportPanel.add(jLabel49);
        jLabel49.setBounds(675, 360, 130, 20);

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machineReportPanel.add(jComboBox2);
        jComboBox2.setBounds(420, 290, 240, 30);

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel50.setText("סוג דוח");
        machineReportPanel.add(jLabel50);
        jLabel50.setBounds(440, 177, 80, 30);

        jComboBox4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "יומי", "חודשי", "שנתי" }));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });
        machineReportPanel.add(jComboBox4);
        jComboBox4.setBounds(290, 180, 130, 30);
        machineReportPanel.add(jYearChooser1);
        jYearChooser1.setBounds(580, 230, 90, 30);
        machineReportPanel.add(jYearChooser2);
        jYearChooser2.setBounds(360, 230, 90, 30);
        machineReportPanel.add(jMonthChooser1);
        jMonthChooser1.setBounds(470, 230, 140, 30);
        machineReportPanel.add(jMonthChooser2);
        jMonthChooser2.setBounds(240, 230, 140, 30);

        breakInHoursLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        breakInHoursLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        breakInHoursLabel.setText("סה\"כ הפסקה");
        machineReportPanel.add(breakInHoursLabel);
        breakInHoursLabel.setBounds(675, 360, 130, 20);

        breakInputTextField.setText("0.0");
        machineReportPanel.add(breakInputTextField);
        breakInputTextField.setBounds(530, 360, 140, 30);

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/machine.png"))); // NOI18N
        machineReportPanel.add(jLabel33);
        jLabel33.setBounds(80, 310, 160, 170);

        jCheckBox1.setText("כל היום");
        jCheckBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox1MouseClicked(evt);
            }
        });
        machineReportPanel.add(jCheckBox1);
        jCheckBox1.setBounds(180, 290, 93, 30);

        mainPanel.add(machineReportPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 830, 540));

        shiftSettingsChoosePanel.setBackground(new java.awt.Color(237, 237, 237));
        shiftSettingsChoosePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        shiftSettingsChoosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("הגדרות משמרת");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        shiftSettingsChoosePanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 110, 20));

        mainPanel.add(shiftSettingsChoosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 380, 40));

        workerSettingsChoosePanel.setBackground(new java.awt.Color(102, 102, 102));
        workerSettingsChoosePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        workerSettingsChoosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("רשימת עובדים");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        workerSettingsChoosePanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 110, 20));

        mainPanel.add(workerSettingsChoosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 380, 40));

        matChooseSettingsPanel.setBackground(new java.awt.Color(237, 237, 237));
        matChooseSettingsPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        matChooseSettingsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("תכונות חומר גלם");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        matChooseSettingsPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 120, 20));

        mainPanel.add(matChooseSettingsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 380, 40));

        machineChooseSettingsPanel.setBackground(new java.awt.Color(102, 102, 102));
        machineChooseSettingsPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        machineChooseSettingsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("הגדרת מכונות");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });
        machineChooseSettingsPanel.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 120, 20));

        mainPanel.add(machineChooseSettingsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 380, 40));

        workersSetPanel.setBackground(new java.awt.Color(242, 242, 242));
        workersSetPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        workersSetPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        workerSetTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        workerSetTitle.setText("השמת עובדים");
        workersSetPanel.add(workerSetTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 190, 30));

        removeWorkerLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        removeWorkerLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_delete_16px.png"))); // NOI18N
        removeWorkerLabel.setText("מחק עובד");
        removeWorkerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeWorkerLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeWorkerLabelMouseEntered(evt);
            }
        });
        workersSetPanel.add(removeWorkerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 342, 100, 20));

        workersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "שם עובד", "מספר עובד"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(workersTable);

        workersSetPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 930, 270));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("מספר עובד");
        workersSetPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 340, 80, 20));
        workersSetPanel.add(numWorkerTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 340, 90, -1));
        workersSetPanel.add(nameWorkerTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 340, 90, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("שם עובד");
        workersSetPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 330, 70, 40));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_add_16px.png"))); // NOI18N
        jLabel18.setText("הוסף עובד");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel18MouseEntered(evt);
            }
        });
        workersSetPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 340, 100, 20));

        mainPanel.add(workersSetPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 950, 380));

        machinesSetPanel.setBackground(new java.awt.Color(242, 242, 242));
        machinesSetPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        machinesSetPanel.setMinimumSize(new java.awt.Dimension(1600, 400));
        machinesSetPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        workerSetTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        workerSetTitle1.setText("השמת מכונות");
        machinesSetPanel.add(workerSetTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 0, 190, 40));

        removeWorkerLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        removeWorkerLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_delete_16px.png"))); // NOI18N
        removeWorkerLabel1.setText("מחק מכונה");
        removeWorkerLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeWorkerLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeWorkerLabel1MouseEntered(evt);
            }
        });
        machinesSetPanel.add(removeWorkerLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 100, 20));

        machinesTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machinesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "מספר מכונה", "מהירות", "זמן חיתוך", "זמן כיפוף", "זמן טעינה", "שם מכונה"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(machinesTable);

        machinesSetPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 920, 270));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_add_16px.png"))); // NOI18N
        jLabel17.setText("הוסף מכונה");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        machinesSetPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, 110, 20));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("זמן כיפוף");
        machinesSetPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 380, 70, 20));

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machinesSetPanel.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, 70, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("מספר מכונה");
        machinesSetPanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 350, 100, -1));

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machinesSetPanel.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 380, 70, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("זמן טעינה");
        machinesSetPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 70, 20));

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machinesSetPanel.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 380, 70, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("זמן חיתוך");
        machinesSetPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 380, 100, -1));

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machinesSetPanel.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 350, 70, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("מהירות");
        machinesSetPanel.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 350, 60, 20));

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machinesSetPanel.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 350, 70, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("שם מכונה");
        machinesSetPanel.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 320, 100, -1));

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machinesSetPanel.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 320, 70, -1));

        mainPanel.add(machinesSetPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 150, 950, 430));

        materialsSetPanel.setBackground(new java.awt.Color(242, 242, 242));
        materialsSetPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        materialsSetPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        workerSetTitle2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        workerSetTitle2.setText("השמת חומרי גלם");
        materialsSetPanel.add(workerSetTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 190, 40));

        materialsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "diameterMM", "weightMeter", "numOfWiresS1", "numOfWiresS2", "numOfWires14", "numOfWires16", "MiniSyntax", "Planet20", "EvgPbc", "RaXa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(materialsTable);
        if (materialsTable.getColumnModel().getColumnCount() > 0) {
            materialsTable.getColumnModel().getColumn(1).setPreferredWidth(70);
            materialsTable.getColumnModel().getColumn(2).setPreferredWidth(90);
            materialsTable.getColumnModel().getColumn(3).setPreferredWidth(90);
            materialsTable.getColumnModel().getColumn(4).setPreferredWidth(90);
            materialsTable.getColumnModel().getColumn(5).setPreferredWidth(90);
            materialsTable.getColumnModel().getColumn(7).setPreferredWidth(50);
            materialsTable.getColumnModel().getColumn(8).setPreferredWidth(50);
            materialsTable.getColumnModel().getColumn(9).setPreferredWidth(50);
        }

        materialsSetPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 920, 280));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_add_16px.png"))); // NOI18N
        jLabel22.setText("הוסף חומר גלם");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        materialsSetPanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 330, -1, 20));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_delete_16px.png"))); // NOI18N
        jLabel28.setText("מחק חומר גלם");
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });
        materialsSetPanel.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 330, 130, 20));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("numOfWiresFormatS2");
        materialsSetPanel.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, -1, 20));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("weightMeterPerKilo");
        materialsSetPanel.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 120, 20));

        jTextField11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, 70, -1));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("numOfWiresFormatS1");
        materialsSetPanel.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 360, -1, 20));

        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 330, 70, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setText("diameterMM");
        materialsSetPanel.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 330, -1, 20));

        jTextField47.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jTextField47, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 70, -1));

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 320, -1, 20));

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel80.setText("numOfWiresFormat16");
        materialsSetPanel.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 390, -1, 20));

        jTextField27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jTextField27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 70, -1));

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 320, -1, 20));

        jTextField32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jTextField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 70, -1));

        jTextField33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jTextField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 70, -1));

        jTextField34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jTextField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 70, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("numOfWiresFormat14");
        materialsSetPanel.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 390, -1, 20));

        jTextField14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 390, 70, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setText("miniSyntax");
        materialsSetPanel.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 420, -1, 20));

        jTextField15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 420, 70, -1));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("planet20");
        materialsSetPanel.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, -1, 20));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel38.setText("Ra-Xa20/6");
        materialsSetPanel.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, -1, 20));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel39.setText("EvgPbc");
        materialsSetPanel.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 450, -1, 20));

        jTextField19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsSetPanel.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 450, 70, -1));

        mainPanel.add(materialsSetPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 960, 490));

        shiftsSetPanel.setBackground(new java.awt.Color(242, 242, 242));
        shiftsSetPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        shiftsSetPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        workerSetTitle3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        workerSetTitle3.setText("השמת משמרות");
        shiftsSetPanel.add(workerSetTitle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 180, 40));

        shiftsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "התחלה", "סוף", "קפה1", "קפה2", "אוכל", "זמן הפסקה", "זמן עבודה", "סוג משמרת"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(shiftsTable);
        if (shiftsTable.getColumnModel().getColumnCount() > 0) {
            shiftsTable.getColumnModel().getColumn(7).setPreferredWidth(150);
        }

        shiftsSetPanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 970, 290));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        shiftsSetPanel.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 320, -1, 20));

        mainPanel.add(shiftsSetPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 990, 340));

        MachineRepChoosePanel.setBackground(new java.awt.Color(237, 237, 237));
        MachineRepChoosePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        MachineRepChoosePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MachineRepChoosePanelMouseClicked(evt);
            }
        });
        MachineRepChoosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titlePanel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        titlePanel.setText("דוח עבור מכונה");
        titlePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                titlePanelMouseEntered(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                titlePanelMouseClicked(evt);
            }
        });
        MachineRepChoosePanel.add(titlePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 110, 20));

        mainPanel.add(MachineRepChoosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 80, 370, 40));

        WorkerRepChoosePanel.setBackground(new java.awt.Color(102, 102, 102));
        WorkerRepChoosePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        WorkerRepChoosePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                WorkerRepChoosePanelMouseClicked(evt);
            }
        });
        WorkerRepChoosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("דוח עבור עובד");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        WorkerRepChoosePanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 10, -1, 20));

        mainPanel.add(WorkerRepChoosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 120, 370, 40));

        UtilRepChoosePanel.setBackground(new java.awt.Color(237, 237, 237));
        UtilRepChoosePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        UtilRepChoosePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UtilRepChoosePanelMouseClicked(evt);
            }
        });
        UtilRepChoosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setBackground(new java.awt.Color(237, 237, 237));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("דוחות ניצול מכונה כללי");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
        });
        UtilRepChoosePanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, 20));

        mainPanel.add(UtilRepChoosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 160, 370, 40));

        MachineEfficRepChoosePanel.setBackground(new java.awt.Color(102, 102, 102));
        MachineEfficRepChoosePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        MachineEfficRepChoosePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MachineEfficRepChoosePanelMouseClicked(evt);
            }
        });
        MachineEfficRepChoosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setBackground(new java.awt.Color(102, 102, 102));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("דוחות יעילות מכונה כללי");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
        });
        MachineEfficRepChoosePanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, 20));

        mainPanel.add(MachineEfficRepChoosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 200, 370, 40));

        WorkerEfficRepChoosePanel.setBackground(new java.awt.Color(237, 237, 237));
        WorkerEfficRepChoosePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        WorkerEfficRepChoosePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                WorkerEfficRepChoosePanelMouseClicked(evt);
            }
        });
        WorkerEfficRepChoosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("דוחות יעילות עובדים כללי");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        WorkerEfficRepChoosePanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 10, -1, 20));

        mainPanel.add(WorkerEfficRepChoosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 240, 370, 40));

        MachineMalfRepChoosePanel.setBackground(new java.awt.Color(102, 102, 102));
        MachineMalfRepChoosePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        MachineMalfRepChoosePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MachineMalfRepChoosePanelMouseClicked(evt);
            }
        });
        MachineMalfRepChoosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("דוח תקלות מכונה");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        MachineMalfRepChoosePanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, 20));

        mainPanel.add(MachineMalfRepChoosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 280, 370, 40));

        MalfunctionRepChoosePanel.setBackground(new java.awt.Color(237, 237, 237));
        MalfunctionRepChoosePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        MalfunctionRepChoosePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MalfunctionRepChoosePanelMouseClicked(evt);
            }
        });
        MalfunctionRepChoosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("דוח תקלות תקופתי");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        MalfunctionRepChoosePanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 10, -1, 20));

        mainPanel.add(MalfunctionRepChoosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 320, 370, 40));

        rawMatRepChoosePanel.setBackground(new java.awt.Color(102, 102, 102));
        rawMatRepChoosePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        rawMatRepChoosePanel.setForeground(new java.awt.Color(255, 255, 255));
        rawMatRepChoosePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rawMatRepChoosePanelMouseClicked(evt);
            }
        });
        rawMatRepChoosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("דוח חמר גלם תקופתי");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        rawMatRepChoosePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, 20));

        mainPanel.add(rawMatRepChoosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(547, 360, 370, 40));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/programBackround.jpg"))); // NOI18N
        jLabel19.setText("jLabel19");

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 1181, Short.MAX_VALUE)
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(backgroundPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 0, 1050, 800));

        menuBar.setBackground(new java.awt.Color(0, 0, 204));
        menuBar.setForeground(new java.awt.Color(0, 51, 204));

        fileMenu.setText("כללי");

        databaseSettingsMenuItem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        databaseSettingsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_database_16px.png"))); // NOI18N
        databaseSettingsMenuItem.setText("הגדרת מאגר נתונים");
        databaseSettingsMenuItem.setPreferredSize(new java.awt.Dimension(173, 30));
        databaseSettingsMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                databaseSettingsMenuItemMousePressed(evt);
            }
        });
        databaseSettingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                databaseSettingsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(databaseSettingsMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reportsPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportsPanelMouseEntered
          
    }//GEN-LAST:event_reportsPanelMouseEntered

    private void reportsPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportsPanelMouseExited
        
    }//GEN-LAST:event_reportsPanelMouseExited

    private void settingsPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsPanelMouseEntered
      
    }//GEN-LAST:event_settingsPanelMouseEntered

    private void settingsPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsPanelMouseExited
     
    }//GEN-LAST:event_settingsPanelMouseExited

    private void databaseSettingsMenuItemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_databaseSettingsMenuItemMousePressed
        dbSettingsForm = new DBSettingsForm(settings,databaseInfo);
        dbSettingsForm.setVisible(true);
    }//GEN-LAST:event_databaseSettingsMenuItemMousePressed

    private void titlePanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titlePanelMouseEntered
 
    }//GEN-LAST:event_titlePanelMouseEntered

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
  
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
 
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
 
    }//GEN-LAST:event_jLabel5MouseEntered
 
    private void reportsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportsPanelMouseClicked
       
    }//GEN-LAST:event_reportsPanelMouseClicked

    private void reportsPanelLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportsPanelLabelMouseClicked
        showReportsPopup(showReportsPopup);
        showSettingsPopup(false);
        showReportsPopup = !showReportsPopup;  
        showSettingsPopup = true;
    }//GEN-LAST:event_reportsPanelLabelMouseClicked

    private void titlePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titlePanelMouseClicked
        resetReportPopupVars(); 
        machineReportPanel.setVisible(true); 
        machinePickComBox .removeAllItems();  
        ArrayList<String>     availableMachinesToChooseFrom = machinesHandler.getMachineNames();
        for(int i = 0; i < availableMachinesToChooseFrom.size(); i ++) 
            machinePickComBox.addItem(availableMachinesToChooseFrom.get(i));
    }//GEN-LAST:event_titlePanelMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
         resetReportPopupVars();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void MachineRepChoosePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MachineRepChoosePanelMouseClicked
      
    }//GEN-LAST:event_MachineRepChoosePanelMouseClicked

    private void WorkerRepChoosePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WorkerRepChoosePanelMouseClicked
        
    }//GEN-LAST:event_WorkerRepChoosePanelMouseClicked

    private void UtilRepChoosePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UtilRepChoosePanelMouseClicked
     
    }//GEN-LAST:event_UtilRepChoosePanelMouseClicked

    private void MachineEfficRepChoosePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MachineEfficRepChoosePanelMouseClicked
       
    }//GEN-LAST:event_MachineEfficRepChoosePanelMouseClicked

    private void WorkerEfficRepChoosePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WorkerEfficRepChoosePanelMouseClicked
       
    }//GEN-LAST:event_WorkerEfficRepChoosePanelMouseClicked

    private void MachineMalfRepChoosePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MachineMalfRepChoosePanelMouseClicked
        
    }//GEN-LAST:event_MachineMalfRepChoosePanelMouseClicked

    private void MalfunctionRepChoosePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MalfunctionRepChoosePanelMouseClicked
      
    }//GEN-LAST:event_MalfunctionRepChoosePanelMouseClicked

    private void rawMatRepChoosePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rawMatRepChoosePanelMouseClicked
 
    }//GEN-LAST:event_rawMatRepChoosePanelMouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
       resetReportPopupVars();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
       resetReportPopupVars();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
       resetReportPopupVars();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
       resetReportPopupVars();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
       resetReportPopupVars();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
       resetReportPopupVars();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered

    }//GEN-LAST:event_jLabel10MouseEntered
   
    private void settingsPanelLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsPanelLabelMouseClicked
      showReportsPopup (false);  
      showSettingsPopup(showSettingsPopup);
      showSettingsPopup   =! showSettingsPopup;   
      showReportsPopup    = true; 
    }//GEN-LAST:event_settingsPanelLabelMouseClicked

    private void removeWorkerLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeWorkerLabelMouseEntered

    }//GEN-LAST:event_removeWorkerLabelMouseEntered
   
    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        resetSettingsPopupVars();
        workersSetPanel.setVisible(true);  
        showSettingsPopup = true; 
        employeeHandler.grabEmployeesToTable();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void settingsPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsPanelMouseClicked
        resetSettingsPopupVars();
    }//GEN-LAST:event_settingsPanelMouseClicked

    private void jLabel18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseEntered
        resetSettingsPopupVars();
    }//GEN-LAST:event_jLabel18MouseEntered

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        String    workerName = nameWorkerTextField.getText(); 
        String    workerId   = numWorkerTextField .getText();
        if(workerName.length() > 0  && workerId.length() > 0)  employeeHandler.addEmploy(Integer.parseInt(workerId), workerName);
    }//GEN-LAST:event_jLabel18MouseClicked

    private void removeWorkerLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeWorkerLabelMouseClicked
       employeeHandler.deleteEmploy(workersTable.getSelectedRow());
    }//GEN-LAST:event_removeWorkerLabelMouseClicked

    private void removeWorkerLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeWorkerLabel1MouseClicked
       machinesHandler.removeMachine(machinesTable.getSelectedRow());
    }//GEN-LAST:event_removeWorkerLabel1MouseClicked

    private void removeWorkerLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeWorkerLabel1MouseEntered
       
    }//GEN-LAST:event_removeWorkerLabel1MouseEntered
   
    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        machinesSetPanel.setVisible(true); 
        showSettingsPopup(false);
        showSettingsPopup = true;
        machinesHandler.grabMachinesToTable();
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        try{
        String name;
        int    machineNum;
        int    speed;
        double cut;
        double bend;
        int    charge; 
        name       = jTextField7.getText();
        machineNum = Integer.parseInt  (jTextField5.getText());
        speed      = Integer.parseInt  (jTextField6.getText()); 
        charge     = Integer.parseInt  (jTextField1.getText()); 
        cut        = Double.parseDouble(jTextField4.getText());
        bend       = Double.parseDouble(jTextField2.getText());  
        machinesHandler    .addMachine(name,machineNum,speed,charge,cut,bend); 
        }catch(Exception addException){
            JOptionPane.showMessageDialog(null,"MainForm.java - FAILED_ADDING_MACHINE "+addException.getMessage());
        }
    }//GEN-LAST:event_jLabel17MouseClicked
  
    
    private boolean           isMachineReportDaily             = true; //should be daily report?
    private boolean           isMachineReportMonthly           = false;//should be monthly report?
    private boolean           isMachineReportYearly            = false;//should be yearly report?
    private ReportFormMachine machineReport; //machinereportGUI
    private int               yearStart;
    private int               yearEnd;
    private int               monthStart;
    private int               monthEnd;
    
    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
       machineName = (String)machinePickComBox.getSelectedItem();
       
        int id = machinesHandler.getIdByMachineName(machineName);    
        if(machineName.length() > 0){
        if(jComboBox3.getSelectedItem().equals("בוקר \\ ערב"))
            splitIntoTwoShifts = true;
        else
            splitIntoTwoShifts = false;  
        String reportMachineType = (String)jComboBox4.getSelectedItem();
        if(reportMachineType.equals("יומי")){
            isMachineReportDaily   = true;
            isMachineReportMonthly = false;
            isMachineReportYearly  = false;
        }else
        if(reportMachineType.equals("חודשי")){
            isMachineReportDaily   = false;
            isMachineReportMonthly = true;
            isMachineReportYearly  = false;
        }else
        if(reportMachineType.equals("שנתי")){
            isMachineReportDaily   = false;
            isMachineReportMonthly = false;
            isMachineReportYearly  = true;
        }
        hourStartReport = jTextField8.getText();
        hourEndReport   = jTextField3.getText();
       double duration     = Misc.getDurationInHours(hourStartReport+"-"+hourEndReport);
       double breakInHours;
        if (breakInputTextField.isEnabled())
        breakInHours = Double.parseDouble(breakInputTextField.getText());
       else{
        breakInHours=2;
       duration=24;
        }
        if(isMachineReportDaily){
        dateStart = jDateChooser2.getDate();
        dateEnd   = jDateChooser1.getDate(); 
        if(dateStart != null && dateEnd != null){ 
        dateStartReport =  reportDateFormatter.format(dateStart);
        dateEndReport   =  reportDateFormatter.format(dateEnd);
        if(splitIntoTwoShifts)  
        machineReport = new ReportFormMachine(databaseInfo,1,machineName+"-"+id,dateStartReport,dateEndReport,hourStartReport,hourEndReport,splitIntoTwoShifts,duration-breakInHours,(String)jComboBox2.getSelectedItem(),(String)jComboBox1.getSelectedItem(),"","","","",shiftsTableName);
        else
        machineReport = new ReportFormMachine(databaseInfo,1,machineName+"-"+id,dateStartReport,dateEndReport,hourStartReport,hourEndReport,splitIntoTwoShifts,duration-breakInHours,"","","","","","",shiftsTableName);
        machineReport.setVisible(true);
        }
        } else
        if(isMachineReportYearly){
          yearStart  = jYearChooser1.getYear();
          yearEnd    = jYearChooser2.getYear();
         
          dateStartReport = ""+yearStart;
          dateEndReport   = ""+yearEnd;
          hourStartReport = jTextField8.getText();
          hourEndReport   = jTextField3.getText();
         if (splitIntoTwoShifts)
          machineReport = new ReportFormMachine(databaseInfo,2,machineName+"-"+id,dateStartReport,dateEndReport,hourStartReport,hourEndReport,splitIntoTwoShifts,duration-breakInHours,(String)jComboBox2.getSelectedItem(),(String)jComboBox1.getSelectedItem(),""+yearStart,""+yearEnd,"","",shiftsTableName);
        else
             machineReport = new ReportFormMachine(databaseInfo,2,machineName+"-"+id,dateStartReport,dateEndReport,hourStartReport,hourEndReport,splitIntoTwoShifts,duration-breakInHours,"","",""+yearStart,""+yearEnd,"","",shiftsTableName);
          machineReport.setVisible(true);
        } else
        if(isMachineReportMonthly){
         yearStart  = jYearChooser1.getYear();
         yearEnd    = jYearChooser2.getYear();
         
         dateStartReport      = ""+yearStart;
         dateEndReport        = ""+yearEnd;  
         String monthStartStr = ""; //sanity for proper format 
         String monthEndStr   = ""; //sanity for proper format 
         
         monthStart = jMonthChooser1.getMonth()+1;
         monthEnd   = jMonthChooser2.getMonth()+1;
        hourStartReport = jTextField8.getText();
        hourEndReport   = jTextField3.getText();
         
         if(monthStart < 10)
          monthStartStr = "0"+monthStart;    //sanity for proper format 
         else monthStartStr = ""+monthStart;
         if(monthEnd   < 10)
          monthEndStr   = "0"+(monthEnd);      //sanity for proper format 
         else monthEndStr = ""+(monthEnd);
         
         System.out.println(monthStartStr + " - " + monthEndStr);
         if (splitIntoTwoShifts)
         machineReport = new ReportFormMachine(databaseInfo,3,machineName+"-"+id,dateStartReport,dateEndReport,hourStartReport,hourEndReport,splitIntoTwoShifts,duration-breakInHours,(String)jComboBox2.getSelectedItem(),(String)jComboBox1.getSelectedItem(),""+yearStart,""+yearEnd,monthStartStr,monthEndStr,shiftsTableName);
       else
          machineReport = new ReportFormMachine(databaseInfo,3,machineName+"-"+id,dateStartReport,dateEndReport,hourStartReport,hourEndReport,splitIntoTwoShifts,duration-breakInHours,"","",""+yearStart,""+yearEnd,monthStartStr,monthEndStr,shiftsTableName);
         machineReport.setVisible(true);
        }
        } 
    }//GEN-LAST:event_jLabel10MouseClicked
   
    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
       materialsSetPanel.setVisible(true);
       showSettingsPopup(false);
       materialsHandler.grabMaterialsToTable();
       showSettingsPopup = true;
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        materialsHandler.deleteMaterial(materialsTable.getSelectedRow()); 
         
    }//GEN-LAST:event_jLabel28MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        try{
        float diameter = Float.parseFloat(jTextField12.getText());
        float weight   = Float.parseFloat(jTextField33.getText());
        float s1       = Float.parseFloat(jTextField11.getText());
        float s2       = Float.parseFloat(jTextField34.getText());
        float w14      = Float.parseFloat(jTextField14.getText());
        float w16      = Float.parseFloat(jTextField27.getText());
        float syntax   = Float.parseFloat(jTextField15.getText());
        float planet   = Float.parseFloat(jTextField47.getText());
        float EP       = Float.parseFloat(jTextField19.getText());
        float raXa     = Float.parseFloat(jTextField32.getText());  
        materialsHandler.addMaterial(diameter,weight,s1,s2,w14,w16,syntax,planet,EP,raXa);
        }catch(Exception addMaterialException){
            JOptionPane.showMessageDialog(null,"MainForm.java FAILED_ADDING_MATERIAL - " + addMaterialException.getMessage());
        }
    }//GEN-LAST:event_jLabel22MouseClicked

    private  Shift  tempShift;
    private  String shiftTime = "";
    
    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
      if (evt.getStateChange() == ItemEvent.SELECTED) {
        Object shiftType = evt.getItem(); 
        if(shiftType.equals("בוקר \\ ערב")){ //Selected index is 1 when we need to split into shifts 
           jComboBox2.removeAllItems();
           jComboBox1.removeAllItems();
           breakInHoursLabel.setVisible(false);
           breakInputTextField.setVisible(false);
           enableShiftPickInReportByMachine(true);  
           jCheckBox1.setVisible(false);
           ArrayList<Shift> shiftsAvailable = shiftHandler.getShifts(); 
           for(int i = 0 ; i < shiftsAvailable.size() ;i ++){
                tempShift = shiftsAvailable.get(i);  
                shiftTime = tempShift.getShiftStart() +"-"+tempShift.getShiftEnd();  
                if(tempShift.getType().contains("שישי")) break;
                else
                if(tempShift.getType().contains("בוקר"))
                 jComboBox2.addItem(shiftTime);
               else
                 if(tempShift.getType().contains("ערב"))
                 jComboBox1 .addItem(shiftTime);  
                
                
          } 
       }else{
           //Selected index is not to split into shifts 
           enableShiftPickInReportByMachine(false);
           jComboBox2.removeAllItems();
           jComboBox1.removeAllItems();
           breakInHoursLabel  .setVisible(true);
           breakInputTextField.setVisible(true);
           jCheckBox1.setVisible(true);
       } 
       }
    }//GEN-LAST:event_jComboBox3ItemStateChanged
 
  
    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
      shiftsSetPanel.setVisible(true);
      showSettingsPopup(false); 
      shiftHandler.addShiftsIntoTable();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
     
       if (evt.getStateChange() == ItemEvent.SELECTED) {
      
        switch(jComboBox4.getSelectedIndex())  {
          case 0:    //Daily
            //  jComboBox3.addItem((String)"יומי");
              enableFullDateChooseInReportByMachine(true);
              jMonthChooser1.setVisible(false);
              jMonthChooser2.setVisible(false);
              jYearChooser1 .setVisible(false);
              jYearChooser2 .setVisible(false);
              break;
          case 1:    //Monthly
           //   jComboBox3.removeItem((String)"יומי");
              enableFullDateChooseInReportByMachine(false);
              jMonthChooser1.setVisible(true);
              jMonthChooser2.setVisible(true);
              jYearChooser1 .setVisible(true);
              jYearChooser2 .setVisible(true);

              break;  
          case 2:    //Yearly
         //   jComboBox3.removeItem((String)"יומי");
              enableFullDateChooseInReportByMachine(false);
              jMonthChooser1.setVisible(false);
              jMonthChooser2.setVisible(false);
              jYearChooser1 .setVisible(true);
              jYearChooser2 .setVisible(true);
              break;
      } 
     }
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox3InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jComboBox3InputMethodTextChanged
         
    }//GEN-LAST:event_jComboBox3InputMethodTextChanged

    private void databaseSettingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_databaseSettingsMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_databaseSettingsMenuItemActionPerformed

    private void jCheckBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox1MouseClicked
       if(jCheckBox1.isSelected()){
           jTextField8.setText("06:30");
           jTextField8.setEnabled(false);
           jTextField3.setText("06:29");
           jTextField3.setEnabled(false);
           breakInputTextField.setEnabled(false);
       }else
       {
           jTextField8.setEnabled(true);
           jTextField3.setEnabled(true);
           breakInputTextField.setEnabled(true);
       }
    }//GEN-LAST:event_jCheckBox1MouseClicked
  
    public static void main(String args[]) {
           
        try {
           String laf = UIManager.getSystemLookAndFeelClassName();
           UIManager.setLookAndFeel(laf);
        }catch(Exception e){
            e.printStackTrace();
        }  
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MachineEfficRepChoosePanel;
    private javax.swing.JPanel MachineMalfRepChoosePanel;
    private javax.swing.JPanel MachineRepChoosePanel;
    private javax.swing.JPanel MalfunctionRepChoosePanel;
    private javax.swing.JPanel UtilRepChoosePanel;
    private javax.swing.JPanel WorkerEfficRepChoosePanel;
    private javax.swing.JPanel WorkerRepChoosePanel;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JLabel breakInHoursLabel;
    private javax.swing.JTextField breakInputTextField;
    private javax.swing.JMenuItem databaseSettingsMenuItem;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel dateLabel1;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel9;
    private com.toedter.calendar.JMonthChooser jMonthChooser1;
    private com.toedter.calendar.JMonthChooser jMonthChooser2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    private com.toedter.calendar.JYearChooser jYearChooser2;
    private javax.swing.JPanel machineChooseSettingsPanel;
    private javax.swing.JLabel machineNameLabel;
    public static javax.swing.JComboBox machinePickComBox;
    private javax.swing.JPanel machineReportPanel;
    private javax.swing.JPanel machinesSetPanel;
    private javax.swing.JTable machinesTable;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel matChooseSettingsPanel;
    private javax.swing.JPanel materialsSetPanel;
    private javax.swing.JTable materialsTable;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextField nameWorkerTextField;
    private javax.swing.JTextField numWorkerTextField;
    private javax.swing.JPanel rawMatRepChoosePanel;
    private javax.swing.JLabel removeWorkerLabel;
    private javax.swing.JLabel removeWorkerLabel1;
    private javax.swing.JLabel reportMachineTitle;
    private javax.swing.JPanel reportsPanel;
    private javax.swing.JLabel reportsPanelLabel;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JLabel settingsPanelLabel;
    private javax.swing.JPanel shiftSettingsChoosePanel;
    private javax.swing.JPanel shiftsSetPanel;
    private javax.swing.JTable shiftsTable;
    private javax.swing.JLabel titlePanel;
    private javax.swing.JLabel workerSetTitle;
    private javax.swing.JLabel workerSetTitle1;
    private javax.swing.JLabel workerSetTitle2;
    private javax.swing.JLabel workerSetTitle3;
    private javax.swing.JPanel workerSettingsChoosePanel;
    private javax.swing.JPanel workersSetPanel;
    private javax.swing.JTable workersTable;
    // End of variables declaration//GEN-END:variables
}
