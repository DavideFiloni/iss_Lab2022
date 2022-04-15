package it.unibo.radarSystem22.domain.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class DomainSystemConfig {
	public static  boolean simulation    = true;
 	public static  boolean ledGui        = false;
	public static  boolean webCam        = false;
 
	public static int sonarDelay          =  100;     
	public static int sonarDistanceMax    =  150;     
	public static boolean sonarObservable =  false;     
	public static int DLIMIT              =  15;     
	public static int testingDistance     =  DLIMIT - 2;     
    
	public static boolean tracing         = false;	
	public static boolean testing         = false;			
	

	public static void setTheConfiguration(  ) {
		setTheConfiguration("../DomainSystemConfig.json");
	}
	
	public static void setTheConfiguration( String resourceName ) {
		//Nella distribuzione resourceName � in una dir che include la bin  
		InputStreamReader fis = null;
		try {
			ColorsOut.out("%%% setTheConfiguration from file:" + resourceName);
			if(  fis == null ) {
 				 fis = new InputStreamReader(new FileInputStream(resourceName));
			}
	        JSONTokener tokener = new JSONTokener(fis);
	        JSONObject object   = new JSONObject(tokener);
	 		
	        simulation          = object.getBoolean("simulation");
	        
	        
	        webCam           = object.getBoolean("webCam");
	        
	        sonarObservable  = object.getBoolean("sonarObservable");	
	        sonarDelay       = object.getInt("sonarDelay");	
	        sonarDistanceMax = object.getInt("sonarDistanceMax");	
	        DLIMIT           = object.getInt("DLIMIT");	
	        tracing          = object.getBoolean("tracing");
	        testing          = object.getBoolean("testing");
	        ledGui			 = object.getBoolean("ledGui");
	        
 	        
		} catch (FileNotFoundException | JSONException e) {
 			ColorsOut.outerr("setTheConfiguration ERROR " + e.getMessage() );
		} 
	}	
	 
}
