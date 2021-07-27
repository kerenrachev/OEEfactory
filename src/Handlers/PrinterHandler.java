 
package Handlers;

import java.util.ArrayList;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

 
public class PrinterHandler {
    private PrintService   chosenPrinter; 
    private PrintService[] printServices; 
     
    private ArrayList<PrintService> availablePrinters ;
    
    public PrinterHandler(){
        refreshPrinters(); 
    } 
    /* Refreshes info about available printers */
    public void refreshPrinters()
    {
        availablePrinters = getAvailablePrinters();
    } 
    
    /* Returns array list containing available printers */ 
    public ArrayList<PrintService> getAvailablePrinters(){ 
        ArrayList<PrintService> printers = new ArrayList<>();
        
        printServices = PrintServiceLookup.lookupPrintServices(null, null);  
         for (PrintService printer : printServices)
             printers.add(printer);   
         return printers;
    } 
    
    /* Sets printer to use  */
    public void setPrinterToUse(PrintService inputPrinter)
    {
         chosenPrinter = inputPrinter;
    }
    /* Returns printer to use */
    public PrintService getPrinterToUse()
    {
        return chosenPrinter;
    }
    /* Returns number of available printers  */
    
    public int getNumAvailablePrinters(){
        return availablePrinters.size();
    }
}
