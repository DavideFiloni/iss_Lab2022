package it.unibo.radarSystem22.domain.observer.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.observer.interfaces.IObserver;
import it.unibo.radarSystem22.domain.observer.interfaces.ISonarObservable;
import it.unibo.radarSystem22.domain.utils.ColorsOut;

public abstract class SonarObservableModel  extends SonarModel implements ISonarObservable {
	private List<IObserver> observers = new ArrayList<IObserver>();;
	

	@Override
	public void register(IObserver observer) {
		observers.add(observer);
		
	}

	@Override
	public void unregister(IObserver observer) {
		observers.remove(observer);
		
	}

	@Override
	protected void sonarSetUp() {
		curVal = new Distance(90);	
	}

	@Override
	protected void updateDistance (int d) {
		if(d != curVal.getVal())
			updateObserver(d);
		curVal = new Distance( d );
		ColorsOut.out("SonarObservableModel | updateDistance "+ d, ColorsOut.BLUE);
		
	}
	
	private void updateObserver(int d) {
		for(IObserver obs : observers) {
			obs.update(""+d);
		}
	}


}
