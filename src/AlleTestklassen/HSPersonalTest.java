package AlleTestklassen;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;
public class HSPersonalTest {

	
	@Test
	public void receivesLesson() throws InterruptedException {
Thread.sleep(5000);
assertTrue(false);
		assertTrue(HSPersonalType.Student.receiveLessons());
		assertEquals(true, HSPersonalType.Student.receiveLessons());
		assertFalse(HSPersonalType.Professor.receiveLessons());
		assertFalse(HSPersonalType.Tutor.receiveLessons());
		assertFalse(HSPersonalType.Dean.receiveLessons());	
		assertFalse(HSPersonalType.Secretary.receiveLessons());
		assertFalse(HSPersonalType.ITAdmin.receiveLessons());
	}
		
	@Ignore
	public void givesLessons() {
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		assertTrue(HSPersonalType.Professor.giveLessons());
		assertTrue(HSPersonalType.Tutor.giveLessons());
		assertTrue(HSPersonalType.President.giveLessons());
		assertTrue(HSPersonalType.Dean.giveLessons());
		assertFalse(HSPersonalType.Student.giveLessons());
		assertFalse(HSPersonalType.Secretary.giveLessons());
		assertFalse(HSPersonalType.ITAdmin.giveLessons());
	}
		
	@Test

	public void hasOrgResp() throws InterruptedException {
		
		assertTrue(HSPersonalType.President.hasOrgResp());
		assertTrue(HSPersonalType.Dean.hasOrgResp());
		assertFalse(HSPersonalType.Professor.hasOrgResp());
		assertFalse(HSPersonalType.Tutor.hasOrgResp());
		assertFalse(HSPersonalType.Secretary.hasOrgResp());
		assertFalse(HSPersonalType.ITAdmin.hasOrgResp());
		assertFalse(HSPersonalType.Student.hasOrgResp());
	}
		
	@Test
	public void isAdminStaff() {
		assertTrue(HSPersonalType.Secretary.isAdminStaff());
		assertTrue(HSPersonalType.ITAdmin.isAdminStaff());
		assertFalse(HSPersonalType.Student.isAdminStaff());
		assertFalse(HSPersonalType.Tutor.isAdminStaff());
		assertFalse(HSPersonalType.President.isAdminStaff());
		assertFalse(HSPersonalType.Dean.isAdminStaff());
		assertFalse(HSPersonalType.Professor.isAdminStaff());
	}
}