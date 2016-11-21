package ui.clerk_ui;

import model.Clerk;
import model.Passenger;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

import static jdbc.JDBC.searchPassenger;

public class SearchPassengerInfo extends JDialog{
	private Clerk clerk;
	
	private JPanel contentPanel;
    private JPanel buttonPanel;
    
    private JTextField uidField;
    
    private DefaultListModel passengerInfoListModel;
    private JList passengerInfoList;
    private ArrayList<Passenger> passengerList;
    
    public SearchPassengerInfo(Clerk clerk) {
    	this.clerk = clerk;
    	
    	contentPanel = new JPanel();
        buttonPanel = new JPanel();
        
        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setTitle("Search Passenger Info");
        setSize(750, 150);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	
        addUidLabel();
        addUidDataLabel();
        
        addPassengerInfoList();
        
    	addFindButton();
    	addCancelButton();
    }
    
    private void addUidLabel() {
        JLabel label = new JLabel("User ID :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }
    
    private void addUidDataLabel() {
        String data = "";
        uidField = new JTextField(data);
        contentPanel.add(uidField, "cell 1 1, growx");
    }
    
    private void addPassengerInfoList() {
    	passengerInfoListModel = new DefaultListModel();
    	passengerInfoList = new JList(passengerInfoListModel);
        JScrollPane listScroller = new JScrollPane(passengerInfoList);

        passengerInfoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        passengerInfoList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setPreferredSize(new Dimension(400, 200));
        contentPanel.add(listScroller, "cell 0 2,grow,span");
        passengerInfoListModel.addElement(String.format("%25s %25s %25s %25s %25s",
                "User ID","Passenger ID","Username","Name","Phone Number"));

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
    	final SearchPassengerInfo that=this;
    	viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int uid = Integer.parseInt(uidField.getText());
                passengerList = searchPassenger(uid);

                passengerInfoListModel.removeAllElements();
                passengerInfoListModel.addElement(String.format("%25s %25s %25s %25s %25s",
                        "User ID","Passenger ID","Username","Name","Phone Number"));

                for (Passenger next : passengerList) {
                    passengerInfoListModel.addElement(String.format("%25s %25s %25s %25s %25s",
                            Integer.toString(next.getUserID()),
                            Integer.toString(next.getPassengerID()),
                            next.getUserName(),
                            next.getName(),
                            next.getPhone()));
                }


                if(uidField.getText() != null) {
                    
                }else{
                    JOptionPane.showMessageDialog(that,
                            "UserID cannot be empty",
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
            	SearchPassengerInfo.this.setVisible(false);
            	SearchPassengerInfo.this.dispose();
                System.out.println("ManageLinesDialog:: GoBack button pressed");
            }
        });
        buttonPanel.add(cancelButton);
    }
}
