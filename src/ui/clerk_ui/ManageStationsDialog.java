package ui.clerk_ui;

import model.Clerk;
import model.Station;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by shuorenwang on 2016-11-06.
 */

//can only be accessed after clerk login
public class ManageStationsDialog extends JDialog{
    private Clerk clerk;
    private Station currentStation;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JComboBox allStationsComboBox;
    private DefaultComboBoxModel allStationComboBoxModel;
    private JTextField stationNameField;
    private JTextField stationAddressField;
    private JCheckBox isActiveCheckBox;



    public ManageStationsDialog(JFrame frame){
        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(500,200);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("","[][grow]","[][][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addAllStationsLabel();
        addAllStationsComboBox();
        addStationNameLabel();
        addStationNameTextField();
        addActiveLabel();
        addActiveCheckBox();
        addAddressLabel();
        addAddressTextField();

        addCreateButton();
        addCancelButton();
    }

    private void addAllStationsLabel(){
        JLabel label=new JLabel("All Stations:");
        contentPanel.add(label, "cell 0 0, alignx trailing");
    }

    private void addAllStationsComboBox(){
        allStationComboBoxModel=new DefaultComboBoxModel();
        allStationsComboBox=new JComboBox(allStationComboBoxModel);
        allStationsComboBox.setMaximumSize(new Dimension(300,600));
        contentPanel.add(allStationsComboBox, "cell 1 0,growx");

        ArrayList<Station> Stations=new ArrayList<Station>();
        //TODO: get data from database

        if(Stations.size()>0) {
            Object[] StationsArr = Stations.toArray();

            synchronized (Stations.toArray()) {
                for (Object next : StationsArr) {
                    allStationComboBoxModel.addElement(next);
                }
            }

            allStationsComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        currentStation = (Station) allStationsComboBox.getSelectedItem();
                        update();
                    }
                }
            });
        }
    }

    private void addAddressLabel(){
        JLabel label=new JLabel("Station Address :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addAddressTextField(){
        String data="";
        if(currentStation!=null) {
            data= currentStation.getAddr();
        }
        stationAddressField =new JTextField(data);
        contentPanel.add(stationAddressField, "cell 1 1, growx");
    }

    private void addStationNameLabel(){
        JLabel label=new JLabel("Station Name :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addStationNameTextField(){
        String data="";
        if(currentStation!=null) {
            data= currentStation.getName();
        }
        stationNameField =new JTextField(data);
        contentPanel.add(stationNameField, "cell 1 2, growx");
    }

    private void addActiveLabel(){
        JLabel label=new JLabel("Active :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }

    private void addActiveCheckBox(){
        isActiveCheckBox=new JCheckBox();
        if(currentStation!=null) {
            isActiveCheckBox.setSelected(currentStation.isActive());
        }else{
            isActiveCheckBox.setSelected(true);
        }
        contentPanel.add(isActiveCheckBox, "cell 1 3, alignx leading");
    }


    private void addCreateButton(){
        JButton submitButton = new JButton("Create");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database

                updateAllStations();
            }
        });
        buttonPanel.add(submitButton);
    }

    private void addCancelButton(){
        JButton cancelButton=new JButton("Go Back");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageStationsDialog.this.setVisible(false);
                ManageStationsDialog.this.dispose();
                System.out.println("ManageStationsDialog:: GoBack button pressed");
            }
        });
        buttonPanel.add(cancelButton);
    }

    private void update(){
        stationNameField.setText(currentStation.getName());
        stationAddressField.setText(currentStation.getAddr());
        isActiveCheckBox.setSelected(currentStation.isActive());
    }

    //update All Station ComboBox
    private void updateAllStations(){
        //TODO
    }
}
