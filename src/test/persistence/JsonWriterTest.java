package persistence;

import model.BloodBank;
import model.BloodDonor;
import model.BloodType;
import model.Patient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// unit test for JsonWriter
// write data to a file and then use the reader to read it back in and check that we
// read in a copy of what was written out.
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            BloodBank bb =  new BloodBank("Toronto");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            BloodBank bb = new BloodBank("Vancouver");
            JsonWriter writer = new JsonWriter("data/testWriterEmptyBloodBank.json");
            writer.open();
            writer.write(bb);
            writer.close();

            JsonReader reader = new JsonReader("data/testWriterEmptyBloodBank.json");
            bb = reader.read();
            assertEquals("Vancouver", bb.getLocation());
            assertEquals(1000, bb.getTotalAmountOfTypeA());
            assertEquals(1000, bb.getTotalAmountOfTypeB());
            assertEquals(1000, bb.getTotalAmountOfTypeAB());
            assertEquals(2000, bb.getTotalAmountOfTypeO());
            assertEquals(0, bb.getDonorList().size());
            assertEquals(0, bb.getPatientWaitList().size());
            assertEquals(0, bb.getUrgentPatientWaitList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBloodBank() {
        try {
            BloodBank bb = new BloodBank("Calgary");
            bb.donateBlood(new BloodDonor("Paul", 20, 60, BloodType.A));
            bb.donateBlood(new BloodDonor("Lucy", 21, 70, BloodType.AB));
            bb.donateBlood(new BloodDonor("Ann", 15, 60, BloodType.A));
            bb.useBlood(new Patient("Gregor", BloodType.B, 200, false));
            bb.useBlood(new Patient("Mary", BloodType.O, 1200, false));
            bb.useBlood(new Patient("Felix", BloodType.A, 20000, true));
            bb.useBlood(new Patient("John", BloodType.AB, 10000, true));

            JsonWriter writer = new JsonWriter("data/testWriterGeneralBloodBank.json");
            writer.open();
            writer.write(bb);
            writer.close();

            JsonReader reader = new JsonReader("data/testWriterGeneralBloodBank.json");
            bb = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}
