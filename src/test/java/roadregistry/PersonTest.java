package roadregistry;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testValidDemeritPointsUnder21Suspended() {
        Person p = new Person("23!_&%gAZ", "Sam", "Young", "32|Street|Melbourne|Victoria|Australia", "01-01-2007");
        p.addPerson();
        assertEquals("Success", p.addDemeritPoints("01-01-2024", 7));
        assertTrue(p.isSuspended());
    }

    @Test
    public void testValidDemeritPointsOver21NotSuspended() {
        Person p = new Person("23!_&%gAZ", "Alex", "Old", "32|Street|Melbourne|Victoria|Australia", "01-01-1990");
        p.addPerson();
        assertEquals("Success", p.addDemeritPoints("01-01-2024", 6));
        assertFalse(p.isSuspended());
    }

    @Test
    public void testInvalidDemeritPointsValue() {
        Person p = new Person("23!_&%gAZ", "Ray", "Ban", "12|Street|Melbourne|Victoria|Australia", "01-01-2000");
        assertEquals("Failed", p.addDemeritPoints("01-01-2024", 10));
    }

    @Test
    public void testInvalidOffenseDateFormat() {
        Person p = new Person("23!_&%gAZ", "Ray", "Ban", "12|Street|Melbourne|Victoria|Australia", "01-01-2000");
        assertEquals("Failed", p.addDemeritPoints("2024/01/01", 2));
    }
}
