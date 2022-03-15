package it.unibo.radarSystem22.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.mock.LedMock;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class TestLed {
	
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
	public void testLedMock() {
		
		// Non uso il file json di configurazione
		DomainSystemConfig.simulation = true;
		
		ILed led = DeviceFactory.createLed();
		assertTrue( ! led.getState() );
		
		led.turnOn();
		assertTrue( led.getState() );
		
		BasicUtils.delay(1000);
		
		led.turnOff();
		assertTrue( ! led.getState() );
		
		BasicUtils.delay(1000);
		
	}
	
	@Test
	public void testLedMockSpento() {
		
		ILed led = new LedMock();

		led.turnOff();
		assertTrue( ! led.getState() );
	}
	
		
}
