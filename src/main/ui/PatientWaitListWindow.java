package ui;

import model.BloodBank;
import model.Patient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class shows the patient wait list, and allows the manager to delete people from the list
public class PatientWaitListWindow extends JFrame implements ListSelectionListener {
    private Dimension frameDimensions;
    private JPanel mainContainer;
    private JScrollPane notUrgentScrollPanel;
    private JList notUrgentList;
    private DefaultListModel notUrgentListModel;
    private JScrollPane urgentScrollPanel;
    private JList urgentList;
    private DefaultListModel urgentListModel;
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 550;
    protected BloodBank bloodBank;
    private JButton deleteFromNotUrgentBtn;
    private JButton deleteFromUrgentListBtn;

    // EFFECTS: constructs the JFrame for the patient window
    public PatientWaitListWindow(ManagerWindow managerWindow) {
        this.bloodBank = managerWindow.bloodBank;
        notUrgentListSetUp();
        urgentListSetUp();
        notUrgentScrollPanel = new JScrollPane(notUrgentList);
        urgentScrollPanel = new JScrollPane(urgentList);

        frameDimensions = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        frameSetUp();

        mainContainer = createMainPanel();
        mainContainerSetUp();
        add(mainContainer);

        deleteFromNotUrgentBtn = new JButton("Delete Not Urgent");
        deleteFromNotUrgentBtn.setActionCommand("Delete");
        deleteFromNotUrgentBtn.addActionListener(new DeleteListener());

        deleteFromUrgentListBtn = new JButton("Delete Urgent");
        deleteFromUrgentListBtn.setActionCommand("Delete");
        deleteFromUrgentListBtn.addActionListener(new DeleteListener1());


        mainContainer.setLayout(new GridLayout());
        mainContainer.add(notUrgentScrollPanel);
        mainContainer.add(urgentScrollPanel);
        mainContainer.add(deleteFromNotUrgentBtn);
        mainContainer.add(deleteFromUrgentListBtn);

    }

    // EFFECTS: sets up the main container of the patient waitlist
    // REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ScrollDemoProject.zip
    private JPanel mainContainerSetUp() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return panel;
    }

    // EFFECTS: constructs the main panel
    private JPanel createMainPanel() {
        return new JPanel(new BorderLayout());
    }

    // EFFECTS: sets up the frame
    private void frameSetUp() {
        setTitle("Patient Wait List");
        setSize(frameDimensions.width, frameDimensions.height);
        setResizable(false);
        ImageIcon icon = new ImageIcon("/Users/weiwang/Desktop/CPSC 210/IdeaProjects/project_n6c2l/src/main/icon.png");
        this.setIconImage(icon.getImage());

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(Color.RED);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the urgent wait list j list
    // REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ScrollDemoProject.zip
    private void urgentListSetUp() {
        urgentListModel = new DefaultListModel();

        for (Patient p : bloodBank.getUrgentPatientWaitList()) {
            urgentListModel.addElement(p.getPatientInfo());
        }
        urgentList = new JList(urgentListModel);
        urgentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        urgentList.setSelectedIndex(0);
        urgentList.addListSelectionListener(this);
        urgentList.setVisibleRowCount(5);
    }

    // MODIFIES: this
    // EFFECTS: sets up the not urgent wait list j list
    // REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ScrollDemoProject.zip
    private void notUrgentListSetUp() {
        notUrgentListModel = new DefaultListModel();

        for (Patient p : bloodBank.getPatientWaitList()) {
            notUrgentListModel.addElement(p.getPatientInfo());
        }
        notUrgentList = new JList(notUrgentListModel);
        notUrgentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notUrgentList.setSelectedIndex(0);
        notUrgentList.addListSelectionListener(this);
        notUrgentList.setVisibleRowCount(5);
    }



    // REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ScrollDemoProject.zip

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (notUrgentList.getSelectedIndex() == -1 || urgentList. getSelectedIndex() == -1) {
                //No selection, disable fire button.
                deleteFromNotUrgentBtn.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                deleteFromNotUrgentBtn.setEnabled(true);
            }
        }
    }

    // EFFECTS: delete listener for the delete button for not urgent list
    // REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ScrollDemoProject.zip
    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = notUrgentList.getSelectedIndex();
            notUrgentListModel.remove(index);
            bloodBank.deleteNotUrgentPatient(index);

            int size = notUrgentListModel.getSize();

            if (size == 0) {
                deleteFromNotUrgentBtn.setEnabled(false);
            } else {
                if (index == notUrgentListModel.getSize()) {
                    index--;
                }

                notUrgentList.setSelectedIndex(index);
                notUrgentList.ensureIndexIsVisible(index);
            }
        }

    }

    // EFFECTS: delete listener for the delete button for urgent list
    // REFERENCE: https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ScrollDemoProject.zip
    class DeleteListener1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = urgentList.getSelectedIndex();
            urgentListModel.remove(index);
            bloodBank.deleteUrgentPatient(index);

            int size = urgentListModel.getSize();

            if (size == 0) {
                deleteFromUrgentListBtn.setEnabled(false);
            } else {
                if (index == urgentListModel.getSize()) {
                    index--;
                }

                urgentList.setSelectedIndex(index);
                urgentList.ensureIndexIsVisible(index);
            }
        }

    }
}

