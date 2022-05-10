package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a patient requiring blood transfusions
// A patient has a name, a blood type(A/B/AB/O), a required amount of blood (in ml)
// , and a situation (whether urgent or not)
public class Patient implements Writable {

    private String name;                // patient's name
    private BloodType bloodType;        // patient's blood type (A/B/AB/O)
    private int requiredAmount;         // patient's required amount of blood (in m;)
    private boolean isUrgent;           // patient's situation (urgent -> true/ not urgent -> false)

    // REQUIRES: name has a non-zero length, bloodType should be chosen from BloodType enum (A/B/AB/O)
    //           , requiredAmount >= 0, isUrgent is whether true or false
    //EFFECTS: constructs a patient, with a name, a bloodType, a requiredAmount, and a isUrgent set to it.
    public Patient(String name, BloodType bloodType, int requiredAmount, boolean isUrgent) {
        this.name = name;
        this.bloodType = bloodType;
        this.requiredAmount = requiredAmount;
        this.isUrgent = isUrgent;
    }

    // getters
    public String getName() {
        return name;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public String getPatientInfo() {
        return ("Name :" + name + "Blood Type: " + bloodType + "Required Amount: " + requiredAmount
               + "ml" + "Urgent? " + isUrgent);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("bloodType", bloodType);
        json.put("requiredAmount", requiredAmount);
        json.put("isUrgent", isUrgent);
        return json;
    }
}
