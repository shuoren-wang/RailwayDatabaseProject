package ui.clerk_ui;

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
import java.util.ArrayList;

/**
 * Created by shuorenwang on 2016-11-06.
 */

//can only be accessed after clerk login
public class ManageTrainsDialog extends JDialog {
    private Clerk clerk;
    private Train currentTrain;
    private TrainType currentTrainType;
    private Line currentLine;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JComboBox allTrainsComboBox;
    private DefaultComboBoxModel allTrainComboBoxModel;
    private JComboBox trainTypeComboBox;
    private DefaultComboBoxModel trainTypeComboBoxModel;
    private JComboBox lineComboBox;
    private DefaultComboBoxModel lineComboBoxModel;

    private JCheckBox monCheckBox;
    private JCheckBox tuesCheckBox;
    private JCheckBox wedCheckBox;
    private JCheckBox thurCheckBox;
    private JCheckBox friCheckBox;
    private JCheckBox satCheckBox;
    private JCheckBox sunCheckBox;


    public ManageTrainsDialog(JFrame frame){
        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(520,200);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("","[][][][][][][][]","[][][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addAllTrainsLabel();
        addAllTrainsComboBox();
        addTrainTypeLabel();
        addTrainTypeComboBox();
        addLineLabel();
        addLineComboBox();
        addRunOnDaysLabel();
        addRunOnDaysCheckBoxes();

        addCreateButton();
        addModifyButton();
        addCancelButton();
    }

    private void addAllTrainsLabel(){
        JLabel label=new JLabel("All Trains:");
        contentPanel.add(label, "cell 0 0, alignx trailing");
    }

    private void addAllTrainsComboBox(){
        allTrainComboBoxModel=new DefaultComboBoxModel();
        allTrainsComboBox=new JComboBox(allTrainComboBoxModel);
        allTrainsComboBox.setMaximumSize(new Dimension(300,600));
        contentPanel.add(allTrainsComboBox, "cell 1 0,growx,span");

        ArrayList<Train> Trains=new ArrayList<Train>();
        //TODO: get data from database

        if(Trains.size()>0) {
            Object[] TrainsArr = Trains.toArray();

            synchronized (Trains.toArray()) {
                for (Object next : TrainsArr) {
                    allTrainComboBoxModel.addElement(next);
                }
            }

            allTrainsComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        currentTrain = (Train) allTrainsComboBox.getSelectedItem();
                    }
                }
            });
        }
    }

    private void addTrainTypeLabel(){
        JLabel label=new JLabel("Train Type :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addTrainTypeComboBox(){
        trainTypeComboBoxModel=new DefaultComboBoxModel();
        trainTypeComboBox=new JComboBox(trainTypeComboBoxModel);
        trainTypeComboBox.setMaximumSize(new Dimension(300,600));
        contentPanel.add(trainTypeComboBox, "cell 1 1,growx,span");

        ArrayList<TrainType> trainTypes=new ArrayList<TrainType>();
        //TODO: get data from database

        if(trainTypes.size()>0) {
            Object[] TrainsArr = trainTypes.toArray();

            synchronized (trainTypes.toArray()) {
                for (Object next : TrainsArr) {
                    trainTypeComboBoxModel.addElement(next);
                }
            }

            trainTypeComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        currentTrainType = (TrainType) allTrainsComboBox.getSelectedItem();
                    }
                }
            });
        }
    }

    private void addLineLabel(){
        JLabel label=new JLabel("Line info :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addLineComboBox(){
        lineComboBoxModel=new DefaultComboBoxModel();
        lineComboBox=new JComboBox(lineComboBoxModel);
        lineComboBox.setMaximumSize(new Dimension(300,600));
        contentPanel.add(lineComboBox, "cell 1 2,growx,span");

        ArrayList<Line> Lines=new ArrayList<Line>();
        //TODO: get data from database


        if(Lines.size()>0) {
            Object[] LinesArr = Lines.toArray();

            synchronized (Lines.toArray()) {
                for (Object next : LinesArr) {
                    lineComboBoxModel.addElement(next);
                }
            }

            trainTypeComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        currentLine = (Line) lineComboBox.getSelectedItem();
                    }
                }
            });
        }
    }

    private void addRunOnDaysLabel(){
        JLabel label=new JLabel("Run On Days :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }

    private void addRunOnDaysCheckBoxes(){
        monCheckBox=new JCheckBox("Mon",false);
        tuesCheckBox=new JCheckBox("Tue",false);
        wedCheckBox=new JCheckBox("Wed",false);
        thurCheckBox=new JCheckBox("Thu",false);
        friCheckBox=new JCheckBox("Fri",false);
        satCheckBox=new JCheckBox("Sat",false);
        sunCheckBox=new JCheckBox("Sun",false);

        contentPanel.add(monCheckBox, "cell 1 3, alignx leading");
        contentPanel.add(tuesCheckBox, "cell 2 3, alignx leading");
        contentPanel.add(wedCheckBox, "cell 3 3, alignx leading");
        contentPanel.add(thurCheckBox, "cell 4 3, alignx leading");
        contentPanel.add(friCheckBox, "cell 5 3, alignx leading");
        contentPanel.add(satCheckBox, "cell 6 3, alignx leading");
        contentPanel.add(sunCheckBox, "cell 7 3, alignx leading");

    }


    private void addCreateButton(){
        JButton submitButton = new JButton("Create");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database

                updateAllTrains();
            }
        });
        buttonPanel.add(submitButton);
    }

    private void addModifyButton(){
        JButton submitButton = new JButton("Modify");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database

                updateAllTrains();
            }
        });
        buttonPanel.add(submitButton);
    }

    private void addCancelButton(){
        JButton cancelButton=new JButton("Go Back");
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

    private void updateAllTrains(){
        //TODO
    }
}
