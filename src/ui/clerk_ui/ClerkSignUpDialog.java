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
        positionField =new JTextField();
        contentPanel.add(positionField,"cell 1 4, growx");
        positionField.setColumns(10);
    }

    @Override
    protected void addSubmitButton(){
        super.addSubmitButton();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }


}
