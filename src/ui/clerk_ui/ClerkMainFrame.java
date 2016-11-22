package ui.clerk_ui;

import model.Clerk;
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

    private Clerk clerk;

    protected static final ClerkMainFrame instance = new ClerkMainFrame();

    private ClerkMainFrame() {
        MainFrame clerkFrame = new MainFrame();
        clerkFrame.isClerk = true;
        clerkFrame.isPassenger = false;

        /*
        clerk=new Clerk();
        purchaseButton.setVisible(false);
        manageMenu.remove(ticketMenuItem);
        */
    }

    public static ClerkMainFrame getInstance() {
        return instance;
    }

    @Override
    protected void addManageMenu() {
        super.addManageMenu();

        addManageLinesMenuItem();
        addManageStationsMenuItem();
        addManageTrainsMenuItem();
        addManageLineStopsMenuItem();
        ManageTrainType();
        addSearchPassengerInfo();
        addViewAllPassengerInfoItem();
        addPassengerAccountRecovery();
    }
    
    private void ManageTrainType() {
        final MainFrame that = this;

        trainTypeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ManageTrainType dialog = new ManageTrainType(clerk);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("ClerkMainFrame:: Manage->ManageTrainType is Pressed");
            }
        });
        manageMenu.add(trainTypeMenuItem);

    }
    
    private void addPassengerAccountRecovery() {
        final MainFrame that = this;

        recoveryMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	PassengerAccountRecovery dialog = new PassengerAccountRecovery(clerk);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("ClerkMainFrame:: Recovery->AccountRecovery is Pressed");
            }
        });
        manageMenu.add(recoveryMenuItem);

    }
    
    private void addSearchPassengerInfo() {
        final MainFrame that = this;


        searchPassengerInfoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clerk == null) {
                    JOptionPane.showMessageDialog(that, "Please sign in first.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    SearchPassengerInfo dialog = new SearchPassengerInfo(clerk);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                }
                System.out.println("ClerkMainFrame:: Search->SearchPassengers is Pressed");
            }
        });
        manageMenu.add(searchPassengerInfoMenuItem);

    }
    
    private void addViewAllPassengerInfoItem() {
        final MainFrame that = this;

        viewAllPassInfoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ViewAllPassengerInfo dialog = new ViewAllPassengerInfo(clerk);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("ClerkMainFrame:: View->ViewPassengers is Pressed");
            }
        });
        manageMenu.add(viewAllPassInfoMenuItem);

    }

    private void addManageLineStopsMenuItem() {
        final MainFrame that = this;

        linestopMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageLineStopsDialog dialog = new ManageLineStopsDialog(clerk);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("ClerkMainFrame:: Manage->ManageTrains is Pressed");
            }
        });
        manageMenu.add(linestopMenuItem);

    }

    private void addManageTrainsMenuItem() {
        final MainFrame that = this;

        trainMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageTrainsDialog dialog = new ManageTrainsDialog(clerk);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("ClerkMainFrame:: Manage->ManageTrains is Pressed");
            }
        });
        manageMenu.add(trainMenuItem);

    }

    private void addManageLinesMenuItem() {
        final MainFrame that = this;

        lineMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageLinesDialog dialog = new ManageLinesDialog(clerk);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("ClerkMainFrame:: Manage->ManageLines is Pressed");
            }
        });
        manageMenu.add(lineMenuItem);

    }

    private void addManageStationsMenuItem() {
        final MainFrame that = this;

        stationMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO :database
                ManageStationsDialog dialog = new ManageStationsDialog(clerk);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("ClerkMainFrame:: Manage->ManageStations is Pressed");
            }
        });
        manageMenu.add(stationMenuItem);
    }

    @Override
    protected void addUserInfoMenuItem() {
        final MainFrame that = this;

        clerkInfoMenuItem.addActionListener(new ActionListener() {
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
                System.out.println("ClerkMainFrame:: Manage->ClerkInfo is Pressed");
            }
        });
        manageMenu.add(clerkInfoMenuItem);
    }

    @Override
    protected void addSignUpMenuItem(){
        final MainFrame that = this;

        signUpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpDialog dialog = new ClerkSignUpDialog(that);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("ClerkMainFrame:: Start->ClerkSignUp is Pressed");
            }
        });
        startMenu.add(signUpMenuItem);
    }
}
