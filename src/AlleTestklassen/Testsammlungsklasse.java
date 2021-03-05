package AlleTestklassen;

import org.junit.*;

import static org.junit.Assert.*;


import java.util.ArrayList;

import javax.swing.border.EmptyBorder;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;

public class Testsammlungsklasse extends AlphabeticIndexMapA {
	private static AlphabeticIndexMapA alphabeticIndexMap;
	
	
	@BeforeClass
	public static void beforeClass() {
		 alphabeticIndexMap = new AlphabeticIndexMapA();
		//alphaIndexMap2 = new AlphabeticIndexMap();
		
	}
	@Test //2c
	public void testStringToDebug() {
		try {
			assertEquals(alphabeticIndexMap.toDebugString().contains("z"), false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		@Test //2e
	public void testputMethod() {
		assertEquals(alphabeticIndexMap.put("O",0),0);
		assertEquals(alphabeticIndexMap.put("B", 1), 1);
		assertEquals(alphabeticIndexMap.put("C",2),2);
	}
}