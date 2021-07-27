/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import SQL.SQLConnection; 
import Handlers.SettingsHandler; 
import Objects.DBInfo; 
import java.awt.Toolkit; 
import java.util.ArrayList; 
import javax.swing.JLabel;
import javax.swing.JOptionPane; 
import javax.swing.WindowConstants;


public class DBSettingsForm extends javax.swing.JFrame {

    private ArrayList<JLabel> labelsToDoHoverEffectOn = new ArrayList<>();
    private SettingsHandler   settings;
 
    private SQLConnection sqlConnectionTester;
    private String        dbIpInput;
    private String        dbPortInput;
    private String        dbNameInput;
    private String        dbUserInput;
    private String        dbPassInput;
    private String        dbWorkerTableInput;
    private String        dbShiftsTableInput;
    private String        dbStopsTableInput;
    private String        dbMachinesTableInput;
    private String        dbMaterialsTableInput;
    private String        dbCycleTableInput;
    
    private DBInfo        dbInfo;
    private boolean       isWindowsAuth = false;
     
    public DBSettingsForm(SettingsHandler settings, DBInfo dbInfo) {
        initComponents();
        
        try{
        this.settings     = settings;
        this.dbInfo       = dbInfo; 

        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("SQL Analysis Tool");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icons/icons8_sql_48px.png"))); 
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         
        dbIpTextField  .setText(dbInfo.getIP());
        dbPortTextField.setText(dbInfo.getPort()); 
        dbUserTextField.setText(dbInfo.getUser()); 
        dbPassTextField.setText(dbInfo.getPass());
        dbNameTextField.setText(dbInfo.getDbName());
        cycleTableTextField.setText(dbInfo.getTableCycles());
        machineTableTextField.setText(dbInfo.getTableMachines());
        stopTableTextField.setText(dbInfo.getTableStops());
        materialsTableTextField.setText(dbInfo.getTableMaterials());
        shiftsTableTextField.setText(dbInfo.getTableShifts());
        
    
        labelsToDoHoverEffectOn.add(connectionTestLabel);
        labelsToDoHoverEffectOn.add(saveLabel); 
        
        labelsHoverEffect labelEffects = new  labelsHoverEffect(labelsToDoHoverEffectOn); 
        labelEffects.start(); 
        }catch(Exception e){
            e.printStackTrace();
        }
        } 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dbSettingsPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        dbIpLabel = new javax.swing.JLabel();
        dbIpTextField = new javax.swing.JTextField();
        dbPortLabel = new javax.swing.JLabel();
        dbPortTextField = new javax.swing.JTextField();
        dbUserLabel = new javax.swing.JLabel();
        dbUserTextField = new javax.swing.JTextField();
        connectionTestLabel = new javax.swing.JLabel();
        dbNameLabel = new javax.swing.JLabel();
        dbPassTextField = new javax.swing.JTextField();
        dbPassLabel = new javax.swing.JLabel();
        saveLabel = new javax.swing.JLabel();
        dbNameTextField = new javax.swing.JTextField();
        tableShiftsLabel = new javax.swing.JLabel();
        shiftsTableTextField = new javax.swing.JTextField();
        tableWorkersLabel = new javax.swing.JLabel();
        workerTableTextField = new javax.swing.JTextField();
        tableMachinesLabel = new javax.swing.JLabel();
        machineTableTextField = new javax.swing.JTextField();
        tableStopsLabel = new javax.swing.JLabel();
        stopTableTextField = new javax.swing.JTextField();
        tableMaterialLabel = new javax.swing.JLabel();
        materialsTableTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cycleTableTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dbSettingsPanel.setPreferredSize(new java.awt.Dimension(350, 438));
        dbSettingsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        titleLabel.setText("הגדרות לשרת");
        dbSettingsPanel.add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 120, 40));

        dbIpLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbIpLabel.setText("כתובת התחברות");
        dbSettingsPanel.add(dbIpLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, -1, 20));

        dbIpTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbIpTextField.setText("localhost");
        dbSettingsPanel.add(dbIpTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 150, -1));

        dbPortLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbPortLabel.setText("פורט התחברות");
        dbSettingsPanel.add(dbPortLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 120, 20));

        dbPortTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbPortTextField.setText("1433");
        dbSettingsPanel.add(dbPortTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 150, -1));

        dbUserLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbUserLabel.setText("שם משתמש");
        dbSettingsPanel.add(dbUserLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 100, 20));

        dbUserTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbUserTextField.setText("admin");
        dbUserTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dbUserTextFieldActionPerformed(evt);
            }
        });
        dbSettingsPanel.add(dbUserTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 150, -1));

        connectionTestLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        connectionTestLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_test_passed_16px.png"))); // NOI18N
        connectionTestLabel.setText("בדיקה");
        connectionTestLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connectionTestLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                connectionTestLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                connectionTestLabelMouseExited(evt);
            }
        });
        dbSettingsPanel.add(connectionTestLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, 120, 30));

        dbNameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbNameLabel.setText("מסד נתונים ראשי");
        dbSettingsPanel.add(dbNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 130, 40));

        dbPassTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbPassTextField.setText("admin");
        dbPassTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dbPassTextFieldActionPerformed(evt);
            }
        });
        dbSettingsPanel.add(dbPassTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 150, -1));

        dbPassLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbPassLabel.setText("סיסמה");
        dbSettingsPanel.add(dbPassLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 50, 20));

        saveLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        saveLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_select_16px.png"))); // NOI18N
        saveLabel.setText("שמור");
        saveLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveLabelMouseExited(evt);
            }
        });
        dbSettingsPanel.add(saveLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 70, 30));

        dbNameTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbNameTextField.setText("OEE");
        dbSettingsPanel.add(dbNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 150, -1));

        tableShiftsLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tableShiftsLabel.setText("טבלת משמרות");
        dbSettingsPanel.add(tableShiftsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 100, 20));

        shiftsTableTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        shiftsTableTextField.setText("shifts");
        dbSettingsPanel.add(shiftsTableTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 150, -1));

        tableWorkersLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tableWorkersLabel.setText("טבלת עובדים");
        dbSettingsPanel.add(tableWorkersLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 110, 20));

        workerTableTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        workerTableTextField.setText("employees");
        dbSettingsPanel.add(workerTableTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 150, -1));

        tableMachinesLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tableMachinesLabel.setText("טבלת מכונות");
        dbSettingsPanel.add(tableMachinesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 120, 20));

        machineTableTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        machineTableTextField.setText("machines");
        dbSettingsPanel.add(machineTableTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 150, -1));

        tableStopsLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tableStopsLabel.setText("טבלת עצירות");
        dbSettingsPanel.add(tableStopsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 120, 20));

        stopTableTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        stopTableTextField.setText("stopInfo");
        dbSettingsPanel.add(stopTableTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 150, -1));

        tableMaterialLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tableMaterialLabel.setText("טבלת חומרי גלם");
        dbSettingsPanel.add(tableMaterialLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, -1, 20));

        materialsTableTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        materialsTableTextField.setText("materials");
        dbSettingsPanel.add(materialsTableTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 150, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("טבלת מחזורים");
        dbSettingsPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 157, -1, -1));

        cycleTableTextField.setText("Cycles");
        dbSettingsPanel.add(cycleTableTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 155, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dbSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dbSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveLabelMouseEntered
         
    }//GEN-LAST:event_saveLabelMouseEntered

    private void saveLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveLabelMouseExited
 
    }//GEN-LAST:event_saveLabelMouseExited

    private void dbPassTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dbPassTextFieldActionPerformed
    }//GEN-LAST:event_dbPassTextFieldActionPerformed

    private void dbUserTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dbUserTextFieldActionPerformed
    }//GEN-LAST:event_dbUserTextFieldActionPerformed

    private void connectionTestLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionTestLabelMouseEntered
      
    }//GEN-LAST:event_connectionTestLabelMouseEntered

    private void connectionTestLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionTestLabelMouseExited
       
    }//GEN-LAST:event_connectionTestLabelMouseExited
 
    private void connectionTestLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionTestLabelMouseClicked
        try{         
        dbIpInput   = dbIpTextField.getText();
        dbPortInput = dbPortTextField.getText();
        dbNameInput = dbNameTextField.getText();
        dbUserInput = dbUserTextField.getText();
        dbPassInput = dbPassTextField.getText(); 
        
        
        dbMaterialsTableInput = materialsTableTextField.getText();
        dbMachinesTableInput  = machineTableTextField.getText();
        dbStopsTableInput     = stopTableTextField.getText();
        dbShiftsTableInput    = shiftsTableTextField.getText();
        dbWorkerTableInput    = workerTableTextField.getText();
        
        DBInfo testConnectionInfo = new DBInfo(dbIpInput, dbPortInput, dbNameInput, dbUserInput, dbPassInput, dbMachinesTableInput,dbWorkerTableInput,dbStopsTableInput,dbMaterialsTableInput,dbShiftsTableInput ,dbCycleTableInput,isWindowsAuth);
        sqlConnectionTester = new SQLConnection(testConnectionInfo); 
        do{
            Thread.sleep(10); 
        }while(!sqlConnectionTester.finishedConnection()); 
        if(sqlConnectionTester.hasConnectedToDb())
             JOptionPane.showMessageDialog(null,"SUCCESS");
            else
             JOptionPane.showMessageDialog(null,"FAILED!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_connectionTestLabelMouseClicked

    private void saveLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveLabelMouseClicked
           dbIpInput   = dbIpTextField.getText();
           dbPortInput = dbPortTextField.getText();
           dbNameInput = dbNameTextField.getText();
           dbUserInput = dbUserTextField.getText();
           dbPassInput = dbPassTextField.getText();
           
           dbMaterialsTableInput = materialsTableTextField.getText();
           dbMachinesTableInput  = machineTableTextField.getText();
           dbStopsTableInput     = stopTableTextField.getText();
           dbShiftsTableInput    = shiftsTableTextField.getText();
           dbWorkerTableInput    = workerTableTextField.getText();
           dbCycleTableInput     = cycleTableTextField.getText();
           
           if(dbIpInput.length() > 0 && dbPortInput.length() > 0 &&  dbNameInput.length() > 0 && dbUserInput.length() > 0 && dbPassInput.length() > 0
                   && dbMaterialsTableInput.length() > 0 && dbMachinesTableInput.length() > 0 &&
                   dbStopsTableInput.length() > 0 && dbShiftsTableInput.length() > 0 
                   && dbWorkerTableInput.length() > 0 && dbCycleTableInput.length() > 0)
           { 
               dbInfo.setDBSettings(dbIpInput, dbPortInput, dbNameInput, dbUserInput, dbPassInput, dbMachinesTableInput,dbWorkerTableInput,dbStopsTableInput,dbMaterialsTableInput,dbShiftsTableInput ,dbCycleTableInput,isWindowsAuth);
               settings.saveFile();
               JOptionPane.showMessageDialog(null,"Success!");
           } 
    }//GEN-LAST:event_saveLabelMouseClicked

   
     

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel connectionTestLabel;
    private javax.swing.JTextField cycleTableTextField;
    private javax.swing.JLabel dbIpLabel;
    private javax.swing.JTextField dbIpTextField;
    private javax.swing.JLabel dbNameLabel;
    private javax.swing.JTextField dbNameTextField;
    private javax.swing.JLabel dbPassLabel;
    private javax.swing.JTextField dbPassTextField;
    private javax.swing.JLabel dbPortLabel;
    private javax.swing.JTextField dbPortTextField;
    private javax.swing.JPanel dbSettingsPanel;
    private javax.swing.JLabel dbUserLabel;
    private javax.swing.JTextField dbUserTextField;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField machineTableTextField;
    private javax.swing.JTextField materialsTableTextField;
    private javax.swing.JLabel saveLabel;
    private javax.swing.JTextField shiftsTableTextField;
    private javax.swing.JTextField stopTableTextField;
    private javax.swing.JLabel tableMachinesLabel;
    private javax.swing.JLabel tableMaterialLabel;
    private javax.swing.JLabel tableShiftsLabel;
    private javax.swing.JLabel tableStopsLabel;
    private javax.swing.JLabel tableWorkersLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField workerTableTextField;
    // End of variables declaration//GEN-END:variables
}
