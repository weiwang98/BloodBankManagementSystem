package ui;


import model.BloodBank;
import model.BloodType;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// A JFrame for the patient widow, where allows the patients to enter their personal info
// REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
public class PatientWindow extends JFrame implements ActionListener {
    Dimension frameDimensions;
    JPanel mainPanel;
    JPanel namePanel;
    JPanel bloodTypePanel;
    JPanel requiredAmountPanel;
    JPanel isUrgentPanel;
    JTextField nameTextField;
    JTextField bloodTypeTextField;
    JTextField requiredAmountTextField;
    JTextField isUrgentTextField;
    JButton addButton;

    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 550;


    protected BloodBank bloodBank;
    private Patient newPatient;

    // EFFECTS: constructs a patient's view window
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    public PatientWindow(NewBloodDonationApp newBloodDonationApp)  {
        frameDimensions = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        frameSetUp();
        setupMainPanel();
        add(mainPanel);
        setupAddBtn();
        mainPanel.add(addButton);
        setVisible(true);

        bloodBank = newBloodDonationApp.bloodBank;

    }

    // MODIFIES: this
    // EFFECTS: sets up the main panel
    private void setupMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5,1));
        setupNamePanel();
        setupBloodTypePanel();
        setupRequiredAmountPanel();
        setupIsUrgentPanel();
        mainPanel.add(namePanel);
        mainPanel.add(bloodTypePanel);
        mainPanel.add(requiredAmountPanel);
        mainPanel.add(isUrgentPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets up the name panel, where allows the patient to enter their names
    private void setupNamePanel() {
        namePanel = new JPanel(new BorderLayout());
        namePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12),
                BorderFactory.createLineBorder(new Color(204, 204, 204))));
        JLabel propertyLabel = new JLabel("Name: ");
        propertyLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        propertyLabel.setPreferredSize(new Dimension(75, 25));

        nameTextField = new JTextField();
        nameTextField.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));

        namePanel.add(propertyLabel, BorderLayout.WEST);
        namePanel.add(nameTextField, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up the blood type panel, where allows the patient to enter their blood types
    private void setupBloodTypePanel() {
        bloodTypePanel = new JPanel(new BorderLayout());
        bloodTypePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12),
                BorderFactory.createLineBorder(new Color(204, 204, 204))));
        JLabel propertyLabel = new JLabel("BType: ");
        propertyLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        propertyLabel.setPreferredSize(new Dimension(75, 25));

        bloodTypeTextField = new JTextField();
        bloodTypeTextField.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));

        bloodTypePanel.add(propertyLabel, BorderLayout.WEST);
        bloodTypePanel.add(bloodTypeTextField, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up the required amount panel, where allows the patient to enter their required amount of blood
    private void setupRequiredAmountPanel() {
        requiredAmountPanel = new JPanel(new BorderLayout());
        requiredAmountPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12),
                BorderFactory.createLineBorder(new Color(204, 204, 204))));
        JLabel propertyLabel = new JLabel("R-Amount: ");
        propertyLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        propertyLabel.setPreferredSize(new Dimension(75, 25));

        requiredAmountTextField = new JTextField();
        requiredAmountTextField.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));

        requiredAmountPanel.add(propertyLabel, BorderLayout.WEST);
        requiredAmountPanel.add(requiredAmountTextField, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up the is urgent panel, where allows the patient to choose whether they are in urgent need
    private void setupIsUrgentPanel() {
        isUrgentPanel = new JPanel(new BorderLayout());
        isUrgentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12),
                BorderFactory.createLineBorder(new Color(204, 204, 204))));
        JLabel propertyLabel = new JLabel("Urgent?: ");
        propertyLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        propertyLabel.setPreferredSize(new Dimension(75, 25));

        isUrgentTextField = new JTextField();
        isUrgentTextField.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));

        isUrgentPanel.add(propertyLabel, BorderLayout.WEST);
        isUrgentPanel.add(isUrgentTextField, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up the add button
    // REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ButtonDemoProject.zip
    private void setupAddBtn() {
        addButton = new JButton("Use Blood");
        addButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: doing the patient's window setup
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    public void frameSetUp()  {
        setTitle("Hi patient! Please enter your personal info.");
        setSize(frameDimensions.width, frameDimensions.height);
        setResizable(false);
        ImageIcon icon = new ImageIcon("/Users/weiwang/Desktop/CPSC 210/IdeaProjects/project_n6c2l/src/main/icon.png");
        this.setIconImage(icon.getImage());
        setLocationRelativeTo(null);
    }


    // EFFECTS: action listener for the add button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameTextField.getText();
            BloodType bloodType = BloodType.valueOf(bloodTypeTextField.getText());
            Integer requiredAmount = Integer.valueOf(requiredAmountTextField.getText());
            boolean isUrgent = Boolean.parseBoolean(isUrgentTextField.getText());

            newPatient = new Patient(name,bloodType,requiredAmount,isUrgent);
            if (bloodBank.useBlood(newPatient) == "Sorry, you have to wait.") {
                JOptionPane.showMessageDialog(getContentPane(), "Sorry, you have to wait");
            } else {
                JOptionPane.showMessageDialog(getContentPane(), "You got the blood!");
            }
            this.dispose();
        }

    }


}