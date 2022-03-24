package it.unibo.radarSystem22.sprint3.handlers;

import it.unibo.comm2022.ApplMsgHandler;
import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.sprint3.interpreters.IApplIntepreter;
import it.unibo.radarSystem22.sprint3.interpreters.SonarApplIntepreter;

public class SonarApplHandler extends ApplMsgHandler {
private IApplIntepreter sonarInterp;
	
	// Factory
	 public static IApplMsgHandler create(String name, ISonar sonar) {
		    return new SonarApplHandler(name, sonar);
		  }

	private SonarApplHandler(String name, ISonar sonar) {
		super(name);
		sonarInterp = new SonarApplIntepreter(sonar);
	}

	@Override
	public void elaborate(String message, Interaction2021 conn) {
		
		if (message.equals("getDistance") || message.equals("isActive"))
			sendAnswerToClient(sonarInterp.elaborate(message), conn);
		else
			sonarInterp.elaborate(message);
		
	}

}
