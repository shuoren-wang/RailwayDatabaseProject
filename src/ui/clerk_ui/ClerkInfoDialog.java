package ui.clerk_ui;

import ui.MainFrame;
import ui.UserInfoDialog;

import javax.swing.*;

/**
 * Created by shuorenwang on 2016-11-06.
 */
public class ClerkInfoDialog extends UserInfoDialog {
    private JTextField positionField;
    public ClerkInfoDialog(MainFrame frame) {
        super(frame);
        addPositionLabel();
        addPositionField();
    }

    private void addPositionLabel(){
        JLabel positionLabel=new JLabel("Position");
        contentPanel.add(positionLabel, "cell 0 4, alignx trailing");
    }

    private void addPositionField(){
        positionField =new JTextField();
        contentPanel.add(positionField,"cell 1 4, growx");
        positionField.setColumns(10);
    }
}
