package Graphs;
  
import java.awt.Color; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger; 
import javafx.scene.chart.CategoryAxis;
import javax.swing.JFrame; 
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset; 
import reports.abstractReportByMachine;
public class GraphManager extends javax.swing.JFrame {

    private boolean isDivided;
    private int     numOfVariables;
    private ArrayList<String> variablesForGraph;
    private int    indexOfDate;
    private int    indexOfShift;
    private int    indexOfValue;
    private int    typeOfReport;
    private String typeOfGraph;
    private int    indexForData;
    private int    indexForReducing;
    private String titleForGraph;
    private String yParameter;
    private int    numOfCoulomns;
    private int    numOfRows;
    private int    maxElementsAllowed;
    private String[][] tableArray;
    private ArrayList<String> morningShifts;
    private ArrayList<String> eveningShifts;
    int numOfMornings;
    int numOfEvenings;

    // Constractor gets the machineReport and the type of graph requested as String :
    //"tonPerHoue" , "machineTimeUtilization","utilizationOfMachineOutPut","efficiency"
    public GraphManager(abstractReportByMachine report, String typeOfGraph) {
         
        this.typeOfReport=report.getTypeReport();
        variablesForGraph = new ArrayList<>();
        this.numOfVariables = report.getTableModel().getRowCount();
        this.isDivided = report.isDevidedByShift();
        this.typeOfGraph = typeOfGraph;
        //Here you can deside how much elements can be in axix X before it turns into line chart (if graph is undivided)
        this.maxElementsAllowed =14;
        this.numOfRows = report.getTableModel().getRowCount();
        this.numOfCoulomns = report.getTableModel().getColumnCount();
        this.variablesForGraph = updateValuesForGraph(report);
        this.morningShifts = new ArrayList<>();
        this.eveningShifts = new ArrayList<>();
        initComponents();
        if (!isDivided) {
             if (numOfRows > maxElementsAllowed) {
                createLineChart(false);
            } else {
                 setUndividedGraph();   //this method is fine dont touch
            }
        } else {
            arangeShiftsInArrays();
            if (numOfRows > maxElementsAllowed) {
                createLineChart(true);
            } else {
                
                createDivideBarChart(report);
            }
        }
        this.   setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private ArrayList<String> updateValuesForGraph(abstractReportByMachine report) {
        //Initializing indexed by type of graph requested in constructor.
        if (typeOfGraph.equalsIgnoreCase("tonPerHour")) {
            this.indexForData = 3;
            this.titleForGraph = "גרף טון לשעה";
            this.yParameter = "טון לשעה";
        }
        if (typeOfGraph.equalsIgnoreCase("machineTimeUtilization")) {
            this.indexForData = 2;
            this.yParameter = "ניצול זמן באחוזים %";
            this.titleForGraph = "גרף ניצול זמן מכונה";
        }
        if (typeOfGraph.equalsIgnoreCase("utilizationOfMachineOutPut")) {
            this.indexForData = 1;
            this.yParameter = "נצילות תפוקת מכונה %";
            this.titleForGraph = "גרף נצילות תפוקת מכונה";
        }
        if (typeOfGraph.equalsIgnoreCase("efficiency")) {
            this.indexForData = 0;
            this.titleForGraph = "גרף יעילות מכונה";
            this.yParameter = "יעילות %";
        }
        int indexToReduce=0;
        if(typeOfReport==1){
            indexToReduce=2;
        }
        if(typeOfReport==2 || typeOfReport==3){
            indexToReduce=1;
        } 
        int numOfRows = report.getTableModel().getRowCount();
        for (int i = 0; i < numOfRows; i++) {

            String shift;
            String dayInWeek;
            addRelevantElementsForBothTypes(report, i);

            // if divided get shift (morning/evening) as well
            if (isDivided) {
                
                shift = (String) report.getTableModel().getValueAt(i, numOfCoulomns - indexToReduce-1); 
                variablesForGraph.add(shift);
                if(typeOfReport==1){
                     dayInWeek = (String) report.getTableModel().getValueAt(i, numOfCoulomns - indexToReduce);
                     variablesForGraph.add(dayInWeek);
                }
                
               
            }
        }
        return variablesForGraph;
    }

    public void addRelevantElementsForBothTypes(abstractReportByMachine report, int i) {
        //Get Date
        String date = (String) report.getTableModel().getValueAt(i, numOfCoulomns - 1);
        //Gets Y axis value
        String value = String.valueOf(report.getTableModel().getValueAt(i, indexForData));
        //Add values to array
        variablesForGraph.add(value);
        variablesForGraph.add(date);
    }

    private void setUndividedGraph() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
       
        if (numOfRows <= maxElementsAllowed) {
            System.out.println(variablesForGraph);
            for (int i = 0; i < variablesForGraph.size(); i += 2) {
                System.out.println(variablesForGraph.get(i)+" "+ variablesForGraph.get(i + 1));
                dataset.setValue(Double.parseDouble(variablesForGraph.get(i)), "", variablesForGraph.get(i + 1));
            }

            JFreeChart chart = ChartFactory.createBarChart3D(titleForGraph, "", yParameter, dataset, PlotOrientation.HORIZONTAL.VERTICAL, false, true, false);
            chart.setBackgroundPaint(Color.white);
            chart.getTitle().setPaint(Color.black);

            CategoryPlot p = chart.getCategoryPlot();
            p.setRangeGridlinePaint(Color.green);

            ChartPanel frame1 = new ChartPanel(chart);
            jPanel1.add(frame1);
            frame1.setVisible(true);
            frame1.setSize(1150, 620);
        } else {
            //Create divided bar Chart
            createLineChart(true);
        }

    }

