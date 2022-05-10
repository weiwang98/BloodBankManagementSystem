package persistence;


import model.BloodBank;
import model.BloodDonor;
import model.BloodType;
import model.Patient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BloodBank bb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBloodBank() {
        JsonReader reader = new JsonReader("data/testReaderEmptyBloodBank.json");
        try {
            BloodBank bb = reader.read();
            assertEquals("Vancouver", bb.getLocation());
            assertEquals(1000, bb.getTotalAmountOfTypeA());
            assertEquals(1000, bb.getTotalAmountOfTypeB());
            assertEquals(1000, bb.getTotalAmountOfTypeAB());
            assertEquals(2000, bb.getTotalAmountOfTypeO());
            assertEquals(0, bb.getDonorList().size());
            assertEquals(0, bb.getPatientWaitList().size());
            assertEquals(0, bb.getUrgentPatientWaitList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBloodBank() {
        JsonReader reader = new JsonReader("data/testReaderGeneralBloodBank.json");
        try {
            BloodBank bb = reader.read();
            assertEquals("Calgary", bb.getLocation());
            assertEquals(1450, bb.getTotalAmountOfTypeA());
            assertEquals(1000, bb.getTotalAmountOfTypeB());
            assertEquals(1450, bb.getTotalAmountOfTypeAB());
            assertEquals(2000, bb.getTotalAmountOfTypeO());
            List<BloodDonor> donorList = bb.getDonorList();
            assertEquals(2, donorList.size());
            checkDonor("Paul", 20, 60, BloodType.A, donorList.get(0));
            checkDonor("Lucy", 21, 70, BloodType.AB, donorList.get(1));
            List<Patient> patientList = bb.getPatientWaitList();
            assertEquals(2, patientList.size());
            checkPatient("Gregor", BloodType.B, 200, false, patientList.get(0));
            checkPatient("Mary", BloodType.O, 1200, false, patientList.get(1));
            List<Patient> urgentPatientWaitList = bb.getUrgentPatientWaitList();
            assertEquals(2, urgentPatientWaitList.size());
            checkPatient("Felix", BloodType.A, 20000, true, urgentPatientWaitList.get(0));
            checkPatient("John", BloodType.AB, 10000, true, urgentPatientWaitList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}