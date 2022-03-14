package it.unibo.radarSystem22.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.mock.SonarMock;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class TestSonar {
	
	// Fatto prima di ogni test
	@Before
	public void up() {
		System.out.println("up");
	}
		
	// Fatto dopo ogni test
	@After
	public void down() {
		System.out.println("down");
	}
		
	@Test
	public void testSonarMock() {
		
			
			DomainSystemConfig.simulation = true;
			int timeToSleep = 5000;
			
			ISonar sonar = DeviceFactory.createSonar();
			assertTrue( ! sonar.isActive() );
			
			sonar.activate();
			assertTrue( sonar.isActive() );
			
			int initDistance = sonar.getDistance().getVal();
			
			BasicUtils.delay(timeToSleep);
			
			int finalDistance = sonar.getDistance().getVal();
			
			assertEquals(timeToSleep/500, initDistance-finalDistance);
			
			
			sonar.deactivate();
			assertTrue( ! sonar.isActive() );
			
			
		}

}
