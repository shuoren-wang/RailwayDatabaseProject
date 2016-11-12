package ui.clerk_ui;

import jdbc.LineDAO;
import jdbc.TrainDAO;
import jdbc.TrainTypeDAO;
import model.Clerk;
import model.Line;
import model.Train;
import model.TrainType;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-06.
 */

//can only be accessed after clerk login
public class ManageTrainsDialog extends JDialog {
    private Clerk clerk;
    private Train currentTrain;
    private TrainType currentTrainType;
    private Line currentLine;
    private TrainDAO trainDAO;
    private TrainTypeDAO trainTypeDAO;
    private LineDAO lineDAO;
    private int lineIndex;
    private int trainTypeIndex;
    private int trainIndex;


    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JComboBox trainComboBox;
    private DefaultComboBoxModel trainComboBoxModel;
    private JComboBox trainTypeComboBox;
    private DefaultComboBoxModel trainTypeComboBoxModel;
    private JComboBox lineComboBox;
    private DefaultComboBoxModel lineComboBoxModel;

    private JButton statusButton;
    private JCheckBox monCheckBox;
    private JCheckBox tuesCheckBox;
    private JCheckBox wedCheckBox;
    private JCheckBox thurCheckBox;
    private JCheckBox friCheckBox;
    private JCheckBox satCheckBox;
    private JCheckBox sunCheckBox;


    public ManageTrainsDialog(Clerk clerk) {
        this.clerk=clerk;
        lineDAO = LineDAO.getInstance();
        trainDAO = trainDAO.getInstance();
        trainTypeDAO = trainTypeDAO.getInstance();
        lineDAO.init();
        trainDAO.init();
        trainTypeDAO.init();
        contentPanel = new JPanel();
        buttonPanel = new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(520, 200);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][][][][][][][]", "[][][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addTrainsLabel();
        addTrainsComboBox();
        addTrainTypeLabel();
        addTrainTypeComboBox();
        addLineLabel();
        addLineComboBox();
        addRunOnDaysLabel();
        addRunOnDaysCheckBoxes();

        addCreateButton();
        addDisableButton();
        addCancelButton();
    }

    private void addTrainsLabel() {
        JLabel label = new JLabel("All Trains:");
        contentPanel.add(label, "cell 0 0, alignx trailing");
    }

