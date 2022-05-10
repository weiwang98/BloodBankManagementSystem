package ui;


import model.BloodBank;
import model.BloodDonor;
import model.Event;
import model.EventLog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// The JFrame for the window of the donor list, with a scroll panel displaying the list and a delete button to delete
public class DonorListWindow extends JFrame implements ListSelectionListener {
    Dimension frameDimensions;
    JPanel mainContainer;
    JScrollPane scrollPane1;
    JList list1;
    DefaultListModel listModel1;

    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 550;
    protected BloodBank bloodBank;
    private JButton deleteButton;



    // EFFECTS: constructs the jframe for the donor list
    // REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    public DonorListWindow(ManagerWindow managerWindow) {
        listModel1 = new DefaultListModel();
        this.bloodBank = managerWindow.bloodBank;
        for (BloodDonor d : bloodBank.getDonorList()) {
            listModel1.addElement(d.getDonorInfo());
        }
        list1 = new JList(listModel1);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setSelectedIndex(0);
        list1.addListSelectionListener(this);
        list1.setVisibleRowCount(5);

        scrollPane1 = new JScrollPane(list1);


        frameDimensions = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        frameSetUp();

        mainContainer = createMainPanel();
        mainContainerSetUp();
        add(mainContainer);


        deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("Delete");
        deleteButton.addActionListener(new DeleteListener());


        mainContainer.setLayout(new GridLayout());
        mainContainer.add(scrollPane1);
        mainContainer.add(deleteButton);

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
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(Color.RED);
        this.setVisible(true);
    }

    // EFFECTS: creates and returns the wrapper container
    private JPanel createMainPanel() {
        return new JPanel(new BorderLayout());
    }

    // EFFECTS: creates and returns the panel at the center of the page
    private JPanel mainContainerSetUp() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return panel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list1.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                deleteButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                deleteButton.setEnabled(true);
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: the actionlistener for the delete button
    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list1.getSelectedIndex();
            listModel1.remove(index);
            bloodBank.deleteDonor(index);

            int size = listModel1.getSize();

            if (size == 0) {
                deleteButton.setEnabled(false);
            } else {
                if (index == listModel1.getSize()) {
                    index--;
                }

                list1.setSelectedIndex(index);
                list1.ensureIndexIsVisible(index);
            }
        }
    }
}


