 
package Objects;
 

public class Material {
  

	private float diameterMM;
	private float weightMeterPerKilo;
	private float numOfWiresFormatS1;
	private float numOfWiresFormatS2;
	private float numOfWiresFormat14;
	private float numOfWiresFormat16;
	private float miniSyntax;
	private float planet20;
	private float EvgPbc;
	private float RaXa;
	
	public Material(float diameter, float weight, float s1, float s2, float w14, float w16,
			float syntax, float planet, float EP, float Ra) {
		this.diameterMM=diameter;
		this.weightMeterPerKilo=weight;
		this.numOfWiresFormatS1=s1;
		this.numOfWiresFormatS2=s2;
		this.numOfWiresFormat14=w14;
		this.numOfWiresFormat16=w16;
		this.miniSyntax=syntax;
		this.planet20=planet;
		this.EvgPbc=EP;
		this.RaXa=Ra;
	} 
        public float getdiamterMM(){
            return diameterMM;
        }
        public float getWeightMeterPerKil(){
            return weightMeterPerKilo;
        }
        public float getnumOfWiresFormatS1(){
            return numOfWiresFormatS1;
        }
        public float getnumOfWiresFormatS2(){
            return numOfWiresFormatS2;
        }
        public float getnumOfWiresFormat14(){
            return numOfWiresFormat14;
        }
        public float getnumOfWiresFormat16(){
            return numOfWiresFormat16;
        }
        public float getminiSyntax(){
            return miniSyntax;
        }
        public float getplanet20(){
            return planet20;
        }
        public float getEvgPbc(){
            return EvgPbc;
        }
        public float getRaXa(){
            return RaXa;
        }
	public void printMaterial(){
	        System.out.println(diameterMM+" "+weightMeterPerKilo+" "+numOfWiresFormatS1+" "+numOfWiresFormatS2+
	                " "+numOfWiresFormat14+" "+numOfWiresFormat16+" "+miniSyntax+" "+planet20+" "+EvgPbc+
	                " "+RaXa); 
	}

}
