package unibo.actor22.common;

import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import unibo.actor22.QakActor22;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

public class ControllerActor extends QakActor22 {
	private final int ITERATION = 30;
	protected IRadarDisplay radar;
	private int i;
	private boolean terminate;
	
	public ControllerActor (String name) {
		super(name);
		radar = DeviceFactory.createRadarGui();
		i=0;
		terminate = false;
	}
	
	protected void handleMsg(IApplMessage msg) {
		//System.out.println("ARRIVATO! "+i);
		if(!terminate) {
			if( msg.isReply() ) {
				elabReply(msg);
			}else { 
				elabCmd(msg);	
			}
		}
 	}
	
	protected void elabCmd(IApplMessage msg) {
		String msgCmd = msg.msgContent();
		//System.out.println("ARRIVATO! "+i);
		ColorsOut.outappl( getName()  + " | elabCmd=" + msgCmd + " obs=" + RadarSystemConfig.sonarObservable, ColorsOut.BLUE);
		if( msgCmd.equals(ApplData.cmdActivate)  ) {
			System.out.println("ARRIVATO! "+i);
 				sendMsg( ApplData.activateSonar);
				doControllerWork();
 		}		
	}
	
	protected void elabReply(IApplMessage msg) {
		ColorsOut.outappl( getName()  + " | elabReply=" + msg, ColorsOut.GREEN);
		this.i++;
		if( msg.msgId().equals(ApplData.reqDistance )) {
			String dStr = msg.msgContent();
			int d = Integer.parseInt(dStr);
			//Radar
			radar.update(dStr, "60");
			//LedUse case
			if( d <  RadarSystemConfig.DLIMIT ) {
				forward(ApplData.turnOnLed); 		
			}
			else {
				forward(ApplData.turnOffLed); 
			}
		}
		doControllerWork();
	}
	
	 protected void doControllerWork() {
			CommUtils.aboutThreads(getName()  + " |  Before doControllerWork " + RadarSystemConfig.sonarObservable );
			if (i >= ITERATION) terminate();
	    	if( ! RadarSystemConfig.sonarObservable) {
	    		CommUtils.delay(500);
	    		request( ApplData.askDistance );
	    	}
		}	
	 
	 protected void terminate() {
		 forward(ApplData.deactivateSonar);
		 forward(ApplData.turnOffLed);
		 terminate = true;
	 }

}
