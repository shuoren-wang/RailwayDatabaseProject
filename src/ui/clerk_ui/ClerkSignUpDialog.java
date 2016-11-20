package ui.clerk_ui;

import ui.MainFrame;
import ui.SignUpDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static jdbc.JDBC.*;

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
                if(validationCheck() == false){
                    JOptionPane.showMessageDialog(that,
                            "Please make sure your passwords match and that you fill out all the fields.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }else if (!(positionField.getText().length() > 0)) {
                    JOptionPane.showMessageDialog(that, "Please fill out your position.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    String newUserName = usernameField.getText();
                    String newPassword = passwordField_1.getText();
                    String newFullName = fullNameField.getText();
                    String newPosition = positionField.getText();

                    int newUid = clerkSignUp(newUserName, newPassword, newFullName, newPosition);
                }

            }
        });
        submitButton.setActionCommand("OK");
        buttonPanel.add(submitButton);
    }


}
