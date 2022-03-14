package it.unibo.radarSystem22.domain;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.models.LedModel;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class DeviceFactory {
	
	public static ILed createLed() {
		//Colors.out("DeviceFactory | createLed simulated="+RadarSystemConfig.simulation);
		if( DomainSystemConfig.simulation)  {
			return LedModel.createLedMock();
		} else {
			return LedModel.createLedConcrete();
		}
	}
	
	public static ISonar createSonar() {
		//Colors.out("DeviceFactory | createLed simulated="+RadarSystemConfig.simulation);
		if( DomainSystemConfig.simulation)  {
			return SonarModel.createSonarMock();
		} else {
			return SonarModel.createSonarConcrete();
		}
	}

}