    //Creates divided graph by shifts
    private void createDivideBarChart(abstractReportByMachine report) {
        // row keys...
        String series1 = "Morning";
        String series2 = "Evening";
        double eveningValue = 0;
        double dayValue = 0;
        String category1 = "";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println(morningShifts);
            int i = 1;
            int index=0;
            if(typeOfReport==1){
                index=4;
            }
            else{
                index=3;
            }
        for (i = 1; i < morningShifts.size() - index; i += index) {
            String nextDate = "";
            String checkForNextDate = morningShifts.get(i);
            dataset.addValue(Double.parseDouble(morningShifts.get(i - 1)), series1, morningShifts.get(i));
            boolean bool = false;
            do {
                
                try {
                    nextDate = getNextDate(checkForNextDate);
                } catch (ParseException ex) {
                    Logger.getLogger(GraphManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (nextDate.equals(morningShifts.get(i + index))) {
                    bool = true;
                } else {
                    dataset.addValue(0, series1, nextDate);
                    checkForNextDate = nextDate;
                }   
            } while (!bool);
        }
        dataset.addValue(Double.parseDouble(morningShifts.get(i - 1)), series1, morningShifts.get(i));
      
        for (i = 1; i < eveningShifts.size() - index; i += index) {
            String nextDate = "";
            String checkForNextDate = eveningShifts.get(i);
            dataset.addValue(Double.parseDouble(eveningShifts.get(i - 1)), series2, eveningShifts.get(i));
            boolean bool = false;
            do {

                try {
                    nextDate = getNextDate(checkForNextDate);
                } catch (ParseException ex) {
                    Logger.getLogger(GraphManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (nextDate.equals(eveningShifts.get(i + index))) {
                    bool = true;
                } else {
                    dataset.addValue(0, series2, nextDate);
                    checkForNextDate = nextDate;
                }

            } while (!bool);
        }
        dataset.addValue(Double.parseDouble(eveningShifts.get(i - 1)), series2, eveningShifts.get(i));
      
        JFreeChart chart = ChartFactory.createBarChart3D(titleForGraph, "", yParameter, dataset, PlotOrientation.HORIZONTAL.VERTICAL, false, true, false);
        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.black);

        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.green);

        ChartPanel frame1 = new ChartPanel(chart);
        jPanel1.add(frame1);
        frame1.setVisible(true);
        frame1.setSize(1150, 620);

    }
    private static final long serialVersionUID = 1L;

    
    public void createLineChart(boolean isDivided) {
        // Create dataset  
        DefaultCategoryDataset dataset = createDataset(isDivided);
//        System.out.println(dataset.getValue(1,0));
      

    
        // Create chart  
        JFreeChart chart = ChartFactory.createLineChart(
                titleForGraph, // Chart title  
                "ציר זמן", // X-Axis Label  
                yParameter, // Y-Axis Label  
                dataset,
                PlotOrientation.VERTICAL,
                
                true, true, false
        );
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.getDomainAxis().setCategoryLabelPositions(
        CategoryLabelPositions.UP_45);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(false);
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setShapesVisible(true);
        ChartPanel frame1 = new ChartPanel(chart);
        
        jPanel1.add(frame1);
        frame1.setVisible(true);
        frame1.setSize(1150, 620);
    }

    public DefaultCategoryDataset createDataset(boolean isDivided) {
        String series1;
        if(!isDivided){
            series1="";
        }
        else{
            series1 = "בוקר";
        }        
        
        String series2 = "ערב";
     
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if(isDivided){
            
          int i=1;
             int index=0;
            if(typeOfReport==1){
                index=4;
            }
            else{
                index=3;
            }
        for (i = 1; i < morningShifts.size() - index; i += index) {
              System.out.println(morningShifts+" "+eveningShifts);
            String nextDate = "";
            String checkForNextDate = morningShifts.get(i);
            dataset.addValue(Double.parseDouble(morningShifts.get(i - 1)), series1, morningShifts.get(i));
            boolean bool = false;
            do {
                
                System.out.println(nextDate+"  graphmanager.GraphManager.createDatase1t11() "+morningShifts.get(i + index));
                try {
                    nextDate = getNextDate(checkForNextDate);
                } catch (ParseException ex) {
                    Logger.getLogger(GraphManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (nextDate.equals(morningShifts.get(i + index))) {
                    bool = true;
                } else {
                    dataset.addValue(Double.parseDouble(morningShifts.get(i - 1)), series1, nextDate);
                    checkForNextDate = nextDate;
                }

            } while (!bool);
        }
        
        dataset.addValue(Double.parseDouble(morningShifts.get(i - 1)), series1, morningShifts.get(i));
           System.out.println(morningShifts.get(i - 1)+" "+series1+" "+morningShifts.get(i));
            
        for (i = 1; i < eveningShifts.size() - index; i += index) {
            String nextDate = "";
            String checkForNextDate = eveningShifts.get(i);
            dataset.addValue(Double.parseDouble(eveningShifts.get(i - 1)), series2, eveningShifts.get(i));
            System.out.println(eveningShifts.get(i - 1) + " " + series2 + " " + eveningShifts.get(i));
            boolean bool = false;
            do {

                try {
                    nextDate = getNextDate(checkForNextDate);
                } catch (ParseException ex) {
                    Logger.getLogger(GraphManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (nextDate.equals(eveningShifts.get(i + index))) {
                    bool = true;
                } else {
                    dataset.addValue(Double.parseDouble(morningShifts.get(i - 1)), series2, nextDate);
                    checkForNextDate = nextDate;
                }

            } while (!bool);
        }
        
        dataset.addValue(Double.parseDouble(eveningShifts.get(i - 1)), series2, eveningShifts.get(i));
        System.out.println(eveningShifts.get(i - 1)+" "+series2+" "+eveningShifts.get(i));
        
        }
        else{
           for (int i = 0; i < variablesForGraph.size(); i += 2) {
                dataset.setValue(Double.parseDouble(variablesForGraph.get(i)), series1, variablesForGraph.get(i + 1));
            }
        }
        //dataset.addValue(0,series1,"07");
        //dataset.addValue(1,series2,"07");
        return dataset;
    }

    public String getNextDate(String curDate) throws ParseException {
       if(typeOfReport==1){
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = format.parse(curDate);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return format.format(calendar.getTime());
       }
       else if (typeOfReport==2){
           int year= Integer.parseInt(curDate)+1;
           return ""+year;
       }
       else{
            if(Integer.parseInt(curDate)+1<=12){
                int month=Integer.parseInt(curDate)+1;
                String addZiro;
                if(month<10){
                    addZiro=""+0;
                }
                else addZiro="";
                String x =addZiro+month;
                return x;
                        
            }
            else return "01";
       }
    }
      private void arangeShiftsInArrays() {
        int indexToADDToGetToShift=0;
        if(typeOfReport==1){
             indexToADDToGetToShift=4;
        }
        else{
            indexToADDToGetToShift=3;
        }
        for (int i = 0; i < variablesForGraph.size() - 2; i+=indexToADDToGetToShift) {
            if (variablesForGraph.get(i + 2).equals("בוקר")) {
                morningShifts.add(variablesForGraph.get(i));
                morningShifts.add(variablesForGraph.get(i + 1));
                morningShifts.add(variablesForGraph.get(i + 2));
                if(typeOfReport==1){
                     morningShifts.add(variablesForGraph.get(i + 3));     
                }
               
            }
        }
          System.out.println(variablesForGraph);
          System.out.println(morningShifts);
        for (int i = 0; i < variablesForGraph.size() - 2; i +=indexToADDToGetToShift) {
            if (variablesForGraph.get(i + 2).equals("ערב")) {
                eveningShifts.add(variablesForGraph.get(i));
                eveningShifts.add(variablesForGraph.get(i + 1));
                eveningShifts.add(variablesForGraph.get(i + 2));
                if(typeOfReport==1){
                    eveningShifts.add(variablesForGraph.get(i + 3));
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1183, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 638, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
 
}
