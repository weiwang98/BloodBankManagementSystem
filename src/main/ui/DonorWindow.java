package ui;

import model.BloodBank;
import model.BloodDonor;
import model.BloodType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
public class DonorWindow extends JFrame implements ActionListener {
    Dimension frameDimensions;
    JPanel mainPanel;
    JPanel namePanel;
    JPanel agePanel;
    JPanel weightPanel;
    JPanel bloodTypePanel;
    JButton addButton;
    JTextField nameTextField;
    JTextField ageTextField;
    JTextField weightTextField;
    JTextField bloodTextField;

    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 550;

    protected BloodBank bloodBank;
    private BloodDonor newDonor;

    // EFFECTS: constructs a donor's view window
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    public DonorWindow(NewBloodDonationApp newBloodDonationApp)  {
        frameDimensions = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        frameSetUp();
        setupMainPanel();
        add(mainPanel);
        setupAddBtn();
        mainPanel.add(addButton);
        setVisible(true);

        bloodBank = newBloodDonationApp.bloodBank;

    }

    private void setupAddBtn() {
        addButton = new JButton("Donate");
        addButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: doing the donor's window setup
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    public void frameSetUp() {
        setTitle("Hi donor! Please enter your personal info.");
        setSize(frameDimensions.width, frameDimensions.height);
        setResizable(false);
        ImageIcon icon = new ImageIcon("/Users/weiwang/Desktop/CPSC 210/IdeaProjects/project_n6c2l/src/main/icon.png");
        this.setIconImage(icon.getImage());
        setLocationRelativeTo(null);
    }


    // MODIFIES: this
    // EFFECTS: constructs the main panel where entering the donor's personal info
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git

    private void setupMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5,1));
        setupAgePanel();
        setupNamePanel();
        setupWeightPanel();
        setupBloodTypePanel();
        mainPanel.add(namePanel);
        mainPanel.add(agePanel);
        mainPanel.add(weightPanel);
        mainPanel.add(bloodTypePanel);

    }


    private void setupNamePanel() {
        namePanel = new JPanel(new BorderLayout());
        namePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12),
                BorderFactory.createLineBorder(new Color(204, 204, 204))));

        JLabel propertyLabel = new JLabel("Name:");
        propertyLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        propertyLabel.setPreferredSize(new Dimension(75, 25));


        nameTextField = new JTextField();
        nameTextField.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));

        namePanel.add(propertyLabel, BorderLayout.WEST);
        namePanel.add(nameTextField, BorderLayout.CENTER);
    }

    private void setupAgePanel() {
        agePanel = new JPanel(new BorderLayout());
        agePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12),
                BorderFactory.createLineBorder(new Color(204, 204, 204))));

        JLabel propertyLabel = new JLabel("Age:");
        propertyLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        propertyLabel.setPreferredSize(new Dimension(75, 25));


        ageTextField = new JTextField();
        ageTextField.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));

        agePanel.add(propertyLabel, BorderLayout.WEST);
        agePanel.add(ageTextField, BorderLayout.CENTER);
    }

    private void setupWeightPanel() {
        weightPanel = new JPanel(new BorderLayout());
        weightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12),
                BorderFactory.createLineBorder(new Color(204, 204, 204))));

        JLabel propertyLabel = new JLabel("Weight:");
        propertyLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        propertyLabel.setPreferredSize(new Dimension(75, 25));


        weightTextField = new JTextField();
        weightTextField.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));

        weightPanel.add(propertyLabel, BorderLayout.WEST);
        weightPanel.add(weightTextField, BorderLayout.CENTER);
    }

    private void setupBloodTypePanel() {
        bloodTypePanel = new JPanel(new BorderLayout());
        bloodTypePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12),
                BorderFactory.createLineBorder(new Color(204, 204, 204))));

        JLabel propertyLabel = new JLabel("BType:");
        propertyLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        propertyLabel.setPreferredSize(new Dimension(75, 25));


        bloodTextField = new JTextField();
        bloodTextField.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));

        bloodTypePanel.add(propertyLabel, BorderLayout.WEST);
        bloodTypePanel.add(bloodTextField, BorderLayout.CENTER);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameTextField.getText();
            Integer age = Integer.valueOf(ageTextField.getText());
            double weight = Double.parseDouble(weightTextField.getText());
            BloodType bloodType = BloodType.valueOf(bloodTextField.getText());
            newDonor = new BloodDonor(name, age, weight, bloodType);
            if (BloodBank.isEligible(newDonor)) {
                JOptionPane.showMessageDialog(getContentPane(), "Congrats! You are eligible.");
                bloodBank.donateBlood(newDonor);
            } else {
                JOptionPane.showMessageDialog(getContentPane(), "You are not eligible!");
            }
            this.dispose();

            System.out.println(newDonor.getName());
        }
    }




}