    private void addTrainsComboBox() {
        trainDAO.loadData();
        List<Train> trains = trainDAO.getTrains();

        trainComboBoxModel = new DefaultComboBoxModel();
        synchronized (trains) {
            for (Train next : trains) {
                trainComboBoxModel.addElement(next.toString());
            }
        }

        trainComboBox = new JComboBox(trainComboBoxModel);
        trainComboBox.setMaximumSize(new Dimension(300, 600));

        trainComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    trainIndex = trainComboBox.getSelectedIndex();
                    currentTrain = TrainDAO.getInstance().getTrains().get(trainIndex);
                    System.out.println("ManageTrainsDialog:: trainComboBox: selected a train");
                }
            }
        });

        //set default line as the first line in database
        if(currentTrain==null){
            currentTrain=trainDAO.getTrains().get(0);
        }

        contentPanel.add(trainComboBox, "cell 1 0,growx,span");
    }

    private void addTrainTypeLabel() {
        JLabel label = new JLabel("Train Type :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addTrainTypeComboBox() {
        trainTypeDAO.loadData();
        List<TrainType> trainTypes = trainTypeDAO.getTrainTypes();

        trainTypeComboBoxModel = new DefaultComboBoxModel();
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
                    currentTrainType = TrainTypeDAO.getInstance().getTrainTypes().get(trainTypeIndex);
                    System.out.println("ManageTrainsDialog:: trainTypeComboBox: selected a trainType");
                }
            }
        });

        //set default traintype as the first train type in database
        if(currentTrainType==null) {
            currentTrainType =trainTypeDAO.getTrainTypes().get(0);
        }

        contentPanel.add(trainTypeComboBox, "cell 1 1,growx,span");
    }

    private void addLineLabel() {
        JLabel label = new JLabel("Line info :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addLineComboBox() {
        lineDAO.loadData();
        List<Line> lines = lineDAO.getLines();

        lineComboBoxModel = new DefaultComboBoxModel();
        synchronized (lines) {
            for (Line next : lines) {
                if(next.isActive()) {
                    lineComboBoxModel.addElement(next.toString());
                }
            }
        }

        lineComboBox = new JComboBox(lineComboBoxModel);
        lineComboBox.setMaximumSize(new Dimension(300, 600));

        trainTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    lineIndex = lineComboBox.getSelectedIndex();
                    currentLine = LineDAO.getInstance().getLines().get(lineIndex);
                    System.out.println("ManageTrainsDialog:: LineComboBox: selected a line");
                }
            }
        });

        //set default line as the first line in database
        if(currentLine==null){
            currentLine=lineDAO.getLines().get(0);
        }

        contentPanel.add(lineComboBox, "cell 1 2,growx,span");
    }

    private void addRunOnDaysLabel() {
        JLabel label = new JLabel("Run On Days :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }

    private void addRunOnDaysCheckBoxes() {
        monCheckBox = new JCheckBox("Mon", false);
        tuesCheckBox = new JCheckBox("Tue", false);
        wedCheckBox = new JCheckBox("Wed", false);
        thurCheckBox = new JCheckBox("Thu", false);
        friCheckBox = new JCheckBox("Fri", false);
        satCheckBox = new JCheckBox("Sat", false);
        sunCheckBox = new JCheckBox("Sun", false);

        contentPanel.add(monCheckBox, "cell 1 3, alignx leading");
        contentPanel.add(tuesCheckBox, "cell 2 3, alignx leading");
        contentPanel.add(wedCheckBox, "cell 3 3, alignx leading");
        contentPanel.add(thurCheckBox, "cell 4 3, alignx leading");
        contentPanel.add(friCheckBox, "cell 5 3, alignx leading");
        contentPanel.add(satCheckBox, "cell 6 3, alignx leading");
        contentPanel.add(sunCheckBox, "cell 7 3, alignx leading");
    }


    private void addCreateButton() {
        final ManageTrainsDialog that=this;
        JButton submitButton = new JButton("Create");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validationCheck()) {

                    Train train = new Train();
                    train.setCreatedByEmployeeID(clerk.getEmployeeId());
                    train.setTrainTypeId(currentTrainType.getTypeId());
                    train.setLineId(currentLine.getId());
                    train.setRunsOnMon(monCheckBox.isSelected());
                    train.setRunsOnTue(tuesCheckBox.isSelected());
                    train.setRunsOnWed(wedCheckBox.isSelected());
                    train.setRunsOnThu(thurCheckBox.isSelected());
                    train.setRunsOnFri(friCheckBox.isSelected());
                    train.setRunsOnSat(satCheckBox.isSelected());
                    train.setRunsOnSun(sunCheckBox.isSelected());
                    trainDAO.insertData(train);

                    loadTrainsComboBox();
                }else{
                    JOptionPane.showMessageDialog(that,
                            "Please select train type, line and at least 1 runsOnday!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        buttonPanel.add(submitButton);
    }

    private void addDisableButton() {
        statusButton = new JButton("Change Status");
        final ManageTrainsDialog that=this;
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Train train = new Train();
                train.setUpdatedByEmployeeID(clerk.getEmployeeId());
                train.setId(currentTrain.getId());
                train.setTrainTypeId(currentTrain.getTrainTypeId());
                train.setLineId(currentTrain.getId());
                train.setRunsOnMon(currentTrain.isRunsOnMon());
                train.setRunsOnTue(currentTrain.isRunsOnTue());
                train.setRunsOnWed(currentTrain.isRunsOnWed());
                train.setRunsOnThu(currentTrain.isRunsOnThu());
                train.setRunsOnFri(currentTrain.isRunsOnFri());
                train.setRunsOnSat(currentTrain.isRunsOnSat());
                train.setRunsOnSun(currentTrain.isRunsOnSun());
                //TODO: setActive after spModifyTrain updated; also need to change for trainDAO.modifyData()
//                train.setActive(!currentTrain.isActive());

                //TODO: spModifyTrain not exist, will check later
                trainDAO.modifyData(train);

                loadTrainsComboBox();

                JOptionPane.showMessageDialog(that,
                        "Trian : "+train.getLineName()+" changed to "+ (train.isActive()? "active": "inactive" ),
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
                ManageTrainsDialog.this.setVisible(false);
                ManageTrainsDialog.this.dispose();
                System.out.println("ManageTrainsDialog:: GoBack button pressed");
            }
        });
        buttonPanel.add(cancelButton);
    }

    private void loadTrainsComboBox() {
        trainDAO.loadData();
        List<Train> trains = trainDAO.getTrains();

        trainComboBoxModel.removeAllElements();

        for (Train next : trains) {
            trainComboBoxModel.addElement(next.toString());
        }
        System.out.println("ManageTrainsDialog:: allLineComboBoxModel.getSize():" + trainComboBoxModel.getSize());

        trainComboBox.setModel(trainComboBoxModel);
    }

    /**
     * @return false if all runOndays are unchecked
     */
    protected boolean validationCheck() {
        if (!monCheckBox.isSelected() && !tuesCheckBox.isSelected() && !wedCheckBox.isSelected() && !thurCheckBox.isSelected() &&
                !friCheckBox.isSelected() && !satCheckBox.isSelected() && !sunCheckBox.isSelected()) {
            return false;
        } else {
            return true;
        }
    }
}
