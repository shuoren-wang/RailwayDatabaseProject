package ui.clerk_ui;

import jdbc.LineDAO;
import jdbc.LineStopDAO;
import jdbc.StationDAO;
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
import java.sql.Time;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-06.
 */

//can only be accessed after clerk login
public class ManageLineStopsDialog extends JDialog {

    private Clerk clerk;
    private LineStop currentLineStop;
    private Station currentStation;
    private Line currentLine;
    protected StationDAO stationDAO;
    protected LineDAO lineDAO;
    protected LineStopDAO lineStopDAO;
    protected int lineIndex;
    protected int stationIndex;
    protected int index;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JComboBox allLineStopsComboBox;
    private DefaultComboBoxModel allLineStopComboBoxModel;
    private JComboBox stationComboBox;
    private DefaultComboBoxModel  stationModel;
    private JComboBox lineComboBox;
    private DefaultComboBoxModel lineComboBoxModel;
    private JTextField arrivalTimeTextField;
    private JTextField stopsForDurationField;
    private JCheckBox statusCheckBox;


    public ManageLineStopsDialog(Clerk clerk) {
        this.clerk=clerk;
        lineDAO=LineDAO.getInstance();
        stationDAO=StationDAO.getInstance();
        lineStopDAO =LineStopDAO.getInstance();
        lineStopDAO.init();
        lineDAO.init();
        stationDAO.init();

        contentPanel = new JPanel();
        buttonPanel = new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(520, 200);
        setTitle("Manage Line Stops");

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addAllLineStopsLabel();
        addAllLineStopsComboBox();
        addStationLabel();
        addStationComboBox();
        addLineLabel();
        addLineComboBox();

        addArrivalTimeLabel();
        addArrivalTimeTextField();
        addStopsForDurationLabel();
        addStopsForDurationField();
        addActiveCheckBox();
        addActiveLabel();
        addCreateButton();
        addStatusButton();
        addCancelButton();
        pack();
    }

    private void addAllLineStopsLabel() {
        JLabel label = new JLabel("All LineStops:");
        contentPanel.add(label, "cell 0 0, alignx trailing");
    }

