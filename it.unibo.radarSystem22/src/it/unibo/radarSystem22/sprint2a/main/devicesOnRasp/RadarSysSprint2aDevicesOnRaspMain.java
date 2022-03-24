package it.unibo.radarSystem22.sprint2a.main.devicesOnRasp;

import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.tcp.TcpServer;
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

/*
 * Attiva il TCPServer.
 * 
 */

public class RadarSysSprint2aDevicesOnRaspMain implements IApplication {
	private TcpServer ledServer;
	private TcpServer sonarServer;
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
	    	DomainSystemConfig.ledGui      = false;	
	
			RadarSystemConfig.tracing           = false;		
			RadarSystemConfig.RadarGuiRemote    = true;	
		}
	}
	
	
	protected void configure() {		
 	   sonar      = DeviceFactory.createSonar();
 	   IApplMsgHandler sonarh = new SonarApplHandler("sonarh", sonar);
 	   sonarServer = new TcpServer("sonarServer", RadarSystemConfig.sonarPort, sonarh);
 	   led        = DeviceFactory.createLed();
 	   IApplMsgHandler ledh = new LedApplHandler("ledh", led);
	   ledServer = new TcpServer("ledServer", RadarSystemConfig.ledPort, ledh);	 		
 	
	}
	
	protected void execute() {
		ledServer.activate();
		sonarServer.activate();
	}
	
	@Override
	public String getName() {
		return this.getClass().getName() ;  
	}
	
		
	public static void main( String[] args) throws Exception {
		BasicUtils.aboutThreads("At INIT with NO CONFIG files| ");
		new RadarSysSprint2aDevicesOnRaspMain().doJob("DomainSystemConfig.json","RadarSystemConfig.json");
	  }

}
