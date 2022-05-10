package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.BloodBank.isEligible;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for BloodBank class
public class BloodBankTest {

    private BloodBank vancouver;
    private BloodDonor d1;
    private BloodDonor d2;
    private BloodDonor d3;
    private BloodDonor d4;
    private BloodDonor d5;
    private BloodDonor d6;
    private BloodDonor d7;
    private Patient p1;
    private Patient p2;
    private Patient p3;
    private Patient p4;
    private Patient p5;
    private Patient p6;
    private Patient p7;
    private Patient p8;
    private Patient p9;
    private Patient p10;
    private Patient p11;
    private Patient p12;
    private Patient p13;
    private Patient p14;
    private Patient p15;
    private Patient p16;
    private Patient p17;
    private Patient p18;
    private Patient p19;

    @BeforeEach
    public void setUp1() {
        vancouver = new BloodBank("Vancouver");
        d1 = new BloodDonor("Paul1", 16, 49, BloodType.A);
        d2 = new BloodDonor("Paul2", 17, 49, BloodType.B);
        d3 = new BloodDonor("Paul3", 16, 50, BloodType.AB);
        d4 = new BloodDonor("Paul4", 17, 50, BloodType.O);
        d5 = new BloodDonor("Paul5", 20, 60, BloodType.A);
        d6 = new BloodDonor("Paul6", 30, 50, BloodType.AB);
        d7 = new BloodDonor("Paul7", 50, 55, BloodType.B);
    }

    @BeforeEach
    public void setUp2() {
        p1 = new Patient("Gregor1", BloodType.A, 1000, true);
        p2 = new Patient("Gregor2", BloodType.A, 1001, true);
        p3 = new Patient("Gregor3", BloodType.A, 1000, false);
        p4 = new Patient("Gregor4", BloodType.A, 2000, true);
        p5 = new Patient("Gregor5", BloodType.A, 2001, true);
    }

    @BeforeEach
    public void setUp3() {
        p6 = new Patient("Gregor6", BloodType.B, 1000, true);
        p7 = new Patient("Gregor7", BloodType.B, 1001, true);
        p8 = new Patient("Gregor8", BloodType.B, 1000, false);
        p9 = new Patient("Gregor9", BloodType.B, 2000, true);
        p10 = new Patient("Gregor10", BloodType.B, 2001, true);
    }

    @BeforeEach
    public void setUp4() {
        p11 = new Patient("Gregor11", BloodType.AB, 1000, true);
        p12 = new Patient("Gregor12", BloodType.AB, 1001, true);
        p13 = new Patient("Gregor13", BloodType.AB, 1000, false);
        p14 = new Patient("Gregor14", BloodType.AB, 2000, true);
        p15 = new Patient("Gregor15", BloodType.AB, 2001, true);
    }

    @BeforeEach
    public void setUp5() {
        p16 = new Patient("Gregor16", BloodType.O, 2000, true);
        p17 = new Patient("Gregor17", BloodType.O, 2000, false);
        p18 = new Patient("Gregor18", BloodType.O, 2001, true);
        p19 = new Patient("Gregor19", BloodType.O, 1000, true);
    }

    @Test
    public void testGetters() {
        assertEquals("Vancouver", vancouver.getLocation());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertNotNull(vancouver.getDonorList());
        assertTrue(vancouver.getDonorList().isEmpty());
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertNotNull(vancouver.getUrgentPatientWaitList());
        assertTrue(vancouver.getUrgentPatientWaitList().isEmpty());
    }

    @Test
    public void testIsNotEligible() {
        assertFalse(isEligible(d1));
        assertFalse(isEligible(d2));
        assertFalse(isEligible(d3));
    }

    @Test
    public void testIsEligible() {
        assertTrue(isEligible(d4));
        assertTrue(isEligible(d5));
        assertTrue(isEligible(d6));
    }

    @Test
    public void testDonateBloodA() {
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        vancouver.donateBlood(d4);
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        vancouver.donateBlood(d5);
        assertEquals(1450, vancouver.getTotalAmountOfTypeA());
    }

    @Test
    public void testDonateBloodB() {
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        vancouver.donateBlood(d5);
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        vancouver.donateBlood(d7);
        assertEquals(1450, vancouver.getTotalAmountOfTypeB());
    }

