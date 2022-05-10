package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit test for Patient class
class PatientTest {

    private Patient p1;

    @BeforeEach
    public void setUp() {
        p1 = new Patient("Gregor", BloodType.A, 1000, true);
    }

    @Test
    public void testGetters() {
        assertEquals("Gregor", p1.getName());
        assertEquals(BloodType.A, p1.getBloodType());
        assertEquals(1000, p1.getRequiredAmount());
        assertTrue(p1.isUrgent());
        assertEquals("Name :GregorBlood Type: ARequired Amount: 1000mlUrgent? true", p1.getPatientInfo());
    }
}