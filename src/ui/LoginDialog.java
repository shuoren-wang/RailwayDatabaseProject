package ui;

import javax.swing.*;

import model.User;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class LoginDialog extends JDialog{
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private User user;
    private JTextField usernameField;
    private JPasswordField passwordField;

    LoginDialog(MainFrame frame){

        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        setResizable(false);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(300,160);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("","[][grow]","[][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


        addUsernameLabel();
        addUsernameField();
        addPasswordLabel();
        addPasswordField();
        addSubmitButton();
        addCancelButton();
    }

    private void addUsernameLabel(){
        JLabel jlUserName=new JLabel("Username: ");
        contentPanel.add(jlUserName, "cell 0 0, alignx trailing");
    }

    private void addUsernameField(){
        usernameField =new JTextField();
        contentPanel.add(usernameField,"cell 1 0, growx");

    }

    private void addPasswordLabel(){
        JLabel jlUserName=new JLabel("Password: ");
        contentPanel.add(jlUserName, "cell 0 1, alignx trailing");
    }

    private void addPasswordField(){
        passwordField=new JPasswordField();
        contentPanel.add(passwordField, "cell 1 1, growx");
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
                LoginDialog.this.setVisible(false);
                LoginDialog.this.dispose();
                System.out.println("LoginDialog:: Cancel button pressed");
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPanel.add(cancelButton);
    }

}
