package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a blood donor having a name, an age, a weight(in kg), and a bloodType(A/ B/ AB/ O)
public class BloodDonor implements Writable {
    private String name;               // donor's name
    private int age;                   // donor's age
    private double weight;             // donor's weight(in kg)
    private BloodType bloodType;       // donor's blood type (A/B/AB/O)


    // REQUIRES: name has a non-zero length, age>0, weight>0, bloodType should be chosen from the BloodType enum
    // EFFECTS: constructing a blood donor. set the donor's name, age, weight, and bloodType.
    public BloodDonor(String name, int age, double weight, BloodType bloodType) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.bloodType = bloodType;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public String getDonorInfo() {
        return ("Name: " + name
                + "\nAge: " + String.valueOf(age)
                + "\nWeight: " + String.valueOf(weight)
                + "\nBlood Type: " + String.valueOf(bloodType));
    }



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("age", age);
        json.put("weight", weight);
        json.put("bloodType", bloodType);
        return json;

    }


}
