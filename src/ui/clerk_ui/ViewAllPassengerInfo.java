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

import static jdbc.JDBC.fillPassengerList;

public class ViewAllPassengerInfo extends JDialog{
	private Clerk clerk;
	private String passengerSortStat;
	
	private JPanel contentPanel;
    private JPanel buttonPanel;
    
    private JComboBox passengerStatComboBox;
    private DefaultComboBoxModel passengerStatComboBoxModel;
    private JTextField displayAmountField;
    private JTextField offsetField;
    
    private DefaultListModel passengerInfoListModel;
    private JList passengerInfoList;
    private ArrayList<Passenger> passengerList;
    
    public ViewAllPassengerInfo(Clerk clerk) {
    	this.clerk = clerk;
    	
    	contentPanel = new JPanel();
        buttonPanel = new JPanel();
        
        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setTitle("View All Sorted Passengers");
        setSize(750, 400);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	
        addPassengerStatLabel();
        passengerStatComboBox();
        addDisplayAmountLabel();
        addDisplayAmountDataLabel();
        addOffsetLabel();
        addOffsetDataLabel();
        
        addPassengerInfoList();
        
    	addViewButton();
    	addCancelButton();
    }
    
    private void addPassengerStatLabel() {
        JLabel label = new JLabel("Passenger Sort By :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }
    
    private void addDisplayAmountLabel() {
        JLabel label = new JLabel("Display Amount :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }
    
    private void addOffsetLabel() {
        JLabel label = new JLabel("Offset :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }
    
    private void passengerStatComboBox() {
    	passengerStatComboBoxModel = new DefaultComboBoxModel();
    	passengerStatComboBoxModel.addElement("uid");
    	passengerStatComboBoxModel.addElement("pid");
    	passengerStatComboBoxModel.addElement("uname");
    	passengerStatComboBoxModel.addElement("name");
    	passengerStatComboBoxModel.addElement("phone");
    	
    	passengerStatComboBox = new JComboBox(passengerStatComboBoxModel);
    	passengerStatComboBox.setMaximumSize(new Dimension(300, 600));
    	
    	passengerStatComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passengerSortStat = (String) passengerStatComboBox.getSelectedItem();
                    System.out.println("select item: " + passengerSortStat);
                }
            }
        });
    	
    	if(passengerSortStat==null){
    		passengerSortStat=(String) passengerStatComboBox.getSelectedItem();
        }
    	contentPanel.add(passengerStatComboBox, "cell 1 1,growx,span");
    }
    
    private void addDisplayAmountDataLabel() {
        String data = "";
        displayAmountField = new JTextField(data);
        contentPanel.add(displayAmountField, "cell 1 2, growx");
    }
    
    private void addOffsetDataLabel() {
        String data = "";
        offsetField = new JTextField(data);
        contentPanel.add(offsetField, "cell 1 3, growx");
    }
    
    private void addPassengerInfoList() {
    	passengerInfoListModel = new DefaultListModel();
    	passengerInfoList = new JList(passengerInfoListModel);
        JScrollPane listScroller = new JScrollPane(passengerInfoList);

        passengerInfoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        passengerInfoList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setPreferredSize(new Dimension(400, 200));
        contentPanel.add(listScroller, "cell 0 4,grow,span");
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
    
    private void addViewButton() {
    	JButton viewButton = new JButton("View");
    	final ViewAllPassengerInfo that=this;
    	viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(displayAmountField.getText() != null && offsetField.getText() != null) {

                    int take = Integer.parseInt(displayAmountField.getText());
                    int offset = Integer.parseInt(offsetField.getText());

                    // fillPassengerList(passengerList, passengerSortStat, take, offset);
                    passengerList = fillPassengerList(passengerSortStat, take, offset);
                    
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
                    
                }else{
                    JOptionPane.showMessageDialog(that,
                            "Display Amount and Offset cannot be empty",
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
            	ViewAllPassengerInfo.this.setVisible(false);
            	ViewAllPassengerInfo.this.dispose();
                System.out.println("ManageLinesDialog:: GoBack button pressed");
            }
        });
        buttonPanel.add(cancelButton);
    }
}
