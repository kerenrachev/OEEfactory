 
package Objects;
 
public class Shift {
    
    private String shiftType;
    private String shiftStart;
    private String shiftEnd;
    private String coffeBreak1;
    private String coffeBreak2;
    private String foodBreak1;
    
    public Shift(String shiftType, String shiftStart,String shiftEnd, String coffeBreak1,String coffeBreak2,String foodBreak1)
    {
        this.shiftType   = shiftType;
        this.shiftStart  = shiftStart;
        this.shiftEnd    = shiftEnd;
        this.coffeBreak1 = coffeBreak1;
        this.coffeBreak2 = coffeBreak2;
        this.foodBreak1  = foodBreak1;
    }
    
    public String getType(){
        return shiftType;
    }
    public String getShiftStart(){
        return shiftStart;
    }
    public String getShiftEnd(){
        return shiftEnd;
    }
    public String getCoffeBreak1(){
        return coffeBreak1;
    }
    public String getCoffeBreak2(){
        return coffeBreak2;
    }
    public String getFoodBreak1(){
        return foodBreak1;
    }
}
