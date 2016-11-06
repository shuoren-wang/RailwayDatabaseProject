package ui;

import model.Passenger;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class PassengerSignUpDialog extends JDialog{
    static Logger LOG = Logger.getLogger(LoginDialog.class);

    private Passenger passenger;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JTextField usernameField;
    private JPasswordField passwordField_1;
    private JPasswordField passwordField_2;
    private JTextField fullNameField;
    private JTextField phoneField;

    PassengerSignUpDialog(JFrame frame){

        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        setResizable(false);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(500,500);

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]",
                "[][][][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        addUsernameLabel();
        addUsernameField();
        addPasswordLabel_1();
        addPasswordField_1();
        addPasswordLabel_2();
        addPasswordField_2();
        addFullNameField();
        addFullNameLabel();
        addPhoneLabel();
        addPhoneField();
        addSubmitButton();
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

    private void addPasswordLabel_1(){
        JLabel jlUserName=new JLabel("Password");
        contentPanel.add(jlUserName, "cell 0 2, alignx trailing");
    }

    private void addPasswordField_1(){
        passwordField_1 =new JPasswordField();
        contentPanel.add(passwordField_1, "cell 1 2, growx");
        passwordField_1.setColumns(10);
    }

    private void addPasswordLabel_2(){
        JLabel jlUserName=new JLabel("re-enter Password");
        contentPanel.add(jlUserName, "cell 0 3, alignx trailing");
    }

    private void addPasswordField_2(){
        passwordField_2=new JPasswordField();
        contentPanel.add(passwordField_2, "cell 1 3, growx");
        passwordField_2.setColumns(10);
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

    private void addSubmitButton(){
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO

            }
        });
        submitButton.setActionCommand("OK");
        contentPanel.add(submitButton);
    }

    private void addCancelButton(){
        JButton cancelButton=new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PassengerSignUpDialog.this.setVisible(false);
                PassengerSignUpDialog.this.dispose();
                LOG.info("PassengerSignUpDialog: Cancel button pressed");
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPanel.add(cancelButton);
    }
}
