package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit test for BloodDonor class
public class BloodDonorTest {

    private BloodDonor d1;

    @BeforeEach
    public void setUp() {
        d1 = new BloodDonor("Paul", 50, 70, BloodType.O);
    }

    @Test
    public void testGetters() {
        assertEquals("Paul", d1.getName());
        assertEquals(50, d1.getAge());
        assertEquals(70, d1.getWeight());
        assertEquals(BloodType.O, d1.getBloodType());
    }
     @Test
    public void testDonorInfo() {
         assertEquals("Name: Paul\n" +
                 "Age: 50\n" +
                 "Weight: 70.0\n" +
                 "Blood Type: O", d1.getDonorInfo());
     }

}
