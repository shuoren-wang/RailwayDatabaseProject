package ui.clerk_ui;

import model.Clerk;
import model.Passenger;
import model.TrainType;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jdbc.TrainTypeDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

import static jdbc.JDBC.createTrainType;
import static jdbc.JDBC.modifyTrainType;

public class ManageTrainType extends JDialog{
	private Clerk clerk;
	
	private JPanel contentPanel;
    private JPanel buttonPanel;
    
    private TrainTypeDAO trainTypeDAO;
    private TrainType currentTrainType;
    private int trainTypeIndex;
    
    private JComboBox trainTypeComboBox;
    private DefaultComboBoxModel trainTypeComboBoxModel;
    
    private JTextField trainTypeIdField;
    private JTextField trainTypeColourField;
    
    public ManageTrainType(Clerk clerk) {
    	this.clerk = clerk;
    	trainTypeDAO = trainTypeDAO.getInstance();
    	trainTypeDAO.init();
    	
    	
    	contentPanel = new JPanel();
        buttonPanel = new JPanel();
        
        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setTitle("Manage Train Type");
        setSize(600, 300);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	
        
        addTrainTypeIdLabel();
        addTrainTypeComboBox();
        addTrainTypeIdDataLabel();
        addTrainColourLabel();
        addTrainColourDataLabel();
        
    	addCreateButton();
    	addModifyButton();
    	addCancelButton();
    }
    
    private void addTrainTypeIdLabel() {
        JLabel label = new JLabel("Train Type ID :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }
    
    private void addTrainTypeComboBox() {
        trainTypeDAO.loadData();
        List<TrainType> trainTypes = trainTypeDAO.getTrainTypes();

        trainTypeComboBoxModel = new DefaultComboBoxModel();
        trainTypeComboBoxModel.addElement("---NEW TRAIN TYPE---");
        synchronized (trainTypes) {
            for (TrainType next : trainTypes) {
                trainTypeComboBoxModel.addElement(next.toString());
            }
        }

        trainTypeComboBox = new JComboBox(trainTypeComboBoxModel);
        trainTypeComboBox.setMaximumSize(new Dimension(300, 600));


        trainTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    trainTypeIndex = trainTypeComboBox.getSelectedIndex();
                    currentTrainType = TrainTypeDAO.getInstance().getTrainTypes().get(trainTypeIndex - 1);
                    trainTypeIdField.setText(Integer.toString(currentTrainType.getTypeId()));
                    trainTypeColourField.setText(currentTrainType.getColor());
                    System.out.println("ManageTrainTypeDialog:: trainTypeComboBox: selected a trainType");
                }
            }
        });

        //set default traintype as the first train type in database
        if(currentTrainType==null) {
            currentTrainType =trainTypeDAO.getTrainTypes().get(0);
        }

        contentPanel.add(trainTypeComboBox, "cell 1 1,growx,span");
    }
    
    private void addTrainTypeIdDataLabel() {
        String data = "";
        trainTypeIdField = new JTextField(data);
        contentPanel.add(trainTypeIdField, "cell 1 2, growx");
    }
    
    private void addTrainColourLabel() {
        JLabel label = new JLabel("Train Type Colour :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }
    
    private void addTrainColourDataLabel() {
        String data = "";
        trainTypeColourField = new JTextField(data);
        contentPanel.add(trainTypeColourField, "cell 1 3, growx");
    }
    
    private void addCreateButton() {
    	JButton viewButton = new JButton("Create");
    	final ManageTrainType that=this;
    	viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Update UI to add new train type
                if(trainTypeColourField.getText() != null && trainTypeIdField.getText() != null) {
                    int newTrainID = createTrainType(trainTypeColourField.getText());
                    if (newTrainID < 0) {
                        JOptionPane.showMessageDialog(that,
                                "Train creation unsuccessful.",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(that,
                                "Train creation successful. New TrainID is " + newTrainID);
                    }
                }else{
                    JOptionPane.showMessageDialog(that,
                            "Train colour and id cannot be empty",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    	buttonPanel.add(viewButton);
    }
    
    private void addModifyButton() {
    	JButton viewButton = new JButton("Modify");
    	final ManageTrainType that=this;
    	viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(trainTypeColourField.getText() != null && trainTypeIdField.getText() != null) {
                    modifyTrainType(Integer.parseInt(trainTypeIdField.getText()), trainTypeColourField.getText());
                    // TODO: Confirm the change actually happened and update UI
                }else{
                    JOptionPane.showMessageDialog(that,
                            "Train colour and id cannot be empty",
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
            	ManageTrainType.this.setVisible(false);
            	ManageTrainType.this.dispose();
                System.out.println("ManageLinesDialog:: GoBack button pressed");
            }
        });
        buttonPanel.add(cancelButton);
    }
}
