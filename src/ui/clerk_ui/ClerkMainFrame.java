package ui.clerk_ui;

import model.User;
import ui.MainFrame;
import ui.SignUpDialog;
import ui.UserInfoDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shuorenwang on 2016-11-06.
 */
public class ClerkMainFrame extends MainFrame {

    protected static final ClerkMainFrame instance = new ClerkMainFrame();

    private ClerkMainFrame() {
        super();
    }

    public static ClerkMainFrame getInstance() {
        return instance;
    }

    @Override
    protected void addUserInfoMenuItem(JMenu manageMenu) {
        final MainFrame that = this;
        JMenuItem userMenuItem = new JMenuItem("ClerkInfo");
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
                    UserInfoDialog dialog = new ClerkInfoDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                    /*only for test*/
                } else {
                    UserInfoDialog dialog = new ClerkInfoDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                }
                System.out.println("MainFrame:: Manage->ClerkInfo is Pressed");
            }
        });
        manageMenu.add(userMenuItem);
    }

    @Override
    protected void addSignUpMenuItem(JMenu startMenu){
        final MainFrame that = this;

        JMenuItem signUpMenuItem = new JMenuItem("Sign Up");
        signUpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpDialog dialog = new ClerkSignUpDialog(that);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("MainFrame:: Start->ClerkSignUp is Pressed");
            }
        });
        startMenu.add(signUpMenuItem);
    }
}
