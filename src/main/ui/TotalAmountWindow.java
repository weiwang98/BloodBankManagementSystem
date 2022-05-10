package ui;

import model.BloodBank;

import javax.swing.*;
import java.awt.*;

// A JFrame for the manager to check the current amount of all types of blood in the inventory
// REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class TotalAmountWindow extends JFrame {
    Dimension frameDimensions;
    JPanel mainContainer;
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 550;
    protected BloodBank bloodBank;
    JLabel typeALabel;
    JLabel typeBLabel;
    JLabel typeABLabel;
    JLabel typeOLabel;



    // EFFECTS: constructs a window for total amount of four types of blood
    public TotalAmountWindow(ManagerWindow managerWindow) {
        this.bloodBank = managerWindow.bloodBank;

        frameDimensions = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        frameSetUp();

        typeALabel = new JLabel("A: " + bloodBank.getTotalAmountOfTypeA() + "ml");
        typeBLabel = new JLabel("B: " + bloodBank.getTotalAmountOfTypeB() + "ml");
        typeABLabel = new JLabel("AB: " + bloodBank.getTotalAmountOfTypeAB() + "ml");
        typeOLabel = new JLabel("O: " + bloodBank.getTotalAmountOfTypeO() + "ml");

        mainContainer = createMainPanel();
        mainContainer.setLayout(new GridLayout());
        mainContainer.add(typeALabel);
        mainContainer.add(typeBLabel);
        mainContainer.add(typeABLabel);
        mainContainer.add(typeOLabel);
        add(mainContainer);


    }


    // MODIFIES: this
    // EFFECTS: creates the main panel for this window
    // REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    private JPanel createMainPanel() {
        return new JPanel(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: sets up the frame
    // REFERENCE: https://github.com/loayjah/AroundBC-CPSC210-Project.git
    public void frameSetUp() {
        setTitle("Total Amount");
        setSize(frameDimensions.width, frameDimensions.height);
        setResizable(false);
        ImageIcon icon = new ImageIcon("/Users/weiwang/Desktop/CPSC 210/IdeaProjects/project_n6c2l/src/main/icon.png");
        this.setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(Color.RED);
        this.setVisible(true);
    }
}
