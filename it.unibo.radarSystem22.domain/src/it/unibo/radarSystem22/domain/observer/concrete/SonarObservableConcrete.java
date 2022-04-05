package it.unibo.radarSystem22.domain.observer.concrete;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.observer.model.SonarObservableModel;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;


public class SonarObservableConcrete extends SonarObservableModel implements ISonar{
	private  BufferedReader reader ;
	private Process p ;
	
	/*
	 * curVal è usata come valore della distanza corrente misurata
	 */
	@Override
	protected void sonarSetUp() {//called by SonarModel constructor
		curVal = new Distance(90);	
	}

	
	@Override
	public void activate() {
		ColorsOut.out("SonarObservableConcrete | activate ");
 		if( p == null ) { 
 	 		try {
 				p       = Runtime.getRuntime().exec("sudo ./SonarAlone");
 		        reader  = new BufferedReader( new InputStreamReader(p.getInputStream()));
 		        ColorsOut.out("SonarObservableConcrete | sonarSetUp done");
 	       	}catch( Exception e) {
 	       		ColorsOut.outerr("SonarObservableConcrete | sonarSetUp ERROR " + e.getMessage() );
 	    	}
 		}
 		super.activate();
 	}
	
	@Override
	protected void sonarProduce( ) {
        try {
			String data = reader.readLine();
			if( data == null ) return;
			int v = Integer.parseInt(data);
			ColorsOut.out("SonarObservableConcrete | v=" + v );
			int lastSonarVal = curVal.getVal();
			if( lastSonarVal != v) {
				if (v > DomainSystemConfig.sonarDistanceMax) //Eliminiamo dati del tipo 3430 //TODO: filtri in sottosistemi a stream
					updateDistance(DomainSystemConfig.sonarDistanceMax);
				else
					updateDistance( v );	 			
			}
       }catch( Exception e) {
       		ColorsOut.outerr("SonarObservableConcrete |  " + e.getMessage() );
       }		
	}
 
	@Override
	public void deactivate() {
		ColorsOut.out("SonarObservableConcrete | deactivate", ColorsOut.GREEN);
	    curVal = new Distance(90);
		if( p != null ) {
			p.destroy();  //Block the runtime process
			p=null;
		}
		super.deactivate();
 	}

 }
