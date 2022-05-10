package persistence;

import model.BloodDonor;
import model.BloodType;
import model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkDonor(String name, int age, double weight, BloodType bloodType, BloodDonor bloodDonor) {
        assertEquals(name, bloodDonor.getName());
        assertEquals(age, bloodDonor.getAge());
        assertEquals(weight, bloodDonor.getWeight());
        assertEquals(bloodType, bloodDonor.getBloodType());
    }

    protected void checkPatient(String name, BloodType bloodType, int requiredAmount, boolean isUrgent, Patient p) {
        assertEquals(name, p.getName());
        assertEquals(bloodType,p.getBloodType());
        assertEquals(requiredAmount, p.getRequiredAmount());
        assertEquals(isUrgent,p.isUrgent());
    }
}