    private void addAllLineStopsComboBox() {
        final ManageLineStopsDialog that=this;
        lineStopDAO.loadData();
        final List<LineStop> lineStops = lineStopDAO.getLineStops();

        allLineStopComboBoxModel = new DefaultComboBoxModel();
        synchronized (lineStops) {
            for (LineStop next : lineStops) {
                allLineStopComboBoxModel.addElement(next.toString());
            }
        }

        allLineStopsComboBox = new JComboBox(allLineStopComboBoxModel);
        allLineStopsComboBox.setMaximumSize(new Dimension(300, 600));

        allLineStopsComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED &&
                        (allLineStopsComboBox.getSelectedIndex()!= -1) ) {
                    index=allLineStopsComboBox.getSelectedIndex();
                    currentLineStop = LineStopDAO.getInstance().getLineStops().get(index);
                    boolean status = currentLineStop.isActive();

                    that.revalidate();
                    that.repaint();

                    System.out.println("select item: " + status);
                }
            }
        });

        //set default line as the first line in database
        if(currentLineStop==null){
            currentLineStop=lineStopDAO.getLineStops().get(0);
        }

        contentPanel.add(allLineStopsComboBox, "cell 1 0,growx");
    }

    private void addStationLabel() {
        JLabel label = new JLabel("Station :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addStationComboBox() {
        final ManageLineStopsDialog that=this;
        final List<Station> stations = stationDAO.getActiveStations();
        stationModel = new DefaultComboBoxModel();
        synchronized (stations) {
            for (Station next : stations) {
                stationModel.addElement(next.toString());
            }
        }
        stationComboBox = new JComboBox(stationModel);
        stationComboBox.setMinimumSize(new Dimension(400, 27));
        stationComboBox.setMaximumRowCount(10);

        stationComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    stationIndex = stationComboBox.getSelectedIndex();
                    currentStation = stations.get(stationIndex);
                    System.out.println("selected station: " + currentStation.getName());
                }

                that.revalidate();
                that.repaint();
            }
        });
        contentPanel.add(stationComboBox, "cell 1 1,growx");
    }

    private void addLineLabel() {
        JLabel label = new JLabel("Line info :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addLineComboBox() {
        final ManageLineStopsDialog that=this;
        lineDAO.loadData();
        List<Line> lines = lineDAO.getLines();

        lineComboBoxModel = new DefaultComboBoxModel();
        synchronized (lines) {
            for (Line next : lines) {
                lineComboBoxModel.addElement(next.toString());
            }
        }

        lineComboBox = new JComboBox(lineComboBoxModel);
        lineComboBox.setMaximumSize(new Dimension(300, 600));

        lineComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED &&
                        (lineComboBox.getSelectedIndex()!= -1) ) {
                    lineIndex=lineComboBox.getSelectedIndex()-1;
                    currentLine = LineDAO.getInstance().getLines().get(lineIndex);
                    boolean status = currentLine.isActive();

                    that.revalidate();
                    that.repaint();

                    System.out.println("select item: " + status);
                }
            }
        });

        //set default line as the first line in database
        if(currentLine==null){
            currentLine=lineDAO.getLines().get(0);
        }

        contentPanel.add(lineComboBox, "cell 1 2,growx");
    }

    private void addArrivalTimeLabel() {
        JLabel label = new JLabel("Arrival Time :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }

    private void addArrivalTimeTextField() {
        arrivalTimeTextField = new JTextField();
        contentPanel.add(arrivalTimeTextField, "cell 1 3, growx");
    }

    private void addStopsForDurationLabel() {
        JLabel label = new JLabel("Stops For Duration :");
        contentPanel.add(label, "cell 0 4, alignx trailing");
    }

    private void addStopsForDurationField() {
        stopsForDurationField = new JTextField();
        contentPanel.add(stopsForDurationField, "cell 1 4, growx");
    }

    private void addActiveLabel() {
        JLabel label = new JLabel("Active :");
        contentPanel.add(label, "cell 0 5, alignx trailing");
    }

    private void addActiveCheckBox() {
        statusCheckBox = new JCheckBox();
        statusCheckBox.setSelected(true);

        contentPanel.add(statusCheckBox, "cell 1 5, alignx leading");
    }

    protected Time strToTime(String timeStr) {
        return java.sql.Time.valueOf(timeStr);
    }

    private void addCreateButton() {
        final ManageLineStopsDialog that=this;
        JButton submitButton = new JButton("Create");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database
                if(validationCheck()) {
                    LineStop lineStop = new LineStop();
                    lineStop.setCreatedByEmployeeId(clerk.getEmployeeId());
                    lineStop.setStatus(statusCheckBox.isSelected());
                    lineStop.setStopsForDuration(strToTime(stopsForDurationField.getText()));
                    lineStop.setArrivalTime(strToTime(arrivalTimeTextField.getText()));
                    lineStop.setForLineId(currentLine.getId());
                    lineStop.setLocatedStationId(currentStation.getId());

                    System.out.println("ManageLineStopsDialog::addCreateButton()"+lineStop.toString());
                    lineStopDAO.insertData(lineStop);

                    loadAllLineStopsComboBox();
                }else{
                    JOptionPane.showMessageDialog(that,
                            "Line/Station/Arrival/Duration cannot be empty/null!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        buttonPanel.add(submitButton);
    }

    private void addStatusButton() {
        JButton submitButton = new JButton("Status");
        final ManageLineStopsDialog that=this;
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database
                currentLineStop.setUpdatedByEmployeeId(clerk.getEmployeeId());
                currentLineStop.setStatus(!currentLineStop.isActive());

                System.out.println(currentLineStop.toString());

                lineStopDAO.modifyData(currentLineStop);

                JOptionPane.showMessageDialog(that,
                        "LineStop : "+currentLineStop.getLineName()+","+currentLineStop.getStationName()+" changed to "+ (currentLineStop.isActive()? "active": "inactive" ),
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);

                loadAllLineStopsComboBox();
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
    private void loadAllLineStopsComboBox() {
        //TODO database
        List<LineStop> lineStops = lineStopDAO.getLineStops();
//        System.out.println("lineStopss.size():"+lineStopss.size());

        allLineStopComboBoxModel.removeAllElements();

        for (LineStop next : lineStops) {
            allLineStopComboBoxModel.addElement(next.toString());
        }
        System.out.println("allLineStopsComboBoxModel.getSize():"+allLineStopComboBoxModel.getSize());

        allLineStopsComboBox.setModel(allLineStopComboBoxModel);

        index=0;
    }

    /**
     * @return true if currentLine and currentStation not null
     */
    private boolean validationCheck(){
        String arrivalTime=arrivalTimeTextField.getText();
        String duration=stopsForDurationField.getText();

        return currentLine!= null && currentStation !=null
                && arrivalTime!="" && duration!="";
    }
}
