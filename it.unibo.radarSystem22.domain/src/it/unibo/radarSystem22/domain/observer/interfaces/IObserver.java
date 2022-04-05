package it.unibo.radarSystem22.domain.observer.interfaces;

import java.util.Observer;

@SuppressWarnings("deprecation")
public interface IObserver extends Observer {
	
	public void update (String value);

}
