package ui.passenger_ui;

import model.User;
import ui.MainFrame;
import ui.SignUpDialog;
import ui.UserInfoDialog;
import ui.clerk_ui.ClerkInfoDialog;
import ui.clerk_ui.ClerkSignUpDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shuorenwang on 2016-11-06.
 */
public class PassengerMainFrame extends MainFrame {

    protected static final PassengerMainFrame instance = new PassengerMainFrame();

    private PassengerMainFrame() {
        super();
    }


    public static PassengerMainFrame getInstance() {
        return instance;
    }

    @Override
    protected void addManageMenu() {
        super.addManageMenu();
    }

    @Override
    protected void addUserInfoMenuItem() {
        final MainFrame that = this;
        JMenuItem userMenuItem = new JMenuItem("PassengerInfo");
        userMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user == null) {
                    /*JOptionPane.showMessageDialog(that,
                            "Sign In Please!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);*/

                    /*only for test*/
                    user = new User();
                    UserInfoDialog dialog = new PassengerInfoDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                    /*only for test*/
                } else {
                    UserInfoDialog dialog = new PassengerInfoDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                }
                System.out.println("PassengerMainFrame:: Manage->PassengerInfo is Pressed");
            }
        });
        manageMenu.add(userMenuItem);
    }

    @Override
    protected void addSignUpMenuItem(){
        final MainFrame that = this;

        JMenuItem signUpMenuItem = new JMenuItem("Sign Up");
        signUpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpDialog dialog = new PassengerSignUpDialog(that);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("PassengerMainFrame:: Start->PassengerSignUp is Pressed");
            }
        });
        startMenu.add(signUpMenuItem);
    }
}
