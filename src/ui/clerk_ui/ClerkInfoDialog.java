package ui.clerk_ui;

import model.Clerk;
import ui.MainFrame;
import ui.UserInfoDialog;

import javax.swing.*;

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

    /**
     * find clerk by userid
     * @return
     */
    private Clerk getClerkByUserId(){
        int userId=getUser().getUserID();
        //TODO: database

        return clerk;
    }

}
