package it.unibo.radarSystem22.domain.observer.interfaces;

import it.unibo.radarSystem22.domain.interfaces.ISonar;

public interface ISonarObservable extends ISonar {
	public void register (IObserver observer);
	public void unregister (IObserver observer);

}
