package unibo.actor22.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import unibo.actor22comm.utils.ColorsOut;



public class MainSelectOnRasp {
public HashMap<String,IApplication> programs = new HashMap<String,IApplication>();
	
	protected void outMenu() {
		for (String i : programs.keySet()) { //
			  System.out.println( ""+i + "    " + programs.get(i).getName() );
		}
 	}
	public void doChoice() {
		try {
			programs.put("1", new RSActorOnRasp() );				 
			programs.put("2", new RSActor22DistribOnRasp());  	 
			/*programs.put("3", new RadarSysSprint2aDevicesOnRaspMain()); 
			programs.put("4", new RadarSysSprint3DevicesOnRaspMain());*/ 
  			String i = "";
			outMenu();
			ColorsOut.outappl(">>>   ", ColorsOut.ANSI_PURPLE);
 			BufferedReader inputr = new BufferedReader(new InputStreamReader(System.in));
			i =  inputr.readLine();
 			programs.get( i ).doJob("DomainSystemConfig.json","RadarSystemConfig.json");
 		} catch ( Exception e) {
			 ColorsOut.outerr("ERROR:" + e.getMessage() );
		}
		
	}
	public static void main( String[] args) throws Exception {
		ColorsOut.outappl("---------------------------------------------------", ColorsOut.BLUE);
		ColorsOut.outappl("MainSelectOnRasp: this application uses Config Files", ColorsOut.BLUE);
		ColorsOut.outappl("---------------------------------------------------", ColorsOut.BLUE);
		new MainSelectOnRasp().doChoice();
	}
}
