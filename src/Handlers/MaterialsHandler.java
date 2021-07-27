 
package Handlers;
 
import Objects.DBInfo;
import Objects.Material;
import SQL.SQLMaterials;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MaterialsHandler {
      
    private final DefaultTableModel   materialsTableModel ;  
    private final String              tableName;
    private final DBInfo              connectionInfo;
    
    //Used to connect into the material database
    private SQLMaterials        sqlMaterialConnector; 
    
    public MaterialsHandler(DefaultTableModel materialsTableModel,DBInfo connectionInfo){
        this.materialsTableModel = materialsTableModel;
        this.connectionInfo      = connectionInfo;
        this.tableName           = connectionInfo.getTableMaterials();
    }
    
     
    private ArrayList<Material> materials;
    private Material            tempMat;
    
      public void grabMaterialsToTable(){
        materialsTableModel.setRowCount(0);
        if(!ready())
        connect(); 
        
        materials            = sqlMaterialConnector.getDataBase(tableName);
       
        for (Material material : materials) {
            tempMat = material; 
            materialsTableModel.addRow(new Object[] {tempMat.getdiamterMM(),tempMat.getWeightMeterPerKil(),tempMat.getnumOfWiresFormatS1(),tempMat.getnumOfWiresFormatS2(),tempMat.getnumOfWiresFormat14(),tempMat.getnumOfWiresFormat16(),tempMat.getminiSyntax(),tempMat.getplanet20(),tempMat.getEvgPbc(),tempMat.getRaXa()});
        } 
      } 
     public boolean ready(){
          return (sqlMaterialConnector != null &&  sqlMaterialConnector.hasConnectedToDb());
     }
     public void connect(){
         sqlMaterialConnector = new SQLMaterials(connectionInfo);  
     }
     public void deleteMaterial(int index){
          float       diameter   = (float) materialsTableModel.getValueAt(index,0); 
        for (Material material : materials) {
            tempMat = material;
            if(tempMat.getdiamterMM() == diameter)
            {
                if(sqlMaterialConnector.deleteMaterial(tableName,diameter)){
                    JOptionPane.showMessageDialog(null,"Success!");
                    grabMaterialsToTable();
                }else JOptionPane.showMessageDialog(null,"Failed!");
                break;
            } 
        }
     }
     
     
     public void addMaterial(float diameter, float weight, float s1, float s2, float w14, float w16,float syntax, float planet,float EP, float raXa){
         if(sqlMaterialConnector.addMaterial(tableName,diameter,weight,s1,s2,w14,w16,syntax,planet,EP,raXa)){
                JOptionPane.showMessageDialog(null,"Success!");
                grabMaterialsToTable();
        } else
                JOptionPane.showMessageDialog(null,"Failed!");
     }
      
}
