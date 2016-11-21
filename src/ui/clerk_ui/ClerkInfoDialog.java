package ui.clerk_ui;

import model.Clerk;
import ui.MainFrame;
import ui.UserInfoDialog;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static jdbc.JDBC.isUsernameTaken;
import static jdbc.JDBC.makeClerk;
import static jdbc.JDBC.updateUserInformation;

/**
 * Created by shuorenwang on 2016-11-06.
 */

//can only be accessed after clerk login
public class ClerkInfoDialog extends UserInfoDialog {
    private Clerk clerk;
    private JTextField positionField;
    public ClerkInfoDialog(MainFrame frame) {
        super(frame);
        clerk=getClerkByUserId();
        addPositionLabel();
        addPositionField();
        addSaveButton();
    }

    private void addPositionLabel(){
        JLabel positionLabel=new JLabel("Position");
        contentPanel.add(positionLabel, "cell 0 3, alignx trailing");
    }

    private void addPositionField(){
        String position="employee";
        if(clerk!=null) {
            position=clerk.getPosition();
        }
        positionField =new JTextField(position);
        contentPanel.add(positionField,"cell 1 3, growx");
        positionField.setColumns(10);
    }

    private void addSaveButton(){
        JButton saveButton = new JButton("Submit");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("ClerkInfoDialog:: Submit button pressed");

                String username = usernameField.getText();
                String name = fullNameField.getText();
                String position = positionField.getText();

                final UserInfoDialog that = getUserInfoDialog();

                if ( !( (username.length() > 0) && (name.length() > 0) && (position.length() > 0) ) ) {
                    JOptionPane.showMessageDialog(that,
                            "Please fill out all the fields.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else if (!(username.equals(clerk.getUserName())) && isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(that,
                            "Username is already taken, please try another.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    updateUserInformation(clerk.getUserID(), username, name, "0", position);
                }
            }
        });
        saveButton.setActionCommand("OK");
        // buttonPanel.add(saveButton);
        addSaveButton(saveButton);
    }

    /**
     * find clerk by userid
     * @return
     */
    private Clerk getClerkByUserId(){
        int userId=getUser().getUserID();
        clerk = makeClerk(userId);

        return clerk;
    }

}
