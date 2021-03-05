package AlleTestklassen;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Rule;
public class TestClass {



@Test
public void testcompareTo() {
	String test= new String("Was geht");
	assertTrue("Beide Wörter sind nicht identisch",test.compareTo("Was geht")==0);
}

@Test
public void testsubString() {
	String test=new String("Hochhaus");
	assertEquals("Teilstring ist falsch","Hoch",test.substring(0,4));
	assertTrue("Teilstring ist falsch",test.substring(0,4).equals("Hoch"));	
}
	

}
