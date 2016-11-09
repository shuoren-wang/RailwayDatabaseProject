package ui.clerk_ui;

import jdk.nashorn.internal.scripts.JD;
import model.Line;
import model.Ticket;
import model.User;
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
public class ManageLinesDialog extends JDialog{
    private User user;
    private Line currentLine;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JComboBox allLinesComboBox;
    private DefaultComboBoxModel allLineComboBoxModel;
    private JTextField lineNameField;
    private JCheckBox isActiveCheckBox;


    public ManageLinesDialog(JFrame frame){
        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(500,200);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("","[][grow]","[][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addAllLinesLabel();
        addAllLinesComboBox();
        addLineNameLabel();
        addLineNameDataLabel();
        addActiveLabel();
        addActiveCheckBox();


        addCreateButton();
        addCancelButton();
    }

    private void addAllLinesLabel(){
        JLabel label=new JLabel("All Lines:");
        contentPanel.add(label, "cell 0 0, alignx trailing");
    }

    private void addAllLinesComboBox(){
        allLineComboBoxModel=new DefaultComboBoxModel();
        allLinesComboBox=new JComboBox(allLineComboBoxModel);
        allLinesComboBox.setMaximumSize(new Dimension(300,600));
        contentPanel.add(allLinesComboBox, "cell 1 0,growx");

        ArrayList<Line> lines=new ArrayList<Line>();
        //TODO: get data from database

        if(lines.size()>0) {
            Object[] linesArr = lines.toArray();

            synchronized (lines.toArray()) {
                for (Object next : linesArr) {
                    allLineComboBoxModel.addElement(next);
                }
            }

            allLinesComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        currentLine = (Line) allLinesComboBox.getSelectedItem();
                        update();
                    }
                }
            });
        }

    }

    private void addLineNameLabel(){
        JLabel label=new JLabel("Line Name :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addLineNameDataLabel(){
        String data="";
        if(currentLine!=null) {
            data= currentLine.getLineName();
        }
        lineNameField=new JTextField(data);
        contentPanel.add(lineNameField, "cell 1 1, growx");
    }

    private void addActiveLabel(){
        JLabel label=new JLabel("Active :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addActiveCheckBox(){
        isActiveCheckBox=new JCheckBox();
        if(currentLine!=null) {
            isActiveCheckBox.setSelected(currentLine.isActive());
        }else{
            isActiveCheckBox.setSelected(true);
        }
        contentPanel.add(isActiveCheckBox, "cell 1 2, alignx leading");
    }


    private void addCreateButton(){
        JButton submitButton = new JButton("Create");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database

                updateAllLines();
            }
        });
        buttonPanel.add(submitButton);
    }



    private void addCancelButton(){
        JButton cancelButton=new JButton("Go Back");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageLinesDialog.this.setVisible(false);
                ManageLinesDialog.this.dispose();
                System.out.println("ManageLinesDialog:: GoBack button pressed");
            }
        });
        buttonPanel.add(cancelButton);
    }

    private void update(){
        lineNameField.setText(currentLine.getLineName());
        isActiveCheckBox.setSelected(currentLine.isActive());
    }

    //update AllLines Combo Box
    private void updateAllLines(){
        //TODO
    }
}
