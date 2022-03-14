package it.unibo.radarSystem22.domain.mock;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.models.LedModel;
import it.unibo.radarSystem22.domain.utils.ColorsOut;

public class LedMock extends LedModel implements ILed {
	
	/* Non sono più necessarie perchè sono nel model
	private boolean state; 
	
	public LedMock() {
		this.state = false; // inizialmente spento
	}
	
	@Override
	public void turnOn() {
		this.state = true;
	}


	@Override
	public void turnOff() {
		this.state = false;
	}

	@Override
	public boolean getState() {
		return this.state;
	}*/


	@Override
	protected void ledActivate(boolean val) {
		ColorsOut.outappl("LedMock state=" + getState(), ColorsOut.MAGENTA );
		
	}

}
