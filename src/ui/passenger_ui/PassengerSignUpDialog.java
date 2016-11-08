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
        super.addSubmitButton();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database
            }
        });
    }
}
