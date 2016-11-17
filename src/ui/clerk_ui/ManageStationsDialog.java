package ui.clerk_ui;

import jdbc.StationDAO;
import model.Clerk;
import model.Station;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-06.
 */

//can only be accessed after clerk login
public class ManageStationsDialog extends JDialog {
    private Clerk clerk;
    private Station currentStation;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JComboBox allStationsComboBox;
    private DefaultComboBoxModel allStationComboBoxModel;
    private JTextField stationNameField;
    private JTextField stationAddressField;
    private JCheckBox isActiveCheckBox;
    private StationDAO stationDAO;
    private int index;


    public ManageStationsDialog(Clerk clerk) {
        this.clerk=clerk;
        stationDAO = StationDAO.getInstance();
        stationDAO.init();

        contentPanel = new JPanel();
        buttonPanel = new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(500, 200);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][]"));
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
        addStatusButton();
        addCancelButton();
    }

    private void addAllStationsLabel() {
        JLabel label = new JLabel("All Stations:");
        contentPanel.add(label, "cell 0 0, alignx trailing");
    }

    private void addAllStationsComboBox() {
        allStationComboBoxModel = new DefaultComboBoxModel();
        allStationsComboBox = new JComboBox(allStationComboBoxModel);
        allStationsComboBox.setMaximumSize(new Dimension(300, 600));
        contentPanel.add(allStationsComboBox, "cell 1 0,growx");

        //TODO: get data from database
        final List<Station> stations = stationDAO.getStations();

        synchronized (stations) {
            for (Object next : stations) {
                allStationComboBoxModel.addElement(next);
            }
        }

        allStationsComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    index= allStationsComboBox.getSelectedIndex();
                    currentStation = StationDAO.getInstance().getStations().get(index);
//                    update();
                    System.out.println("select station name: " + currentStation.getName()+
                                    "; status: "+currentStation.isActive());
                }
            }
        });

        //set default line as the first station in database
        if(currentStation==null){
            currentStation=stationDAO.getStations().get(0);
        }

    }

    private void addAddressLabel() {
        JLabel label = new JLabel("Station Address :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addAddressTextField() {
        stationAddressField = new JTextField();
        contentPanel.add(stationAddressField, "cell 1 1, growx");
    }

    private void addStationNameLabel() {
        JLabel label = new JLabel("Station Name :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addStationNameTextField() {
        stationNameField = new JTextField();
        contentPanel.add(stationNameField, "cell 1 2, growx");
    }

    private void addActiveLabel() {
        JLabel label = new JLabel("Active :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }

    private void addActiveCheckBox() {
        isActiveCheckBox = new JCheckBox();
        isActiveCheckBox.setSelected(true);

        contentPanel.add(isActiveCheckBox, "cell 1 3, alignx leading");
    }


    private void addCreateButton() {
        final ManageStationsDialog that=this;
        JButton submitButton = new JButton("Create");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database
                if(validationCheck()) {
                    Station station = new Station();
                    station.setCreatedByEmployeeID(clerk.getEmployeeId());
                    station.setAddr(stationAddressField.getText());
                    station.setName(stationNameField.getText());

                    stationDAO.insertData(station);

                    reloadAllStationsCombobox();
                }else{
                    JOptionPane.showMessageDialog(that,
                            "StationName is empty!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        buttonPanel.add(submitButton);
    }

    private void addStatusButton() {
        JButton statusButton = new JButton("Change Status");
        final ManageStationsDialog that=this;
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database
                Station station = new Station();
                station.setUpdatedByEmployeeID(clerk.getEmployeeId());
                station.setId(currentStation.getId());
                station.setName(currentStation.getName());
                station.setActive(!currentStation.isActive());
                stationDAO.modifyData(station);

                reloadAllStationsCombobox();

                JOptionPane.showMessageDialog(that,
                        "Station : "+station.getName()+" changed to "+ (station.isActive()? "active": "inactive" ),
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(statusButton);
    }

    private void addCancelButton() {
        JButton cancelButton = new JButton("Go Back");
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


    //update All Station ComboBox
    private void reloadAllStationsCombobox() {
        //TODO
        stationDAO.loadData();
        List<Station> stations = stationDAO.getStations();
//        System.out.println("stations.size():"+stations.size());

        allStationComboBoxModel.removeAllElements();

        for (Station next : stations) {
            allStationComboBoxModel.addElement(next.toString());
        }
        System.out.println("allStationComboBoxModel.getSize():"+allStationComboBoxModel.getSize());


        allStationsComboBox.setModel(allStationComboBoxModel);
    }

    /**
     * @return true if no empty textfields for station name and address
     */
    private boolean validationCheck(){
        String stationName=stationNameField.getText();
        String stationAddr=stationAddressField.getText();
        return stationName.length()>0 && stationAddr.length()>0 ;
    }
}
