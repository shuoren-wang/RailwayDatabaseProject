package ui.clerk_ui;

import jdbc.LineDAO;
import model.Clerk;
import model.Line;
import model.User;
import net.miginfocom.swing.MigLayout;
import ui.MainFrame;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


/**
 * Created by shuorenwang on 2016-11-06.
 */

//can only be accessed after clerk login
public class ManageLinesDialog extends JDialog {
    private Clerk clerk;
    private Line currentLine;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JComboBox allLinesComboBox;
    private DefaultComboBoxModel allLineComboBoxModel;
    private JTextField lineNameField;
    private JCheckBox isActiveCheckBox;
    private JButton statusButton;
    private LineDAO lineDAO;

    private int index=0;

    public ManageLinesDialog(Clerk clerk) {
        this.clerk = clerk;
        lineDAO = LineDAO.getInstance();
        lineDAO.init();

        contentPanel = new JPanel();
        buttonPanel = new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setTitle("Manage Line Info");
        setSize(500, 200);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addAllLinesLabel();
        addAllLinesComboBox();
        addLineNameLabel();
        addLineNameDataLabel();
        addActiveLabel();
        addActiveCheckBox();

        addCreateButton();
        addStatusButton();
        addCancelButton();
    }

    private void addAllLinesLabel() {
        JLabel label = new JLabel("All Lines:");
        contentPanel.add(label, "cell 0 0, alignx trailing");
    }

    private void addAllLinesComboBox() {
        lineDAO.loadData();
        List<Line> lines = lineDAO.getLines();

        allLineComboBoxModel = new DefaultComboBoxModel();
        synchronized (lines) {
            for (Line next : lines) {
                allLineComboBoxModel.addElement(next.toString());
            }
        }

        allLinesComboBox = new JComboBox(allLineComboBoxModel);
        allLinesComboBox.setMaximumSize(new Dimension(300, 600));

        allLinesComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    index=allLinesComboBox.getSelectedIndex();
                    currentLine = LineDAO.getInstance().getLines().get(index);
                    boolean status = currentLine.isActive();

                    System.out.println("select item: " + status);
                }
            }
        });

        //set default line as the first line in database
        if(currentLine==null){
            currentLine=lineDAO.getLines().get(0);
        }
        contentPanel.add(allLinesComboBox, "cell 1 0,growx");
    }

    private void addLineNameLabel() {
        JLabel label = new JLabel("Line Name :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addLineNameDataLabel() {
        String data = "";
        lineNameField = new JTextField(data);
        contentPanel.add(lineNameField, "cell 1 1, growx");
    }

    private void addActiveLabel() {
        JLabel label = new JLabel("Active :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addActiveCheckBox() {
        isActiveCheckBox = new JCheckBox();
        isActiveCheckBox.setSelected(true);

        contentPanel.add(isActiveCheckBox, "cell 1 2, alignx leading");
    }


    private void addCreateButton() {
        final ManageLinesDialog that=this;
        JButton submitButton = new JButton("Create");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database
                if(validationCheck()) {
                    Line line = new Line();
                    line.setCreatedByEmployeeId(clerk.getEmployeeId());
                    line.setLineName(lineNameField.getText());
                    line.setActive(isActiveCheckBox.isSelected());

                    lineDAO.insertData(line);

                    loadAllLinesComboBox();
                }else{
                    JOptionPane.showMessageDialog(that,
                            "LineName is empty!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        buttonPanel.add(submitButton);
    }

    private void addStatusButton() {
        statusButton = new JButton("Change Status");
        final ManageLinesDialog that=this;
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Line line = new Line();
                line.setUpdatedByEmployeeId(clerk.getEmployeeId());
                line.setId(currentLine.getId());
                line.setLineName(currentLine.getLineName());
                line.setActive(!currentLine.isActive());
                lineDAO.modifyData(line);

                loadAllLinesComboBox();

                JOptionPane.showMessageDialog(that,
                        "Line : "+line.getLineName()+" changed to "+ (line.isActive()? "active": "inactive" ),
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
                ManageLinesDialog.this.setVisible(false);
                ManageLinesDialog.this.dispose();
                System.out.println("ManageLinesDialog:: GoBack button pressed");
            }
        });
        buttonPanel.add(cancelButton);
    }


    private void loadAllLinesComboBox() {
        lineDAO.loadData();
        List<Line> lines = lineDAO.getLines();
//        System.out.println("lines.size():"+lines.size());

        allLineComboBoxModel.removeAllElements();

        for (Line next : lines) {
            allLineComboBoxModel.addElement(next.toString());
        }
        System.out.println("allLineComboBoxModel.getSize():"+allLineComboBoxModel.getSize());


        allLinesComboBox.setModel(allLineComboBoxModel);
    }

    /**
     * @return true if no empty textFields
     */
    private boolean validationCheck(){
        String lineName=lineNameField.getText();
        return lineName.length()>0 ;
    }
}
