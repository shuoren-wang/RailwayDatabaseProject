package ui.clerk_ui;

import ui.MainFrame;
import ui.SignUpDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shuorenwang on 2016-11-06.
 */

//can only be accessed after clerk login
public class ClerkSignUpDialog extends SignUpDialog {

    private JTextField positionField;

    public ClerkSignUpDialog(MainFrame frame) {
        super(frame);
        addPositionLabel();
        addPositionField();
    }

    private void addPositionLabel(){
        JLabel positionLabel=new JLabel("Position");
        contentPanel.add(positionLabel, "cell 0 4, alignx trailing");
    }

    private void addPositionField(){
        positionField =new JTextField("employee");
        contentPanel.add(positionField,"cell 1 4, growx");
        positionField.setColumns(10);
    }

    @Override
    protected void addSubmitButton(){
        final SignUpDialog that=this;
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validationCheck()){
                    JOptionPane.showMessageDialog(that,
                            "Username/Password/FullName is empty!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }else{
                    //TODO
                }

            }
        });
        submitButton.setActionCommand("OK");
        buttonPanel.add(submitButton);
    }


}
