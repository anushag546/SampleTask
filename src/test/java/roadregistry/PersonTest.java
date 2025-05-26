package roadregistry;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class PersonTest {


	 @Test
	    public void testValidAddPerson() {
	        Person p = new Person("23!_&$%gAZ", "John", "Doe", "12|Main Street|Melbourne|Victoria|Australia", "10-10-1990");
	        assertTrue(p.addPerson());
	    }

	    @Test
	    public void testInvalidPersonID() {
	        Person p = new Person("12abcdefYZ", "Jane", "Doe", "12|Main Street|Melbourne|Victoria|Australia", "10-10-1990");
	        assertFalse(p.addPerson());
	    }

	    @Test
	    public void testInvalidAddressFormat() {
	        Person p = new Person("23!_&$%gAZ", "Jane", "Smith", "12 Main Street Melbourne", "10-10-1990");
	        assertFalse(p.addPerson());
	    }

	    @Test
	    public void testInvalidBirthdayFormat() {
	        Person p = new Person("23!_&$%gAZ", "Steve", "King", "12|Main Street|Melbourne|Victoria|Australia", "1990-10-10");
	        assertFalse(p.addPerson());
	    }

	    @Test
	    public void testAllInvalidAddPerson() {
	        Person p = new Person("123", "Anna", "Lee", "InvalidAddress", "10/10/1990");
	        assertFalse(p.addPerson());
	    }

	    @Test
	    public void testValidDemeritPointsUnder21Suspended() {
	        Person p = new Person("23!_&$%gAZ", "Sam", "Young", "32|Street|Melbourne|Victoria|Australia", "01-01-2007");
	        p.addPerson();
	        assertEquals("Success", p.addDemeritPoints("01-01-2024", 7));
	        assertFalse(p.isSuspended());
	    }

	    @Test
	    public void testValidDemeritPointsOver21NotSuspended() {
	        Person p = new Person("23!_&$%gAZ", "Alex", "Old", "32|Street|Melbourne|Victoria|Australia", "01-01-1990");
	        p.addPerson();
	        assertEquals("Success", p.addDemeritPoints("01-01-2024", 6));
	        assertFalse(p.isSuspended());
	    }

	    @Test
	    public void testInvalidDemeritPointsValue() {
	        Person p = new Person("23!_&$%gAZ", "Ray", "Ban", "12|Street|Melbourne|Victoria|Australia", "01-01-2000");
	        assertEquals("Failed", p.addDemeritPoints("01-01-2024", 10));
	    }

	    @Test
	    public void testInvalidOffenseDateFormat() {
	        Person p = new Person("23!_&$%gAZ", "Ray", "Ban", "12|Street|Melbourne|Victoria|Australia", "01-01-2000");
	        assertEquals("Failed", p.addDemeritPoints("2024/01/01", 2));
	    }

	    @Test
	    public void testValidDemeritPointsEdgeSuspension() {
	        Person p = new Person("23!_&$%gAZ", "Ella", "Blue", "12|Street|Melbourne|Victoria|Australia", "01-01-2000");
	        p.addPerson();
	        p.addDemeritPoints("01-01-2023", 5);
	        assertEquals("Success", p.addDemeritPoints("01-01-2024", 2));
	        assertFalse(p.isSuspended());
	    }

	    // Placeholder test cases for updatePersonalDetails (to be completed after logic is added)
	    @Test
	    public void testUpdateWithValidDetails() {
	        Person p = new Person("23!_&$%gAZ", "Mira", "Joe", "12|Street|Melbourne|Victoria|Australia", "01-01-2000");
	        assertFalse(p.updatePersonalDetails("23!_&$%gAZ", "Mira", "Joe", "15|New Street|Melbourne|Victoria|Australia", "01-01-2000"));
	    }

	    @Test
	    public void testUpdateAddressUnder18() {
	        Person p = new Person("23!_&$%gAZ", "Tom", "Kid", "12|Street|Melbourne|Victoria|Australia", "01-01-2010");
	        assertFalse(p.updatePersonalDetails("23!_&$%gAZ", "Tom", "Kid", "99|Change|Melbourne|Victoria|Australia", "01-01-2010"));
	    }

	    @Test
	    public void testChangeBirthdayOnly() {
	        Person p = new Person("23!_&$%gAZ", "Lisa", "Chan", "12|Street|Melbourne|Victoria|Australia", "01-01-1995");
	        assertFalse(p.updatePersonalDetails("23!_&$%gAZ", "Lisa", "Chan", "12|Street|Melbourne|Victoria|Australia", "02-02-1995"));
	    }

	    @Test
	    public void testEvenIDCannotChange() {
	        Person p = new Person("24!_&$%gAZ", "Rick", "Even", "12|Street|Melbourne|Victoria|Australia", "01-01-1990");
	        assertFalse(p.updatePersonalDetails("99!_&$%gAZ", "Rick", "Even", "12|Street|Melbourne|Victoria|Australia", "01-01-1990"));
	    }

	    @Test
	    public void testUpdateAllInvalidDetails() {
	        Person p = new Person("12", "Fail", "Case", "BadAddress", "wrongDate");
	        assertFalse(p.updatePersonalDetails("xx", "Fail", "Case", "NewBad", "wrongAgain"));
	    }

}
