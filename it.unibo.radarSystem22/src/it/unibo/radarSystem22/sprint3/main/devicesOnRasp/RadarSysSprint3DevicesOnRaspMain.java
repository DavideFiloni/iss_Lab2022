package it.unibo.radarSystem22.sprint3.main.devicesOnRasp;

import it.unibo.comm2022.ProtocolType;
import it.unibo.comm2022.enablers.EnablerAsServer;
import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.utils.CommSystemConfig;
import it.unibo.radarSystem22.IApplication;
import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import it.unibo.radarSystem22.sprint1.RadarSystemConfig;
import it.unibo.radarSystem22.sprint2a.handlers.LedApplHandler;
import it.unibo.radarSystem22.sprint2a.handlers.SonarApplHandler;

public class RadarSysSprint3DevicesOnRaspMain implements IApplication {
	private EnablerAsServer ledEnabler;
	private EnablerAsServer sonarEnabler;
	private ISonar sonar;
	private ILed led;
	
	@Override
	public void doJob(String domainConfig, String systemConfig) {
		setup( domainConfig, systemConfig);
		configure();
		execute();	
		
	}
	
	public void setup( String domainConfig, String systemConfig )  {
	    BasicUtils.aboutThreads(getName() + " | Before setup ");
	    CommSystemConfig.tracing            = true;
		if( domainConfig != null ) {
			DomainSystemConfig.setTheConfiguration(domainConfig);
		}
		if( systemConfig != null ) {
			RadarSystemConfig.setTheConfiguration(systemConfig);
		}
		if( domainConfig == null && systemConfig == null) {
			DomainSystemConfig.simulation  = true;
	    	DomainSystemConfig.testing     = false;			
	    	DomainSystemConfig.tracing     = false;			
			DomainSystemConfig.sonarDelay  = 200;
	    	DomainSystemConfig.ledGui      = true;		//se siamo su PC	
	
			RadarSystemConfig.tracing           = false;		
			RadarSystemConfig.RadarGuiRemote    = true;		
			RadarSystemConfig.protcolType       = ProtocolType.tcp;		
		}
 
	}
	
	protected void configure() {
		ProtocolType protocol = RadarSystemConfig.protcolType;
		sonar = DeviceFactory.createSonar();
		IApplMsgHandler sonarh = new SonarApplHandler("sonarh", sonar);
		led = DeviceFactory.createLed();
		IApplMsgHandler ledh = new LedApplHandler("ledh", led);
		
		// Enabler
		
		sonarEnabler = new EnablerAsServer("sonarSvr", RadarSystemConfig.sonarPort, protocol, sonarh);
		ledEnabler = new EnablerAsServer("ledSvr", RadarSystemConfig.ledPort, protocol, ledh);
	}
	
	protected void execute() {
		sonarEnabler.start();
		ledEnabler.start();
	}
	
	@Override
	public String getName() {
		return this.getClass().getName() ;  
	}
	
	public static void main( String[] args) throws Exception {
		BasicUtils.aboutThreads("At INIT with NO CONFIG files| ");
		new RadarSysSprint3DevicesOnRaspMain().doJob("DomainSystemConfig.json","RadarSystemConfig.json");
	  }


}