    @Test
    public void testDonateBloodAB() {
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        vancouver.donateBlood(d5);
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        vancouver.donateBlood(d6);
        assertEquals(1450, vancouver.getTotalAmountOfTypeAB());
    }

    @Test
    public void testDonateBloodO() {
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        vancouver.donateBlood(d6);
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        vancouver.donateBlood(d4);
        assertEquals(2450, vancouver.getTotalAmountOfTypeO());
    }

    @Test
    public void testNotUrgentUseBlood() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals("Sorry, you have to wait.", vancouver.useBlood(p3));
        assertEquals(p3, vancouver.getPatientWaitList().get(0));
        assertEquals("Sorry, you have to wait.", vancouver.useBlood(p8));
        assertEquals(p8, vancouver.getPatientWaitList().get(1));
        assertEquals("Sorry, you have to wait.", vancouver.useBlood(p13));
        assertEquals(p13, vancouver.getPatientWaitList().get(2));
        assertEquals("Sorry, you have to wait.", vancouver.useBlood(p17));
        assertEquals(p17, vancouver.getPatientWaitList().get(3));
    }

    @Test
    public void testUseBloodAWhenAIsEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("You have received 1000ml Type A Blood", vancouver.useBlood(p1));
        assertEquals(0, vancouver.getTotalAmountOfTypeA());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodAWhenOIsEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("You have received 1001ml Type O Blood", vancouver.useBlood(p2));
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(999, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodAWhenOIsJustEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("You have received 2000ml Type O Blood", vancouver.useBlood(p4));
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(0, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodAWhenOIsNotEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("Sorry, you have to wait.", vancouver.useBlood(p5));
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals(p5, vancouver.getUrgentPatientWaitList().get(0));
    }

    @Test
    public void testUseBloodBWhenBIsEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("You have received 1000ml Type B Blood", vancouver.useBlood(p6));
        assertEquals(0, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodBWhenOIsEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("You have received 1001ml Type O Blood", vancouver.useBlood(p7));
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(999, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodBWhenOIsJustEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        vancouver.useBlood(p9);
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(0, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodBWhenOIsNotEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("Sorry, you have to wait.", vancouver.useBlood(p10));
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals(p10, vancouver.getUrgentPatientWaitList().get(0));
    }

    @Test
    public void testUseBloodABWhenABIsEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("You have received 1000ml Type AB Blood", vancouver.useBlood(p11));
        assertEquals(0, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodABWhenAIsEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        vancouver.donateBlood(d5);
        assertEquals(1450, vancouver.getTotalAmountOfTypeA());
        assertEquals("You have received 1001ml Type A Blood", vancouver.useBlood(p12));
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(449, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodABWhenBIsEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        vancouver.donateBlood(d7);
        assertEquals(1450, vancouver.getTotalAmountOfTypeB());
        assertEquals("You have received 1001ml Type B Blood", vancouver.useBlood(p12));
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(449, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodABWhenOIsEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("You have received 1001ml Type O Blood", vancouver.useBlood(p12));
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(999, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodABWhenOIsJustEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("You have received 2000ml Type O Blood", vancouver.useBlood(p14));
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(0, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodABWhenOIsNotEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("Sorry, you have to wait.", vancouver.useBlood(p15));
        assertEquals(1000, vancouver.getTotalAmountOfTypeAB());
        assertEquals(1000, vancouver.getTotalAmountOfTypeA());
        assertEquals(1000, vancouver.getTotalAmountOfTypeB());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals(p15, vancouver.getUrgentPatientWaitList().get(0));
    }

    @Test
    public void testUseBloodOWhenOIsEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("You have received 1000ml Type O Blood", vancouver.useBlood(p19));
        assertEquals(1000, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodOWhenOIsJustEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("You have received 2000ml Type O Blood", vancouver.useBlood(p16));
        assertEquals(0, vancouver.getTotalAmountOfTypeO());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
    }

    @Test
    public void testUseBloodOWhenOIsNotEnough() {
        assertNotNull(vancouver.getPatientWaitList());
        assertTrue(vancouver.getPatientWaitList().isEmpty());
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals("Sorry, you have to wait.", vancouver.useBlood(p18));
        assertEquals(2000, vancouver.getTotalAmountOfTypeO());
        assertEquals(p18, vancouver.getUrgentPatientWaitList().get(0));
    }


}






