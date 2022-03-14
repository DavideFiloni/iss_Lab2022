package it.unibo.radarSystem22.domain.mock;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.utils.BasicUtils;

public class SonarMock extends SonarModel implements ISonar {
	private boolean stato;
	private IDistance curVal;
	private int delta;
	
	public SonarMock() {
		this.stato = false;
		this.curVal = new Distance(90);
		this.delta = 1;
		
	}
	
	@Override
	public void activate() {
		this.stato = true;
		new Thread() {
			public void run() {
				while (stato) {
					curVal = new Distance (curVal.getVal()-delta);
					BasicUtils.delay(500);
				}
			}
				
		}.start();
	}

	@Override
	public void deactivate() {
		this.stato = false;	
	}

	@Override
	public IDistance getDistance() {
		return curVal;
	}

	@Override
	public boolean isActive() {
		return this.stato;
	}

}
