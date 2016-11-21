package ui.passenger_ui;

import model.Passenger;
import ui.MainFrame;
import ui.UserInfoDialog;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static jdbc.JDBC.isUsernameTaken;
import static jdbc.JDBC.makePassenger;
import static jdbc.JDBC.updateUserInformation;

/**
 * Created by shuorenwang on 2016-11-06.
 */

//can only be accessed after passenger login
public class PassengerInfoDialog extends UserInfoDialog {

    Passenger passenger;
    private JTextField phoneField;

    PassengerInfoDialog(MainFrame frame) {
        super(frame);
        getPassengerByUserId();
        addPhoneLabel();
        addPhoneField();
        addSaveButton();
    }

    private void addPhoneLabel(){
        JLabel phone=new JLabel("Phone");
        contentPanel.add(phone, "cell 0 3, alignx trailing");
    }

    private void addPhoneField(){
        String phone="";
        if(passenger!=null){
            phone=passenger.getPhone();
        }
        phoneField =new JTextField(phone);
        contentPanel.add(phoneField,"cell 1 3, growx");
    }

    /**
     * find clerk by userid
     * @return
     */
    private Passenger getPassengerByUserId(){
        int userId=getUser().getUserID();
        passenger = makePassenger(userId);

        return passenger;
    }

    private void addSaveButton(){
        JButton saveButton = new JButton("Submit");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("PassengerInfoDialog:: Submit button pressed");

                String username = usernameField.getText();
                String name = fullNameField.getText();
                String phone = phoneField.getText();

                final UserInfoDialog that = getUserInfoDialog();

                if ( !( (username.length() > 0) && (name.length() > 0) && (phone.length() > 0) ) ) {
                    JOptionPane.showMessageDialog(that,
                            "Please fill out all the fields.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else if (!(username.equals(passenger.getUserName())) && isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(that,
                            "Username is already taken, please try another.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    updateUserInformation(passenger.getUserID(), username, name, phone, "0");
                }
            }
        });
        saveButton.setActionCommand("OK");
        // buttonPanel.add(saveButton);
        addSaveButton(saveButton);
    }

}
