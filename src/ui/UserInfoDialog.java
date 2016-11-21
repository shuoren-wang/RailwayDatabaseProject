package ui;
import model.Passenger;
import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static jdbc.JDBC.getCurrentUser;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class UserInfoDialog extends JDialog{
    protected User user;

    protected JPanel contentPanel;
    private JPanel buttonPanel;
    protected JTextField usernameField;
    protected JTextField fullNameField;


    public UserInfoDialog(MainFrame frame){

        // this.user=frame.getUser();
        user = getCurrentUser();

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Please sign in first.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            contentPanel = new JPanel();
            buttonPanel = new JPanel();

            setResizable(false);
            setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            setSize(500, 200);

            getContentPane().add(contentPanel, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            contentPanel.setLayout(new MigLayout("", "[][grow]",
                    "[][][][][]"));
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

            addUsernameLabel();
            addUsernameField();
            addFullNameLabel();
            addFullNameField();

            addChangePasswordButton();
            // addSaveButton();
            addCancelButton();
        }
    }

    private void addUsernameLabel(){
        JLabel username=new JLabel("Username");
        contentPanel.add(username, "cell 0 1, alignx trailing");
    }

    private void addUsernameField(){
        usernameField =new JTextField(user.getUserName());
        contentPanel.add(usernameField,"cell 1 1, growx");
        usernameField.setColumns(10);
    }

    private void addFullNameLabel(){
        JLabel name=new JLabel("Full Name");
        contentPanel.add(name, "cell 0 2, alignx trailing");
    }

    private void addFullNameField(){
        fullNameField =new JTextField(user.getName());
        contentPanel.add(fullNameField,"cell 1 2, growx");
        fullNameField.setColumns(10);
    }



    private void addChangePasswordButton(){
        final UserInfoDialog that=this;
        JButton passwordButton=new JButton("Change Password");
        passwordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyPasswordDialog dialog=new ModifyPasswordDialog(that);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("UserInfoDialog:: Change Password button pressed");
            }
        });
        passwordButton.setActionCommand("Change Password");
        buttonPanel.add(passwordButton);
    }

/* Moving to ClerkInfoDialog.java and PassengerInfoDialogue.java
    private void addSaveButton(){
        JButton saveButton = new JButton("Submit");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("UserInfoDialog:: Submit button pressed");
            }
        });
        saveButton.setActionCommand("OK");
        buttonPanel.add(saveButton);
    }
*/

    public void addSaveButton(JButton button) {
        buttonPanel.add(button);
    }

    private void addCancelButton(){
        JButton cancelButton=new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInfoDialog.this.setVisible(false);
                UserInfoDialog.this.dispose();
                System.out.println("UserInfoDialog:: Cancel button pressed");
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPanel.add(cancelButton);
    }

    public User getUser(){
        return user;
    }

    protected UserInfoDialog getUserInfoDialog() {
        return this;
    }

}
