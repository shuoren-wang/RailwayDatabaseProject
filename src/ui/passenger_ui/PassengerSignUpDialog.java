package ui.passenger_ui;

import ui.MainFrame;
import ui.SignUpDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
