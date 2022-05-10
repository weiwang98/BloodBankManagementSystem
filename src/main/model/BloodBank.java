package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a blood bank, with a location, total amount of all types of blood (in ml), a list of blood donor
// , a list of normal patient wait list, and a list of urgent patient wait list
public class BloodBank implements Writable {

    public static final int AMOUNT_PER_PERSON = 450;   // the required amount of blood donation per person per time

    private String location;                           // location of the blood bank
    private int totalAmountOfTypeA;                    // total amount of Type A blood in the inventory (init: 1000ml)
    private int totalAmountOfTypeB;                    // total amount of Type B blood in the inventory (init: 1000ml)
    private int totalAmountOfTypeAB;                   // total amount of Type AB blood in the inventory (init: 1000ml)
    private int totalAmountOfTypeO;                    // total amount of Type O blood in the inventory (init: 2000ml)
    private List<BloodDonor> donorList;                // a donor list (initial: empty)
    private List<Patient> patientWaitList;             // a normal patient wait list (initial: empty)
    private List<Patient> urgentPatientWaitList;       // an urgent patient wait list (initial: empty)

    // REQUIRES: location has a non-zero length
    // EFFECTS: set the location of the blood bank. Initialize the blood bank with A-blood 1000 ml, B-blood 1000 ml
    //          , AB-blood 1000ml, O-blood 1000ml, an empty donor list, an empty normal patient wait list, and an empty
    //          urgent patient wait list.
    public BloodBank(String location) {
        this.location = location;
        totalAmountOfTypeA = 1000;
        totalAmountOfTypeB = 1000;
        totalAmountOfTypeAB = 1000;
        totalAmountOfTypeO = 2000;
        donorList = new ArrayList<>();
        patientWaitList = new ArrayList<>();
        urgentPatientWaitList = new ArrayList<>();
    }

    // getters
    public String getLocation() {
        return location;
    }

    public int getTotalAmountOfTypeA() {
        return totalAmountOfTypeA;
    }

    public int getTotalAmountOfTypeB() {
        return totalAmountOfTypeB;
    }

    public int getTotalAmountOfTypeAB() {
        return totalAmountOfTypeAB;
    }

    public int getTotalAmountOfTypeO() {
        return totalAmountOfTypeO;
    }

    public List<BloodDonor> getDonorList() {
        return donorList;
    }

    public List<Patient> getPatientWaitList() {
        return patientWaitList;
    }

    public List<Patient> getUrgentPatientWaitList() {
        return urgentPatientWaitList;
    }

    // REQUIRES: blood donor's age > 0, and weight > 0
    // MODIFIES: this
    // EFFECT: test whether the donor is eligible (age>=17 and weight >=50kg)
    public static boolean isEligible(BloodDonor d) {
        return d.getAge() >= 17 && d.getWeight() >= 50;
    }

    // REQUIRES: the donor is eligible
    // MODIFIES: this
    // EFFECTS: if eligible, add the donor's amount to the total amount of the corresponding blood type inventory
    // generate a donor card, and add the donor to the donor list
    public void donateBlood(BloodDonor d) {
        if (d.getBloodType() == BloodType.A) {
            totalAmountOfTypeA += AMOUNT_PER_PERSON;
        } else if (d.getBloodType() == BloodType.B) {
            totalAmountOfTypeB += AMOUNT_PER_PERSON;
        } else if (d.getBloodType() == BloodType.AB) {
            totalAmountOfTypeAB += AMOUNT_PER_PERSON;
        } else {
            totalAmountOfTypeO += AMOUNT_PER_PERSON;
        }
        donorList.add(d);
        EventLog.getInstance().logEvent(new Event("A donor donated blood."));
    }

    // REQUIRES: the required amount of blood >= 0
    // MODIFIES: this
    // EFFECTS: if the patient is urgent, use blood according to the stock
    // if not urgent, add the patient to the wait list
    public String useBlood(Patient p) {
        EventLog.getInstance().logEvent(new Event("A patient tried to used blood."));
        if (p.isUrgent()) {
            if (p.getBloodType() == BloodType.A) {
                return useBloodAorO(p);
            } else if (p.getBloodType() == BloodType.B) {
                return useBloodBorO(p);

            } else if (p.getBloodType() == BloodType.AB) {
                return useAllTypesOfBlood(p);
            } else {
                return useBloodO(p);
            }
        } else {
            patientWaitList.add(p);
            return "Sorry, you have to wait.";
        }


    }


    // REQUIRES: the required amount of blood >= 0, the patient's blood type is A,
    //           the patient is urgent
    // MODIFIES: this
    // EFFECTS: If A blood is sufficient, use A Blood, otherwise use O blood.
    //          If both A & O are insufficient, add the patient to the urgent patient wait list.
    public String useBloodAorO(Patient p) {
        if (p.getRequiredAmount() <= totalAmountOfTypeA) {
            totalAmountOfTypeA -= p.getRequiredAmount();
            return "You have received " + p.getRequiredAmount() + "ml Type A Blood";
        } else if (p.getRequiredAmount() <= totalAmountOfTypeO) {
            totalAmountOfTypeO -= p.getRequiredAmount();
            return "You have received " + p.getRequiredAmount() + "ml Type O Blood";
        } else {
            urgentPatientWaitList.add(p);
            return "Sorry, you have to wait.";
        }
    }

