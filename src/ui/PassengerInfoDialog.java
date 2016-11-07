package ui;
import model.Passenger;
import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class PassengerInfoDialog extends JDialog{

    private Passenger passenger;
    private User user;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JTextField phoneField;

    PassengerInfoDialog(MainFrame frame){

        this.user=frame.getUser();

        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setSize(500,300);

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]",
                "[][][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addUsernameLabel();
        addUsernameField();
        addPasswordLabel();
        addPasswordField();
        addFullNameLabel();
        addFullNameField();
        addPhoneLabel();
        addPhoneField();

        addChangePasswordButton();
        addSaveButton();
        addCancelButton();
    }

    private void addUsernameLabel(){
        JLabel username=new JLabel("Username");
        contentPanel.add(username, "cell 0 1, alignx trailing");
    }

    private void addUsernameField(){
        usernameField =new JTextField();
        contentPanel.add(usernameField,"cell 1 1, growx");
        usernameField.setColumns(10);
    }

    private void addPasswordLabel(){
        JLabel jlUserName=new JLabel("Password");
        contentPanel.add(jlUserName, "cell 0 2, alignx trailing");
    }

    private void addPasswordField(){
        passwordField =new JPasswordField();
        contentPanel.add(passwordField, "cell 1 2, growx");
        passwordField.setColumns(10);
    }

    private void addFullNameLabel(){
        JLabel name=new JLabel("Full Name");
        contentPanel.add(name, "cell 0 4, alignx trailing");
    }

    private void addFullNameField(){
        fullNameField =new JTextField();
        contentPanel.add(fullNameField,"cell 1 4, growx");
        fullNameField.setColumns(10);
    }

    private void addPhoneLabel(){
        JLabel phone=new JLabel("Phone");
        contentPanel.add(phone, "cell 0 5, alignx trailing");
    }

    private void addPhoneField(){
        phoneField =new JTextField();
        contentPanel.add(phoneField,"cell 1 5, growx");
    }

    private void addChangePasswordButton(){
        final PassengerInfoDialog that=this;
        JButton passwordButton=new JButton("Change Password");
        passwordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
                ModifyPasswordDialog dialog=new ModifyPasswordDialog(that);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("PassengerInfoDialog:: Change Password button pressed");
            }
        });
        passwordButton.setActionCommand("Change Password");
        buttonPanel.add(passwordButton);
    }

    private void addSaveButton(){
        JButton saveButton = new JButton("Submit");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO

                System.out.println("PassengerInfoDialog:: Submit button pressed");
            }
        });
        saveButton.setActionCommand("OK");
        buttonPanel.add(saveButton);
    }

    private void addCancelButton(){
        JButton cancelButton=new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PassengerInfoDialog.this.setVisible(false);
                PassengerInfoDialog.this.dispose();
                System.out.println("PassengerInfoDialog:: Cancel button pressed");
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPanel.add(cancelButton);
    }


    public User getUser(){
        return user;
    }

}
