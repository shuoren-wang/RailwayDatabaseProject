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
public class SignUpDialog extends JDialog{

    protected JPanel contentPanel;
    protected JPanel buttonPanel;
    protected JTextField usernameField;
    protected JPasswordField passwordField_1;
    protected JPasswordField passwordField_2;
    protected JTextField fullNameField;
    protected JTextField phoneField;
    protected JButton submitButton;
    

    public SignUpDialog(MainFrame frame){
        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        setResizable(false);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(500,300);

        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("", "[][grow]",
                "[][][][][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        addUsernameLabel();
        addUsernameField();
        addPasswordLabel_1();
        addPasswordField_1();
        addPasswordLabel_2();
        addPasswordField_2();
        addFullNameField();
        addFullNameLabel();

        addSubmitButton();
        addCancelButton();
    }

    protected void addUsernameLabel(){
        JLabel username=new JLabel("Username");
        contentPanel.add(username, "cell 0 0, alignx trailing");
    }

    protected void addUsernameField(){
        usernameField =new JTextField("");
        contentPanel.add(usernameField,"cell 1 0, growx");
        usernameField.setColumns(10);
    }

    protected void addPasswordLabel_1(){
        JLabel jlUserName=new JLabel("Password");
        contentPanel.add(jlUserName, "cell 0 1, alignx trailing");
    }

    protected void addPasswordField_1(){
        passwordField_1 =new JPasswordField("");
        contentPanel.add(passwordField_1, "cell 1 1, growx");
        passwordField_1.setColumns(10);
    }

    protected void addPasswordLabel_2(){
        JLabel jlUserName=new JLabel("Re-enter Password");
        contentPanel.add(jlUserName, "cell 0 2, alignx trailing");
    }

    protected void addPasswordField_2(){
        passwordField_2=new JPasswordField("");
        contentPanel.add(passwordField_2, "cell 1 2, growx");
        passwordField_2.setColumns(10);
    }

    protected void addFullNameLabel(){
        JLabel name=new JLabel("Full Name");
        contentPanel.add(name, "cell 0 3, alignx trailing");
    }

    protected void addFullNameField(){
        fullNameField =new JTextField("");
        contentPanel.add(fullNameField,"cell 1 3, growx");
        fullNameField.setColumns(10);
    }



    protected void addSubmitButton(){
        submitButton = new JButton("Submit");
        buttonPanel.add(submitButton);
    }

    protected void addCancelButton(){
        JButton cancelButton=new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpDialog.this.setVisible(false);
                SignUpDialog.this.dispose();
                System.out.println("SignUpDialog:: Cancel button pressed");
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPanel.add(cancelButton);
    }
}
