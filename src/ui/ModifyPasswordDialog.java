package ui;

import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shuorenwang on 2016-11-06.
 */
public class ModifyPasswordDialog extends JDialog{
    private User user;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JTextField oldPasswordField;
    private JPasswordField newPasswordField_1;
    private JPasswordField newPasswordField_2;

    public ModifyPasswordDialog(JDialog dialog){

        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setSize(500,200);

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]",
                "[][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addOldPasswordLabel();
        addOldPasswordField();
        addNewPasswordLabel_1();
        addNewPasswordField_1();
        addNewPasswordLabel_2();
        addNewPasswordField_2();

        addSubmitButton();
        addCancelButton();
    }

    private void addOldPasswordLabel(){
        JLabel pwd=new JLabel("Old Password");
        contentPanel.add(pwd, "cell 0 1, alignx trailing");
    }

    private void addOldPasswordField(){
        oldPasswordField =new JPasswordField("");
        contentPanel.add(oldPasswordField, "cell 1 1, growx");
        oldPasswordField.setColumns(10);
    }

    private void addNewPasswordLabel_1(){
        JLabel pwd=new JLabel("New Password");
        contentPanel.add(pwd, "cell 0 2, alignx trailing");
    }

    private void addNewPasswordField_1(){
        newPasswordField_1 =new JPasswordField("");
        contentPanel.add(newPasswordField_1, "cell 1 2, growx");
        newPasswordField_1.setColumns(10);
    }

    private void addNewPasswordLabel_2(){
        JLabel pwd=new JLabel("Re-enter New Password");
        contentPanel.add(pwd, "cell 0 3, alignx trailing");
    }

    private void addNewPasswordField_2(){
        newPasswordField_2 =new JPasswordField("");
        contentPanel.add(newPasswordField_2, "cell 1 3, growx");
        newPasswordField_2.setColumns(10);
    }


    private void addSubmitButton(){
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        submitButton.setActionCommand("OK");
        buttonPanel.add(submitButton);
    }

    private void addCancelButton(){
        JButton cancelButton=new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyPasswordDialog.this.setVisible(false);
                ModifyPasswordDialog.this.dispose();
                System.out.println("SignUpDialog:: Cancel button pressed");
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPanel.add(cancelButton);
    }
}
