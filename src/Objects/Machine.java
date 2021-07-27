 
package Objects;

public class Machine {
	private int    id;
	private int    machineNum;
	private String machineName;
	private int    speedMeterPerSec;
	private float  CutTimeSec;
	private float  BendingTimeSec;
	private int    ChargingTimeSec;
	
	public Machine(int id, int num, String name, int speed, float cut, float bend, int charge) {
		this.id=id;
		this.machineNum=num;
		this.machineName=name;
		this.speedMeterPerSec=speed;
		this.CutTimeSec=cut;
		this.BendingTimeSec=bend;
		this.ChargingTimeSec=charge;
	}
	
	public int getId() {
		return id;
	}
	public int getNumOfMachine() {
		return machineNum;
	}
	public String getNameOfMachine() {
		return machineName;
	}
	public int getMachineSpeed() {
		return  speedMeterPerSec;
	}
	public float getMachineCutingTime() {
		return CutTimeSec;
	}
	public float getMachineBendingTime() {
		return BendingTimeSec;
	}
	public int getMachineChargingTime() {
		return ChargingTimeSec;
	}
        public void printMachine() {
  	  System.out.println( " "+id+" "+machineNum+" "+machineName+" "+speedMeterPerSec+" "+CutTimeSec+
              " "+BendingTimeSec+" "+ChargingTimeSec); 
        } 
}