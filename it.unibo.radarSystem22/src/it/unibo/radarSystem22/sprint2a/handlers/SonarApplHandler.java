package it.unibo.radarSystem22.sprint2a.handlers;

import it.unibo.comm2022.ApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.ColorsOut;

public class SonarApplHandler extends ApplMsgHandler {
	private ISonar sonar;
	
	public SonarApplHandler (String name, ISonar sonar) {
		super(name);
		this.sonar = sonar;
	}

	@Override
	public void elaborate(String message, Interaction2021 conn) {
		ColorsOut.out(name + " | elaborate " + message + " conn=" + conn);
		switch (message) {
		case "active" : sonar.activate();; break;
		case "deactive" : sonar.deactivate();; break;
		case "isActive" : this.sendMsgToClient(sonar.isActive()+"", conn); break;
		case "getDistance" : this.sendMsgToClient(sonar.getDistance().getVal()+"", conn); break;
		default : break;
		}
		
	}

}
