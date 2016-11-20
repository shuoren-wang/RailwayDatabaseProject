package ui.passenger_ui;

import ui.MainFrame;
import ui.SignUpDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static jdbc.JDBC.*;

/**
 * Created by shuorenwang on 2016-11-06.
 */
//can only be accessed after passenger login
public class PassengerSignUpDialog extends SignUpDialog {

    public PassengerSignUpDialog(MainFrame frame) {
        super(frame);

        addPhoneLabel();
        addPhoneField();
    }


    protected void addPhoneLabel(){
        JLabel phone=new JLabel("Phone");
        contentPanel.add(phone, "cell 0 4, alignx trailing");
    }

    protected void addPhoneField(){
        phoneField =new JTextField("");
        contentPanel.add(phoneField,"cell 1 4, growx");
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
                }else{
                    String newUserName = usernameField.getText();
                    String newPassword = passwordField_1.getText();
                    String newFullName = fullNameField.getText();
                    String newPhoneNum = phoneField.getText();

                    int newUid = passengerSignUp(newUserName, newPassword, newFullName, newPhoneNum);
                }

            }
        });
        submitButton.setActionCommand("OK");
        buttonPanel.add(submitButton);
    }
}
