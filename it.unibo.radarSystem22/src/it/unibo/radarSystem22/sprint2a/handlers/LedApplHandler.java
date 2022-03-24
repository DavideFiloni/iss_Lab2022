package it.unibo.radarSystem22.sprint2a.handlers;

import it.unibo.comm2022.ApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.utils.ColorsOut;

public class LedApplHandler extends ApplMsgHandler {
	private ILed led;
	
	public LedApplHandler (String name, ILed led) {
		super(name);
		this.led = led;
	}

	@Override
	public void elaborate(String message, Interaction2021 conn) {
		ColorsOut.out(name + " | elaborate " + message + " conn=" + conn);
		switch (message) {
		case "off" : led.turnOff(); break;
		case "on" : led.turnOn(); break;
		case "getState" : this.sendMsgToClient(led.getState()+"", conn); break;
		default : break;
		}
		
	}
	
	

}
