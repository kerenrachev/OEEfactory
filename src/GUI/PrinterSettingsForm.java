 
package GUI;
  
import Handlers.PrinterHandler;
import com.alee.laf.WebLookAndFeel;
import java.awt.Toolkit; 
import java.util.ArrayList;
import javax.print.PrintService;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

 
public class PrinterSettingsForm extends javax.swing.JFrame {
 
    private ArrayList<PrintService> printers;
    private PrinterHandler          printerObject;  
    private int                     numAvailablePrinters;  
     
    private PrintService            chosenPrinter; 
    private ArrayList<JLabel>       labelsToDoHoverEffectOn = new ArrayList<>();
     
 
    public PrinterSettingsForm(PrinterHandler printerObject) { 
        try {
        initComponents();  
        this.printerObject = printerObject;     
        
        numAvailablePrinters = printerObject.getNumAvailablePrinters();
        printers             = printerObject.getAvailablePrinters();
        
        numPrintersFoundLabel.setText("מספר מדפסות שנמצאו - " + numAvailablePrinters);
        
        for(int i = 0; i < printers.size(); i++)
            printerComboBox.addItem((String)printers.get(i).getName());
        
        chosenPrinter = printerObject.getPrinterToUse();
        if(chosenPrinter != null)
            setOldChosenPrinter(chosenPrinter.getName());
        
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Rachev - SQL Analysis Tool");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icons/icons8_sql_48px.png"))); 
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  
        labelsToDoHoverEffectOn.add(choosePrinterButton); 
        
        labelsHoverEffect labelEffects = new  labelsHoverEffect(labelsToDoHoverEffectOn); 
        labelEffects.start();
        
        UIManager.setLookAndFeel ( new WebLookAndFeel () ); 
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    public void setOldChosenPrinter(String printerName){     
         String currentItem = "";
         
         for(int i = 0 ; i < printerComboBox.getItemCount() ; i++){
             currentItem = (String)printerComboBox.getItemAt(i);
             if(currentItem.equals(printerName)){
                 printerComboBox.setSelectedIndex(i);
                 break;
             }
         }
    }
    public PrinterHandler getPrinter(){
        return printerObject;
    } 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        printerPanel = new javax.swing.JPanel();
        printerFormTitle = new javax.swing.JLabel();
        numPrintersFoundLabel = new javax.swing.JLabel();
        printerComboBox = new javax.swing.JComboBox();
        choosePrinterButton = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(400, 165));

        printerPanel.setPreferredSize(new java.awt.Dimension(390, 110));
        printerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        printerFormTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        printerFormTitle.setText("בחירת מדפסת");
        printerPanel.add(printerFormTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 120, 30));

        numPrintersFoundLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        numPrintersFoundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_numbers_16px.png"))); // NOI18N
        numPrintersFoundLabel.setText("מספר מדפסות שנמצאו - 0");
        printerPanel.add(numPrintersFoundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 60, 190, 20));

        printerPanel.add(printerComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 300, 30));

        choosePrinterButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        choosePrinterButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_select_16px.png"))); // NOI18N
        choosePrinterButton.setText("בחר");
        choosePrinterButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choosePrinterButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                choosePrinterButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                choosePrinterButtonMouseExited(evt);
            }
        });
        printerPanel.add(choosePrinterButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 50, 30));
        printerPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 25, 400, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(printerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(printerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void choosePrinterButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choosePrinterButtonMouseEntered
       
    }//GEN-LAST:event_choosePrinterButtonMouseEntered

    private void choosePrinterButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choosePrinterButtonMouseExited
        
    }//GEN-LAST:event_choosePrinterButtonMouseExited

    private void choosePrinterButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choosePrinterButtonMouseClicked
          
          if(printers.size() <= 0) return; //sanity check
          
          String chosenPrinterInComboBox = (String)printerComboBox.getSelectedItem();  
          String currentPrinter =  ""; 
          
          for(int i = 0 ; i < printers.size(); i++){
              currentPrinter = printers.get(i).getName();
              if(chosenPrinterInComboBox.equals(currentPrinter)){
                  printerObject.setPrinterToUse(printers.get(i));
                  break;
              }
          }
          this.setVisible(false);
          this.dispose();
    }//GEN-LAST:event_choosePrinterButtonMouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel choosePrinterButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel numPrintersFoundLabel;
    private javax.swing.JComboBox printerComboBox;
    private javax.swing.JLabel printerFormTitle;
    private javax.swing.JPanel printerPanel;
    // End of variables declaration//GEN-END:variables
}
