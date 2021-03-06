package unibo.actor22.main;

import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.Qak22Context;
import unibo.actor22.Qak22Util;
import unibo.actor22.annotations.ActorLocal;
import unibo.actor22.annotations.ActorRemote;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.ControllerActor;
import unibo.actor22.common.RadarSystemConfig;
import unibo.actor22comm.ProtocolType;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils;

@ActorLocal(name =     {ApplData.controllerName}, 
implement = {ControllerActor.class}
)

@ActorRemote(name =   {ApplData.ledName,ApplData.sonarName}, 
host=    {ApplData.raspAddr,ApplData.raspAddr}, 
port=    { ""+ApplData.ctxPort, ""+ApplData.ctxPort}, 
protocol={ "TCP" , "TCP" })

public class RSActor22DistribOnPC implements IApplication{
	//private EnablerContextForActors ctx;

	public void doJob(String domainConfig, String systemConfig) {
		setup( domainConfig, systemConfig);
		configure();
		execute();		
	}
	
	protected void setup( String domainConfig, String systemConfig )  {
	    CommSystemConfig.tracing            = true;
		if( domainConfig != null ) {
			DomainSystemConfig.setTheConfiguration(domainConfig);
		}
		else {
			DomainSystemConfig.simulation  = false;
	    	DomainSystemConfig.testing     = false;			
	    	DomainSystemConfig.tracing     = false;			
			DomainSystemConfig.sonarDelay  = 200;
	    	DomainSystemConfig.ledGui      = false;		//se siamo su PC
		}
		if( systemConfig != null ) {
			RadarSystemConfig.setTheConfiguration(systemConfig);
		}
		else {
			RadarSystemConfig.tracing           = false;		
			RadarSystemConfig.RadarGuiRemote    = false;		
			RadarSystemConfig.protcolType       = ProtocolType.tcp;	
			RadarSystemConfig.sonarObservable 	= false; 
			RadarSystemConfig.DLIMIT			=  10;
		}
 
	}
	
	protected void configure () {
		Qak22Context.handleLocalActorDecl(this);
 		Qak22Context.handleRemoteActorDecl(this);
		if( RadarSystemConfig.sonarObservable  ) {
 			Qak22Context.registerAsEventObserver(ApplData.controllerName, ApplData.evDistance);
		}
		
	}
	
	protected void execute() {
		Qak22Util.sendAMsg( ApplData.activateCrtl );
	}
	
	
	public static void main( String[] args) {
		CommUtils.aboutThreads("Before start - ");
		new RSActor22DistribOnPC().doJob("DomainSystemConfig.json", "RadarSystemConfig.json");
		CommUtils.aboutThreads("Before end - ");
	}

	@Override
	public String getName() {
		return  this.getClass().getName();
	}

}
