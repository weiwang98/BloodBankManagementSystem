package ui;


import model.BloodBank;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.FileNotFoundException;
import java.io.IOException;



// A GUI class for Blood Donation App
// REFERENCES:
// https://github.com/loayjah/AroundBC-CPSC210-Project.git
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class NewBloodDonationApp extends JFrame implements ActionListener {
    Dimension frameDimensions;
    JPanel mainContainer;
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 550;
    public static final String JSON_STORE = "./data/bloodbank.json";
    protected BloodBank bloodBank;
    JButton donorButton;
    JButton patientButton;
    JButton managerButton;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs the main frame of the app
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    public NewBloodDonationApp()  {
        this.bloodBank = new BloodBank("Vancouver");
        frameDimensions = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        frameSetUp();

        donorButton = createDonorBtn();
        donorButton.addActionListener(this);

        patientButton = createPatientBtn();
        patientButton.addActionListener(this);

        managerButton = createManagerBtn();
        managerButton.addActionListener(this);

        mainContainer = createMainPanel();
        mainContainerSetUp();
        add(mainContainer);

        setVisible(true);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        bloodBankInformationLoadingPopUpMsg();

    }


//     MODIFIES: this
//     EFFECTS: pop up the dialog of whether loading up the data
//     REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    private void bloodBankInformationLoadingPopUpMsg() {
        String[] responses = {"Load Blood Bank Information", "Start a New Blood Bank"};

        ImageIcon saveIcon = new
                ImageIcon("/Users/weiwang/Desktop/CPSC 210/IdeaProjects/project_n6c2l/src/main/saveIcon.png");

        int confirmed = JOptionPane.showOptionDialog(getContentPane(),
                "Do you want to load your past blood bank information from memory?",
                "Loading former blood bank information", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, saveIcon, responses, responses[0]);

        if (confirmed == 0) {
            try {
                bloodBank = jsonReader.read();
                JOptionPane.showMessageDialog(getContentPane(), "Loaded Successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(getContentPane(), "Error with Loading!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: constructs the main container for the main frame
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    private void mainContainerSetUp() {
        JLabel greetingLabel = new JLabel("Welcome to blood service, " + System.getProperty("user.name") + "!");
        JLabel chooseCharacterLabel = new JLabel("Please choose your character: ");

        JPanel greetingPanel = new JPanel();
        greetingPanel.setLayout(new BorderLayout());

        greetingPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED, 1),
                BorderFactory.createEmptyBorder(6, 10, 5, 10)));

        greetingLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        greetingPanel.add(greetingLabel, BorderLayout.NORTH);
        chooseCharacterLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        greetingPanel.add(chooseCharacterLabel, BorderLayout.SOUTH);

        mainContainer.add(greetingPanel);

        mainContainer.add(donorButton);

        mainContainer.add(patientButton);

        mainContainer.add(managerButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the donor button and sets its listener to open the donor's view while clicked
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    private JButton createDonorBtn() {
        JButton donorBtn = new JButton("I'm a donor.");
        donorBtn.setFocusable(false);

        return donorBtn;
    }

    // MODIFIES: this
    // EFFECTS: creates the patient button and sets its listener to open the patient's view while clicked
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    private JButton createPatientBtn() {
        JButton patientBtn = new JButton("I'm a patient.");
        patientBtn.setFocusable(false);
        return patientBtn;
    }

    // MODIFIES: this
    // EFFECTS: creates the manager button and sets its listener to open the passwords window to verify the identity
    // while clicked.
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    private JButton createManagerBtn() {
        JButton managerBtn = new JButton("I'm the manager.");
        managerBtn.setFocusable(false);
        return managerBtn;
    }

    // MODIFIES: this
    // EFFECTS: constructs the main panel of the project
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 0, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(70, 150, 50, 150));

        return panel;
    }


    // MODIFIES: this
    // EFFECTS: sets up the frame
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    public void frameSetUp() {
        setTitle("Blood Bank Management System");
        setSize(frameDimensions.width, frameDimensions.height);
        setResizable(false);
        ImageIcon icon = new ImageIcon("/Users/weiwang/Desktop/CPSC 210/IdeaProjects/project_n6c2l/src/main/icon.png");
        this.setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setOnClosePopUpMsg();
        this.setBackground(Color.RED);
    }

    // MODIFIES: this
    // EFFECTS: listener for the quit button
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    private void setOnClosePopUpMsg() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeApplication();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: showing the dialog of whether to save it when quiting it
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    private void closeApplication() {
        printLog();
        String[] responses = {"Save and Quit", "Just Quit"};
        ImageIcon saveIcon = new
                ImageIcon("/Users/weiwang/Desktop/CPSC 210/IdeaProjects/project_n6c2l/src/main/saveIcon.png");

        int confirmed = JOptionPane.showOptionDialog(getContentPane(),
                "Save data?",
                "SAVE DATA", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, saveIcon, responses, responses[0]);
        if (confirmed == 0) {
            try {
                jsonWriter.open();
                jsonWriter.write(bloodBank);
                jsonWriter.close();
                JOptionPane.showMessageDialog(getContentPane(), "Saved Successfully!");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(getContentPane(), "Error with saving!");
            }
            System.exit(0);
        } else if (confirmed == 1) {
            System.exit(0);
        }
    }

    public void printLog() {
        for (Event e: EventLog.getInstance()) {
            System.out.println("Date: " + e.getDate() + " Description: " + e.getDescription());
        }
    }


    // EFFECTS: action listener for the three buttons on this frame
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == donorButton) {
            new DonorWindow(this);
        } else if (e.getSource() == patientButton) {
            new PatientWindow(this);
        } else if (e.getSource() == managerButton) {
            new ManagerWindow(this);
        }
    }
}

