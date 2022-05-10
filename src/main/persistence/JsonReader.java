package persistence;

import model.BloodBank;
import model.BloodDonor;
import model.BloodType;
import model.Patient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads BloodBank from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads blood bank from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BloodBank read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses BloodBank from JSON object and returns it
    private BloodBank parseWorkRoom(JSONObject jsonObject) {
        String location = jsonObject.getString("location");
        int totalAmountOfTypeA = jsonObject.getInt("totalAmountOfTypeA");
        int totalAmountOfTypeB = jsonObject.getInt("totalAmountOfTypeB");
        int totalAmountOfTypeAB = jsonObject.getInt("totalAmountOfTypeAB");
        int totalAmountOfTypeO = jsonObject.getInt("totalAmountOfTypeO");
        BloodBank bb = new BloodBank(location);
        addDonorList(bb, jsonObject);
        addPatientWaitList(bb, jsonObject);
        addUrgentPatientWaitList(bb, jsonObject);
        return bb;
    }

    // MODIFIES: bb
    // EFFECTS: parses donorList from JSON object and adds them to BloodBank
    private void addDonorList(BloodBank bb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("donorList");
        for (Object json : jsonArray) {
            JSONObject nextDonor = (JSONObject) json;
            addDonor(bb, nextDonor);
        }
    }

    // MODIFIES: bb
    // EFFECTS: parses bloodDonor from JSON object and adds it to BloodBank
    private void addDonor(BloodBank bb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int age = jsonObject.getInt("age");
        double weight = jsonObject.getDouble("weight");
        BloodType bloodType = BloodType.valueOf(jsonObject.getString("bloodType"));
        BloodDonor bloodDonor = new BloodDonor(name, age, weight, bloodType);
        if (BloodBank.isEligible(bloodDonor)) {
            bb.donateBlood(bloodDonor);
        }
    }

    // MODIFIES: bb
    // EFFECTS: parses patientWaitList from JSON object and adds them to BloodBank
    private void addPatientWaitList(BloodBank bb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("patientWaitList");
        for (Object json : jsonArray) {
            JSONObject nextNotUrgentPatient = (JSONObject) json;
            addNotUrgentPatient(bb, nextNotUrgentPatient);
        }
    }

    // MODIFIES: bb
    // EFFECTS: parses notUrgentPatient from JSON object and adds it to BloodBank
    private void addNotUrgentPatient(BloodBank bb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        BloodType bloodType = BloodType.valueOf(jsonObject.getString("bloodType"));
        int requiredAmount = jsonObject.getInt("requiredAmount");
        boolean isUrgent = jsonObject.getBoolean("isUrgent");
        Patient notUrgentPatient = new Patient(name, bloodType, requiredAmount, isUrgent);
        if (! notUrgentPatient.isUrgent()) {
            bb.useBlood(notUrgentPatient);
        } else {
            return;
        }
    }

    // MODIFIES: bb
    // EFFECTS: parses patientWaitList from JSON object and adds them to BloodBank
    private void addUrgentPatientWaitList(BloodBank bb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("urgentPatientList");
        for (Object json : jsonArray) {
            JSONObject nextUrgentPatient = (JSONObject) json;
            addUrgentPatient(bb, nextUrgentPatient);
        }
    }

    // MODIFIES: bb
    // EFFECTS: parses notUrgentPatient from JSON object and adds it to BloodBank
    private void addUrgentPatient(BloodBank bb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        BloodType bloodType = BloodType.valueOf(jsonObject.getString("bloodType"));
        int requiredAmount = jsonObject.getInt("requiredAmount");
        boolean isUrgent = jsonObject.getBoolean("isUrgent");
        Patient notUrgentPatient = new Patient(name, bloodType, requiredAmount, isUrgent);
        if (notUrgentPatient.isUrgent()) {
            bb.useBlood(notUrgentPatient);
        } else {
            return;
        }

    }
}



