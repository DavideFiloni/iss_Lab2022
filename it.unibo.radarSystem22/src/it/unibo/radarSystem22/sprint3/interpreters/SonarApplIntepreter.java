package it.unibo.radarSystem22.sprint3.interpreters;

import it.unibo.radarSystem22.domain.interfaces.ISonar;

public class SonarApplIntepreter implements IApplIntepreter{
	private ISonar sonar;
	
	public SonarApplIntepreter (ISonar sonar) {
		this.sonar = sonar;
	}

	@Override
	public String elaborate(String message) {
		
		if(message.equals("getDistance")) return ""+sonar.getDistance().getVal();
		else if(message.equals("isActive")) return ""+sonar.isActive();
		else if(message.equals("activate")) sonar.activate();
		else if (message.equals("deactivate")) sonar.deactivate();
		return message+"_done";
	}

}