    // REQUIRES: the required amount of blood >= 0, the patient's blood type is B,
    //           the patient is urgent
    // MODIFIES: this
    // EFFECTS: If B blood is sufficient, use B Blood, otherwise use O blood.
    //          If both B & O are insufficient, add the patient to the urgent patient wait list.
    public String useBloodBorO(Patient p) {
        if (p.getRequiredAmount() <= totalAmountOfTypeB) {
            totalAmountOfTypeB -= p.getRequiredAmount();
            return "You have received " + p.getRequiredAmount() + "ml Type B Blood";
        } else if (p.getRequiredAmount() <= totalAmountOfTypeO) {
            totalAmountOfTypeO -= p.getRequiredAmount();
            return "You have received " + p.getRequiredAmount() + "ml Type O Blood";
        } else {
            urgentPatientWaitList.add(p);
            return "Sorry, you have to wait.";
        }
    }


    // REQUIRES: the required amount of blood >= 0, the patient's blood type is AB,
    //           the patient is urgent
    // MODIFIES: this
    // EFFECTS: If AB blood is sufficient, use AB Blood, otherwise use A blood.
    //          If A blood is also not sufficient, use B blood.
    //          If B blood is also not sufficient, use O blood.
    //          If all the AB & A & B & O are insufficient, add the patient to the urgent patient wait list.
    public String useAllTypesOfBlood(Patient p) {
        if (p.getRequiredAmount() <= totalAmountOfTypeAB) {
            totalAmountOfTypeAB -= p.getRequiredAmount();
            return "You have received " + p.getRequiredAmount() + "ml Type AB Blood";
        } else if (p.getRequiredAmount() <= totalAmountOfTypeA) {
            totalAmountOfTypeA -= p.getRequiredAmount();
            return "You have received " + p.getRequiredAmount() + "ml Type A Blood";
        } else if (p.getRequiredAmount() <= totalAmountOfTypeB) {
            totalAmountOfTypeB -= p.getRequiredAmount();
            return "You have received " + p.getRequiredAmount() + "ml Type B Blood";
        } else if (p.getRequiredAmount() <= totalAmountOfTypeO) {
            totalAmountOfTypeO -= p.getRequiredAmount();
            return "You have received " + p.getRequiredAmount() + "ml Type O Blood";
        } else {
            urgentPatientWaitList.add(p);
            return "Sorry, you have to wait.";
        }
    }


    // REQUIRES: the required amount of blood >= 0, the patient's blood type is O,
    //           the patient is urgent
    // MODIFIES: this
    // EFFECTS: If O blood is sufficient, use O blood, otherwise add the patient to the urgent patient wait list.
    public String useBloodO(Patient p) {
        if (p.getRequiredAmount() <= totalAmountOfTypeO) {
            totalAmountOfTypeO -= p.getRequiredAmount();
            return "You have received " + p.getRequiredAmount() + "ml Type O Blood";
        } else {
            urgentPatientWaitList.add(p);
            return "Sorry, you have to wait.";
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("location", location);
        json.put("totalAmountOfTypeA", totalAmountOfTypeA);
        json.put("totalAmountOfTypeB", totalAmountOfTypeB);
        json.put("totalAmountOfTypeAB", totalAmountOfTypeAB);
        json.put("totalAmountOfTypeO", totalAmountOfTypeO);
        json.put("donorList", donorListToJson());
        json.put("patientWaitList", patientWaitListToJson());
        json.put("urgentPatientList", urgentPatientWaitListToJson());
        return json;
    }

    // EFFECTS: returns donorList in this BloodBank as a JSON array
    private JSONArray donorListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (BloodDonor d : donorList) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns patientWaitList in this BloodBank as a JSON array
    private JSONArray patientWaitListToJson() {
        JSONArray jsonArray1 = new JSONArray();

        for (Patient p : patientWaitList) {
            jsonArray1.put(p.toJson());
        }

        return jsonArray1;
    }

    // EFFECTS: returns urgentPatientWaitList in this BloodBank as a JSON array
    private JSONArray urgentPatientWaitListToJson() {
        JSONArray jsonArray2 = new JSONArray();

        for (Patient p : urgentPatientWaitList) {
            jsonArray2.put(p.toJson());
        }

        return jsonArray2;

    }

    public void deleteUrgentPatient(int index) {
        EventLog.getInstance().logEvent(new Event("A patient was deleted from the urgent patient wait list."));
        if (urgentPatientWaitList.size() > index) {
            urgentPatientWaitList.remove(index);
        }
    }

    public void deleteNotUrgentPatient(int index) {
        EventLog.getInstance().logEvent(new Event("A patient was deleted from the not urgent patient wait list."));
        if (patientWaitList.size() > index) {
            patientWaitList.remove(index);
        }
    }

    public void deleteDonor(int index) {
        EventLog.getInstance().logEvent(new Event("A donor was deleted from the donor list."));
        if (donorList.size() > index) {
            donorList.remove(index);
        }
    }
}




















