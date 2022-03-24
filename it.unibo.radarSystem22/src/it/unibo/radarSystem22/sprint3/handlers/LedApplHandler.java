package it.unibo.radarSystem22.sprint3.handlers;

import it.unibo.comm2022.ApplMsgHandler;
import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.sprint3.interpreters.IApplIntepreter;
import it.unibo.radarSystem22.sprint3.interpreters.LedApplIntepreter;


public class LedApplHandler extends ApplMsgHandler{
	private IApplIntepreter ledInterp;
	
	// Factory
	 public static IApplMsgHandler create(String name, ILed led) {
		    return new LedApplHandler(name, led);
		  }

	private LedApplHandler(String name, ILed led) {
		super(name);
		ledInterp = new LedApplIntepreter(led);
	}

	@Override
	public void elaborate(String message, Interaction2021 conn) {
		
		if(message.equals("getState"))
			sendAnswerToClient(ledInterp.elaborate(message), conn);
		else
			ledInterp.elaborate(message);
		
	}

}
