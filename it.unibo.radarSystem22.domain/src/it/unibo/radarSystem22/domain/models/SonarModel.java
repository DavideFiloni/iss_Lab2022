package it.unibo.radarSystem22.domain.models;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.concrete.SonarConcrete;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.mock.SonarMock;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public abstract class SonarModel implements ISonar {
	 protected boolean stopped = false; //se true il sonar si ferma
	 protected  IDistance curVal = new Distance(90);
	
	
	//Factory methods
	  public static ISonar create() {
	    ISonar sonar;
	    if( DomainSystemConfig.simulation ) 
	    	sonar = createSonarMock();
	    else 
	    	sonar = createSonarConcrete();
	    return sonar;
	  }
	  
	  public static ISonar createSonarMock(){
		  return new SonarMock(); 
		  }
	  
	  public static ISonar createSonarConcrete(){
		  return new SonarConcrete();
		  }
	  
	//Abstract methods
	  protected abstract void sonarSetUp() ;
	  protected abstract void sonarProduce();
	  
	  protected void updateDistance( int d ) {
			curVal = new Distance( d );
			ColorsOut.out("SonarModel | updateDistance "+ d, ColorsOut.BLUE);
		}
	  
	  @Override
		public boolean isActive() {
			//ColorsOut.out("SonarModel | isActive "+ (! stopped), ColorsOut.GREEN);
			return ! stopped;
		}
		
		@Override
		public IDistance getDistance() {
			return curVal;
		}
		
		@Override
		public void activate() {
			curVal = new Distance( 90 );
	 		ColorsOut.out("SonarModel | activate" );
			stopped = false;
			new Thread() {
				public void run() {
					while( ! stopped  ) {
						//Colors.out("SonarModel | call produce", Colors.GREEN);
						sonarProduce(  );
						//Utils.delay(RadarSystemConfig.sonarDelay);
					}
					ColorsOut.out("SonarModel | ENDS" );
			    }
			}.start();
		}
	 	
		@Override
		public void deactivate() {
			ColorsOut.out("SonarModel | deactivate", ColorsOut.BgYellow );
			stopped = true;
		}


}
