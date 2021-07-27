 
package Objects;
 
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream; 
import java.io.FileOutputStream;  
import java.time.Duration;
import java.time.LocalTime;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel; 
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

 
public class Misc {
     
     public static boolean writeTableToExcel (JTable inputTable, String Location)  {
             try{
                HSSFWorkbook fWorkbook = new HSSFWorkbook();
                HSSFSheet fSheet = fWorkbook.createSheet("SavedSheet");
                HSSFFont sheetTitleFont = fWorkbook.createFont();
                HSSFCellStyle cellStyle = fWorkbook.createCellStyle();
                sheetTitleFont.setBold(true); 
                TableModel model = inputTable.getModel();
 
                TableColumnModel tcm = inputTable.getColumnModel();
                HSSFRow hRow = fSheet.createRow((short) 0);
                for(int j = 0; j < tcm.getColumnCount(); j++) {                       
                   HSSFCell cell = hRow.createCell((short) j);
                   cell.setCellValue(tcm.getColumn(j).getHeaderValue().toString());
                   cell.setCellStyle(cellStyle);
                } 
                for (int i = 0; i < model.getRowCount(); i++) {
                    HSSFRow fRow = fSheet.createRow((short) i+1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        if(model.getValueAt(i, j) != null){
                        HSSFCell cell = fRow.createCell((short) j);
                        cell.setCellValue(model.getValueAt(i, j).toString());
                        cell.setCellStyle(cellStyle);
                        }
                    }
                }
            FileOutputStream fileOutputStream;
            fileOutputStream = new FileOutputStream(Location);
            try (BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream)) {
            fWorkbook.write(bos);
            }
            fileOutputStream.close();
            }catch(Exception writeFileException){
                 writeFileException.printStackTrace();
                 return false;
            }
            return true;
    }
  
    public static void killFrame(JFrame input) { 
    WindowEvent wev = new WindowEvent(input, WindowEvent.WINDOW_CLOSING);
    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev); 
    input.setVisible(false);
    input.dispose(); 
    }
    
    
    public static double getDurationInHours(String input){ 
        try{
        if(input != null && input.length() > 0){  
        String split []       = input.split("-");
        if(split.length > 0){ 
        String firstInRange   = split[0];
        String secondInRange  = split[1];
        
        LocalTime start = LocalTime.parse(firstInRange);
        LocalTime stop  = LocalTime.parse(secondInRange);
        if (stop.isAfter(start)) { // the normal situation
            return Duration.between(start, stop).getSeconds()/360.0/10;
        } else if (stop.equals(LocalTime.MIDNIGHT)) {
             return Duration.between(start, stop).plusDays(1).getSeconds() / 360.0/10;
        } else {
             return Duration.between(start, stop).plusDays(1).getSeconds() / 360.0/10;
        } 
        }
        }
        }catch(Exception parseDurationException){ 
            parseDurationException.printStackTrace();
        }
        return 0.0;
    } 
}
