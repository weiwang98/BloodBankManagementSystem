package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.BloodBank;

// JFrame for the manager view
public class ManagerWindow extends JFrame implements ActionListener {
    Dimension frameDimensions;
    JPanel mainContainer;
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 550;
    private JButton totalAmountButton;
    private JButton donorListButton;
    private JButton waitListButton;
    protected BloodBank bloodBank;


    // EFFECTS: constructs the manager window of the app
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    public ManagerWindow(NewBloodDonationApp newBloodDonationApp)  {
        frameDimensions = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        frameSetUp();
        mainContainer = createMainPanel();


        bloodBank = newBloodDonationApp.bloodBank;
        totalAmountButton = new JButton("Total Amount ");
        totalAmountButton.addActionListener(this);
        donorListButton = new JButton("Donor List");
        donorListButton.addActionListener(this);
        waitListButton = new JButton("Wait List");
        waitListButton.addActionListener(this);
        mainContainerSetUp();
        add(mainContainer);


        setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: constructs the main container for the manager's window
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    private void mainContainerSetUp() {
        JLabel titleLabel = new JLabel("Blood Bank Management System");
        JLabel greetingLabel = new JLabel("Hi, Manger " + System.getProperty("user.name") + "!");


        JPanel greetingPanel = new JPanel();
        greetingPanel.setLayout(new BorderLayout());

        greetingPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED, 1),
                BorderFactory.createEmptyBorder(6, 10, 5, 10)));

        greetingLabel.setFont(new Font(Font.MONOSPACED,Font.BOLD, 20));
        greetingPanel.add(greetingLabel, BorderLayout.SOUTH);
        titleLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        greetingPanel.add(titleLabel, BorderLayout.NORTH);

        mainContainer.add(greetingPanel);

        mainContainer.add(totalAmountButton);

        mainContainer.add(donorListButton);

        mainContainer.add(waitListButton);
    }


    // MODIFIES: this
    // EFFECTS: sets up the frame
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    public void frameSetUp()  {
        setTitle("Blood Bank Management System");
        setSize(frameDimensions.width, frameDimensions.height);
        setResizable(false);
        ImageIcon icon = new ImageIcon("/Users/weiwang/Desktop/CPSC 210/IdeaProjects/project_n6c2l/src/main/icon.png");
        this.setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(Color.RED);
    }


    // MODIFIES: this
    // EFFECTS: constructs the main panel of the project
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    private  JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 0, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(70, 150, 50, 150));

        return panel;
    }

    // EFFECTS: action listener for the manager view
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == totalAmountButton) {
            new TotalAmountWindow(this);
        } else if (e.getSource() == donorListButton) {
            new DonorListWindow(this);
        } else if (e.getSource() == waitListButton) {
            new PatientWaitListWindow(this);
        }
    }


}
