package it.unibo.radarSystem22.domain.models;

import it.unibo.radarSystem22.domain.concrete.SonarConcrete;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.mock.SonarMock;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public abstract class SonarModel {
	
	
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


}
