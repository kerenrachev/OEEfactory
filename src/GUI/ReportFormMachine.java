/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
 
import Graphs.GraphManager;
import Objects.DBInfo;
import Objects.Misc;
import SQL.SQLShifts;
import com.alee.laf.WebLookAndFeel;
import java.awt.Dimension;  
import java.awt.Toolkit;   
import java.time.YearMonth; 
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame; 
import javax.swing.JLabel;
import javax.swing.JOptionPane; 
import javax.swing.WindowConstants;  
import reports.abstractReportByMachine;
import reports.dailyReportByMachine;
import reports.monthlyReportByMachine;
import reports.yearlyReportByMachine;
 
public class ReportFormMachine extends javax.swing.JFrame {
   private  abstractReportByMachine reportAboutMachinePanel;
    private ArrayList<JLabel>      labelsToDoHoverEffectOn = new ArrayList<>();
    
    private  YearMonth  lastMonth;
    private  String     numDaysInMonth = "";
    private  SQLShifts  sqlShifts;
    private  double     morningWorkHours=0.0;
    private  double     eveningWorkHours=0.0;
    private  double     fridayWorkHours=0.0;
    private  double     saturdayWorkHours=0.0;
    private  int        typeReport;
    
    public ReportFormMachine(DBInfo dbInfo,
            int typeReport,String machineInfo,String dateStart,String dateEnd,String hourStart,String hourEnd,
            boolean shiftSplit, double workHours,String morningShift,String nightShift
            ,String yearStart,String yearEnd , String monthStart, String monthEnd,String shiftTableName ) {
        sqlShifts       = new SQLShifts(dbInfo);
        this.typeReport = typeReport;
        initComponents       ();
        WebLookAndFeel.install ();
        setLocationRelativeTo(null);
        setTitle             ("Rachev - SQL Analysis Tool");  
        setIconImage         (Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icons/icons8_sql_48px.png")));
        setResizable         (false);  
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
 
        String machineName = machineInfo.split("-")[0];
        String machineId   = machineInfo.split("-")[1];
        
        String infoSplit1[] = null ;
        String infoSplit2[] = null;
        fridayWorkHours=sqlShifts.getFridayWorksHours(shiftTableName);
        saturdayWorkHours=sqlShifts.getSaturdayWorksHours(shiftTableName);
        try{
        if(shiftSplit && morningShift.length() > 0 && nightShift.length() > 0){ 
        infoSplit1  = morningShift.split("-"); //[0] - hour start [1] - minute start
        infoSplit2  = nightShift  .split("-"); //[0] - hour start [1] - minute start
        
        System.out.println("Morning Start :" + infoSplit1[0] + " Morning End : " + infoSplit1[1]); 
        System.out.println("Evening Start :" + infoSplit2[0] + " Evening End : " + infoSplit2[1]);
        morningWorkHours=sqlShifts.getWorkHours(shiftTableName,infoSplit1[0],infoSplit1[1]);
        eveningWorkHours=sqlShifts.getWorkHours(shiftTableName,infoSplit2[0],infoSplit2[1]);
        workHours=morningWorkHours+eveningWorkHours;
        hourStart = infoSplit1[0]; // get start of morning shift
        hourEnd   = infoSplit2[1]; // get end   of evening shift
        } 
         
        if(typeReport == 1){ // daily report   
        if(infoSplit1 != null && infoSplit2 != null) //shift split
        reportAboutMachinePanel = new dailyReportByMachine (dbInfo,machineId, dateStart,dateEnd, hourStart, hourEnd,workHours,morningWorkHours,eveningWorkHours,shiftSplit,infoSplit1[0],infoSplit1[1],infoSplit2[0],infoSplit2[1],fridayWorkHours,saturdayWorkHours,typeReport);
        else //not shift split
        reportAboutMachinePanel = new dailyReportByMachine (dbInfo,machineId, dateStart,dateEnd, hourStart, hourEnd,workHours,morningWorkHours,eveningWorkHours,shiftSplit,hourStart,hourEnd,"19:00","06:30",fridayWorkHours,saturdayWorkHours,typeReport);
         
        }else{ 
        if(typeReport == 2){ //yearly report
        lastMonth = YearMonth.of(Integer.parseInt(yearEnd), 12);
        numDaysInMonth = ""+lastMonth.lengthOfMonth();
      if (infoSplit1 != null && infoSplit2 != null)
        reportAboutMachinePanel = new yearlyReportByMachine(dbInfo,machineId, yearStart+"-01-01",yearEnd+"-12-"+numDaysInMonth, hourStart, hourEnd,workHours,morningWorkHours,eveningWorkHours,shiftSplit,infoSplit1[0],infoSplit1[1],infoSplit2[0],infoSplit2[1],fridayWorkHours,saturdayWorkHours,typeReport);
       else reportAboutMachinePanel = new yearlyReportByMachine(dbInfo,machineId, yearStart+"-01-01",yearEnd+"-12-"+numDaysInMonth, hourStart, hourEnd,workHours,morningWorkHours,eveningWorkHours,shiftSplit,hourStart,hourEnd,"","",fridayWorkHours,saturdayWorkHours,typeReport);
        }else
        if(typeReport == 3){ //monthly report
            System.out.println(hourStart);
        System.out.println(yearEnd + " - > " + monthEnd);
        lastMonth = YearMonth.of(Integer.parseInt(yearEnd), Integer.parseInt(monthEnd));   
        numDaysInMonth = ""+ lastMonth.lengthOfMonth();
        if (infoSplit1 != null && infoSplit2 != null)
        reportAboutMachinePanel = new monthlyReportByMachine(dbInfo,machineId, yearStart+"-"+monthStart+"-01",yearEnd+"-"+monthEnd+"-"+numDaysInMonth, hourStart, hourEnd,workHours,morningWorkHours,eveningWorkHours,shiftSplit,infoSplit1[0],infoSplit1[1],infoSplit2[0],infoSplit2[1],fridayWorkHours,saturdayWorkHours,typeReport);
        else
            reportAboutMachinePanel = new monthlyReportByMachine(dbInfo,machineId, yearStart+"-"+monthStart+"-01",yearEnd+"-"+monthEnd+"-"+numDaysInMonth, hourStart, hourEnd,workHours,morningWorkHours,eveningWorkHours,shiftSplit,hourStart,hourEnd,"","",fridayWorkHours,saturdayWorkHours,typeReport);
        }
        } 
        reportAboutMachinePanel.setLocation(10,90);
        reportAboutMachinePanel.setSize(1420,600);
        reportAboutMachinePanel.setVisible(true);
        jLabel5.setText(dateStart);
        jLabel6.setText(dateEnd); //
        
        jLabel7.setText(hourStart );
        jLabel2.setText(hourEnd );
        
        labelsToDoHoverEffectOn.add(jLabel9); 
        labelsToDoHoverEffectOn.add(jLabel11);
        labelsToDoHoverEffectOn.add(jLabel12);
        labelsToDoHoverEffectOn.add(jLabel13);
        labelsToDoHoverEffectOn.add(jLabel10);
        
        labelsHoverEffect labelEffects = new  labelsHoverEffect(labelsToDoHoverEffectOn); 
        labelEffects      .start();  
        
        jLabel8         .setText("זמן עבודה   "+workHours + " שעות"); 
        machineNameTitle.setText(machineName); 
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double y   = screenSize.getHeight() ;
        double x   = screenSize.getWidth();
        reportAboutMachinePanel.setSize((int)(x-25),(int)(y-120));
        setResizable(false);  
        this.add(reportAboutMachinePanel);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        }catch(Exception startReportException){
            JOptionPane.showMessageDialog(null,"REPORT_CREATION_FAILED - " + startReportException.getMessage()); 
            startReportException.printStackTrace();
            Misc.killFrame(this);
        }
    }
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        reportMachineTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        machineNameTitle = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15=new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reportMachineTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        reportMachineTitle.setText("דוח עבור מכונה");
        jPanel1.add(reportMachineTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 0, 140, 40));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 50, 60, 30));

        jLabel1.setText("מהשעה ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 80, 30));

        jLabel15.setText("עד השעה ");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 120, -1));

        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, 120, -1));
        jLabel3.setText("מתאריך    ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 60, -1, -1));

        jLabel4.setText("עד תאריך - ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 60, 120, -1));
        jPanel1.add(machineNameTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 0, 140, 40));

        jLabel5.setText("jLabel5");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 60, -1, -1));

        jLabel6.setText("jLabel6");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 60, -1, -1));

        jLabel7.setText("jLabel7");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, -1, 30));

        jLabel9.setText("שמירה לאקסל");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 20));

        jLabel8.setText("זמן עבודה ביום - ");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 250, -1));

        jLabel10.setText("גרף יעילות מכונה");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 10, 160, -1));

        jLabel11.setText("גרף טון לשעה");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, 20));

        jLabel12.setText("גרף ניצול זמן מכונה");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 150, 20));

        jLabel13.setText("גרף נצילות תפוקת מכונה");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 230, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1440, 10));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    JFileChooser excelSaveChooser; 
    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        excelSaveChooser= new JFileChooser();
        excelSaveChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  
        if (excelSaveChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        String savePath = excelSaveChooser.getSelectedFile().getAbsolutePath(); 
     
        if(Misc.writeTableToExcel(reportAboutMachinePanel.getTable(),savePath+".xls"))
           JOptionPane.showMessageDialog(null,"Done!");
        else
            JOptionPane.showMessageDialog(null,"Failed!"); 
    
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
       new GraphManager(reportAboutMachinePanel, "tonPerHour").setVisible(true);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
       new GraphManager(reportAboutMachinePanel,"machineTimeUtilization").setVisible(true);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
       new GraphManager(reportAboutMachinePanel,"utilizationOfMachineOutPut").setVisible(true);
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
       new GraphManager(reportAboutMachinePanel,"efficiency").setVisible(true);
    }//GEN-LAST:event_jLabel10MouseClicked

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel machineNameTitle;
    private javax.swing.JLabel reportMachineTitle;
    // End of variables declaration//GEN-END:variables
}
