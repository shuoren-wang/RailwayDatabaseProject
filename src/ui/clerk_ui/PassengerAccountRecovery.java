package ui.clerk_ui;

import model.Clerk;
import model.Passenger;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

import static jdbc.JDBC.accountRecovery;

public class PassengerAccountRecovery extends JDialog{
	private Clerk clerk;
	
	private JPanel contentPanel;
    private JPanel buttonPanel;
    
    private JTextField usernameField;
    private JTextField nameField;
    private JTextField phoneField;
    
    private DefaultListModel passengerInfoListModel;
    private JList passengerInfoList;
    private ArrayList<Passenger> passengerList;
    
    public PassengerAccountRecovery(Clerk clerk) {
    	this.clerk = clerk;
    	
    	contentPanel = new JPanel();
        buttonPanel = new JPanel();
        
        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setTitle("Passenger Account Recovery");
        setSize(300, 200);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	
        addUsernameLabel();
        addUsernameDataLabel();
        addNameLabel();
        addNameDataLabel();
        addPhoneLabel();
        addPhoneDataLabel();
        
        addPassengerInfoList();
        
    	addFindButton();
    	addCancelButton();
    }
    
    private void addUsernameLabel() {
        JLabel label = new JLabel("Username :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }
    
    private void addUsernameDataLabel() {
        String data = "";
        usernameField = new JTextField(data);
        contentPanel.add(usernameField, "cell 1 1, growx");
    }
    
    private void addNameLabel() {
        JLabel label = new JLabel("Name :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }
    
    private void addNameDataLabel() {
        String data = "";
        nameField = new JTextField(data);
        contentPanel.add(nameField, "cell 1 2, growx");
    }
    
    private void addPhoneLabel() {
        JLabel label = new JLabel("Phone Number :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }
    
    private void addPhoneDataLabel() {
        String data = "";
        phoneField = new JTextField(data);
        contentPanel.add(phoneField, "cell 1 3, growx");
    }
    
    private void addPassengerInfoList() {
    	passengerInfoListModel = new DefaultListModel();
    	passengerInfoList = new JList(passengerInfoListModel);
        JScrollPane listScroller = new JScrollPane(passengerInfoList);

        passengerInfoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        passengerInfoList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setPreferredSize(new Dimension(300, 100));
        contentPanel.add(listScroller, "cell 0 4,grow,span");
        passengerInfoListModel.addElement(String.format("%25s %25s",
                "User ID","Password"));

        if (passengerList != null && passengerList.size() > 0) {
            ArrayList<String> passengerInfoArr = new ArrayList<String>();
            for (Passenger next : passengerList) {
            	passengerInfoArr.add(next.toString());
            }
            synchronized (passengerInfoArr) {
                for (String next : passengerInfoArr) {
                	passengerInfoListModel.addElement(next);
                }
            }
        }
    }
    
    private void addFindButton() {
    	JButton viewButton = new JButton("Find");
    	final PassengerAccountRecovery that=this;
    	viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(usernameField.getText() != null && nameField.getText() != null && phoneField.getText() != null) {
                    String[] ret = new String[2];
                    ret = accountRecovery(usernameField.getText(), nameField.getText(), phoneField.getText());
                    int uid = Integer.parseInt(ret[0]);
                    String password = ret[1];
                    System.out.println("User ID:  " + uid + "\nPassword: " + password);

                    passengerInfoListModel.removeAllElements();
		    passengerInfoListModel.addElement(String.format("%25s %25s",
								    "User ID","Password"));
		    passengerInfoListModel.addElement(String.format("%25s %25s",
								    Integer.toString(uid),password));
                }else{
                    JOptionPane.showMessageDialog(that,
                            "Username, Name, and Phone Number cannot be empty",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    	buttonPanel.add(viewButton);
    }
    
    private void addCancelButton() {
        JButton cancelButton = new JButton("Go Back");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	PassengerAccountRecovery.this.setVisible(false);
            	PassengerAccountRecovery.this.dispose();
                System.out.println("ManageLinesDialog:: GoBack button pressed");
            }
        });
        buttonPanel.add(cancelButton);
    }
}
