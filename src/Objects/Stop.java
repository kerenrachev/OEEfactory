package Objects;

public class Stop {
    private String startIdle;
    private String endIdle;
    private String machineId;
    private String managerBegin;
    private int    IdleCause;

    public Stop( String StartIdle,String EndIdle,String machineId,String ManagerBegin,int IdleCause){
        this.startIdle=StartIdle;
        this.endIdle=EndIdle;
        this.machineId=machineId;
        this.managerBegin=ManagerBegin;
        this.IdleCause=IdleCause;

    }

    public String getDate(){
        String [] tokens=startIdle.split(" ");
        return tokens[0];
    }

    public String getHour(){
        String [] tokens=startIdle.split(" ");
        return tokens[1].substring(0,5);
    }
    public String getMonth(){
        return startIdle.substring(5,7);
    }
    public String getYear(){
        return startIdle.substring(0,4);
    }

    public String getMachineId() {
        return machineId;
    }

    public String getStartIdle() {
        return startIdle.substring(0,19);
    }

    public String getEndIdle() {
        return endIdle.substring(0,19);
    }

    public int getIdleCause() {
        return IdleCause;
    }

    public String getManagerBegin() {
        return managerBegin;
    }

    public void print(){
        System.out.println(startIdle+" "+endIdle+" "+machineId+" "+managerBegin+" "+getIdleCause());

    }
}
