package ui;

import javax.swing.*;

import com.sun.codemodel.internal.JOp;
import jdk.nashorn.internal.scripts.JO;
import model.User;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;

import static jdbc.JDBC.*;
import jdbc.JDBC;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class LoginDialog extends JDialog{
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private User user;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private MainFrame frame;


    private boolean isClerk;

    public LoginDialog(MainFrame frame, boolean isClerk){


            this.frame=frame;
        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        this.isClerk = isClerk;

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
        final LoginDialog that=this;
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(validationCheck()){
                    if(checkData()){
                        LoginDialog.this.setVisible(false);
                        LoginDialog.this.dispose();
                        System.out.println("LoginDialog:: login successful");
                    }
                }else{
                    JOptionPane.showMessageDialog(that,
                            "Username or Password is empty!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
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

    /**
     * @return true if no empty textFields
     */
    private boolean validationCheck(){
        String password=passwordField.getText();
        String username=usernameField.getText();
        return password.length()>0 && username.length()>0;
    }

    /**
     * check if password and username matches recodes in DB
     * @return true if find user, false otherwise
     */
    private boolean checkData(){
        String password = passwordField.getText();
        String username = usernameField.getText();
        int uid = userLogin(username, password, isClerk);

        if (uid == -1) {
            JOptionPane.showMessageDialog(getLoginDialog(),
                    "Wrong UserType for this mode of operation.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (uid < -1) {
            JOptionPane.showMessageDialog(getLoginDialog(),
                    "Username or password is wrong.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            user = getCurrentUser();

            frame.ticketMenuItem.setEnabled(true);
            frame.trainTypeMenuItem.setEnabled(true);
            frame.recoveryMenuItem.setEnabled(true);
            frame.lineMenuItem.setEnabled(true);
            frame.linestopMenuItem.setEnabled(true);
            frame.stationMenuItem.setEnabled(true);
            frame. passengerMenuItem.setEnabled(true);
            frame. clerkInfoMenuItem.setEnabled(true);
            frame. searchPassengerInfoMenuItem.setEnabled(true);
            frame. viewAllPassInfoMenuItem.setEnabled(true);
            frame.trainMenuItem.setEnabled(true);
            frame.loginMenuItem.setEnabled(false);
            frame.signUpMenuItem.setEnabled(false);
//            frame.purchaseButton.setEnabled(true);
//            frame.purchaseButton.setEnabled(true);

            return true;
        }
    }

    private LoginDialog getLoginDialog() {
        return this;
    }
}
