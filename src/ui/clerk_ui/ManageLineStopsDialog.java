package ui.clerk_ui;

import model.Clerk;
import model.Line;
import model.LineStop;
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
public class ManageLineStopsDialog extends JDialog {

    private Clerk clerk;
    private LineStop currentLineStop;
    private Station currentStation;
    private Line currentLine;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JComboBox allLineStopsComboBox;
    private DefaultComboBoxModel allLineStopComboBoxModel;
    private JComboBox stationsComboBox;
    private DefaultComboBoxModel stationComboBoxModel;
    private JComboBox lineComboBox;
    private DefaultComboBoxModel lineComboBoxModel;

    private JCheckBox monCheckBox;
    private JCheckBox tuesCheckBox;
    private JCheckBox wedCheckBox;
    private JCheckBox thurCheckBox;
    private JCheckBox friCheckBox;
    private JCheckBox satCheckBox;
    private JCheckBox sunCheckBox;

    public ManageLineStopsDialog(JFrame frame) {
        contentPanel = new JPanel();
        buttonPanel = new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(520, 200);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addAllLineStopsLabel();
        addAllLineStopsComboBox();
        addStationLabel();
        addStationComboBox();
        addLineLabel();
        addLineComboBox();

        addCreateButton();
        addDisableButton();
        addCancelButton();
    }

    private void addAllLineStopsLabel() {
        JLabel label = new JLabel("All LineStops:");
        contentPanel.add(label, "cell 0 0, alignx trailing");
    }

    private void addAllLineStopsComboBox() {
        allLineStopComboBoxModel = new DefaultComboBoxModel();
        allLineStopsComboBox = new JComboBox(allLineStopComboBoxModel);
        allLineStopsComboBox.setMaximumSize(new Dimension(300, 600));
        contentPanel.add(allLineStopsComboBox, "cell 1 0,growx,span");

        ArrayList<LineStop> LineStops = new ArrayList<LineStop>();
        //TODO: get data from database

        if (LineStops.size() > 0) {
            Object[] LineStopsArr = LineStops.toArray();

            synchronized (LineStops.toArray()) {
                for (Object next : LineStopsArr) {
                    allLineStopComboBoxModel.addElement(next);
                }
            }

            allLineStopsComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        currentLineStop = (LineStop) allLineStopsComboBox.getSelectedItem();
                    }
                }
            });
        }
    }

    private void addStationLabel() {
        JLabel label = new JLabel("LineStop Type :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addStationComboBox() {
        stationComboBoxModel = new DefaultComboBoxModel();
        stationsComboBox = new JComboBox(stationComboBoxModel);
        stationsComboBox.setMaximumSize(new Dimension(300, 600));
        contentPanel.add(stationsComboBox, "cell 1 1,growx");

        ArrayList<Station> Stations = new ArrayList<Station>();
        //TODO: get data from database

        if (Stations.size() > 0) {
            Object[] StationsArr = Stations.toArray();

            synchronized (Stations.toArray()) {
                for (Object next : StationsArr) {
                    stationComboBoxModel.addElement(next);
                }
            }

            stationsComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        currentStation = (Station) stationsComboBox.getSelectedItem();
                    }
                }
            });
        }
    }

    private void addLineLabel() {
        JLabel label = new JLabel("Line info :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addLineComboBox() {
        lineComboBoxModel = new DefaultComboBoxModel();
        lineComboBox = new JComboBox(lineComboBoxModel);
        lineComboBox.setMaximumSize(new Dimension(300, 600));
        contentPanel.add(lineComboBox, "cell 1 2,growx,span");

        ArrayList<Line> Lines = new ArrayList<Line>();
        //TODO: get data from database


        if (Lines.size() > 0) {
            Object[] LinesArr = Lines.toArray();

            synchronized (Lines.toArray()) {
                for (Object next : LinesArr) {
                    lineComboBoxModel.addElement(next);
                }
            }

            lineComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        currentLine = (Line) lineComboBox.getSelectedItem();
                    }
                }
            });
        }
    }

    private void addCreateButton() {
        JButton submitButton = new JButton("Create");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database


                updateAllLineStops();
            }
        });
        buttonPanel.add(submitButton);
    }

    private void addDisableButton() {
        JButton submitButton = new JButton("Disable");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database

                updateAllLineStops();
            }
        });
        buttonPanel.add(submitButton);
    }

    private void addCancelButton() {
        JButton cancelButton = new JButton("Go Back");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageLineStopsDialog.this.setVisible(false);
                ManageLineStopsDialog.this.dispose();
                System.out.println("ManageLineStopsDialog:: GoBack button pressed");
            }
        });
        buttonPanel.add(cancelButton);
    }

    //update AllLineStops Combo Box
    private void updateAllLineStops() {
        //TODO database
    }
}
