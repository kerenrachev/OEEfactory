package Objects;

public class Cycle {
    private String startCycle;
    private String endCycle;
    private String machineId;
    private String workerNumber;
    private int    TWork;
    private int    averageSpeed;
    private int    totalLength;
    private int    bendingCounter;
    private int    totalWeight;
    private int    cutCounter;
    private int    wireDeameter;
    private int    wireAmount;
    private int    weightPerMeter;
    private double machineOutputUsage;
    
    public Cycle( String startCycle,String endCycle,String machineId,String workerNumber,int TWork,int averageSpeed,int totalLength,int bendingCounter,
                    int totalWeight,int cutCounter,int wireDeameter,int wireAmount,int weightPerMeter,double machineOutputUsage){
        this.startCycle=startCycle;
        this.endCycle=endCycle;
        this.machineId=machineId;
        this.workerNumber=workerNumber;
        this.TWork=TWork;
        this.averageSpeed=averageSpeed;
        this.totalLength=totalLength;
        this.bendingCounter=bendingCounter;
        this.totalLength=totalLength;
        this.cutCounter=cutCounter;
        this.wireDeameter=wireDeameter;
        this.weightPerMeter=weightPerMeter;
        this.totalWeight=totalWeight;
        this.wireAmount=wireAmount;
        this.machineOutputUsage=machineOutputUsage;
    }

    public int getWeightPerMeter() {
        return weightPerMeter;
    }
    public String getDate(){
        String [] tokens=startCycle.split(" ");
        return tokens[0];
    }
    public String getHour(){
        String [] tokens=startCycle.split(" ");
        return tokens[1].substring(0,5);
    }
    public String getMonth(){
        return startCycle.substring(5,7);
    }
    public String getYear(){
        return startCycle.substring(0,4);
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public int getBendingCounter() {
        return bendingCounter;
    }

    public int getCutCounter() {
        return cutCounter;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public int getTWork() {
        return TWork;
    }

    public int getWireAmount() {
        return wireAmount;
    }

    public int getWireDeameter() {
        return wireDeameter;
    }

    public String getEndCycle() {
        return endCycle.substring(0,19);
    }

    public String getMachineId() {
        return machineId;
    }

    public String getStartCycle() {
        return startCycle.substring(0,19);
    }

    public String getWorkerNumber() {
        return workerNumber;
    }
    public void print(){
        System.out.println(" "+startCycle+" "+endCycle+" "+machineId+" "+workerNumber+" "+TWork+
                " "+averageSpeed+" "+totalLength+" "+bendingCounter+" "+totalWeight+" "+cutCounter+
                " "+wireDeameter+" "+wireAmount+" "+weightPerMeter);
    }
    public double getMachineOutputUsage(){
        return machineOutputUsage;
    }
}
