package ui;

import model.Station;
import model.TrainByStops;
import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import static jdbc.JDBC.*;



/**
 * Created by shuorenwang on 2016-10-21.
 */
public class MainFrame extends JFrame {
    protected User user;
    protected boolean loginStatus; //signed in=>true, otherwise=>false

    protected static final MainFrame instance = new MainFrame();
    protected JMenuBar menuBar;
    protected JPanel mainWindow;

    protected JMenu manageMenu;
    protected JMenu startMenu;

    protected JComboBox fromStationComboBox;
    protected DefaultComboBoxModel fromStationModel;
    protected JComboBox toStationComboBox;
    protected DefaultComboBoxModel toStationModel;
    protected JTextField dateField;
    protected JList trainsList;
    protected DefaultListModel trainsListModel;
    protected TrainByStops currentTrainByStops;
    protected int index;
    protected ArrayList<TrainByStops> trainByStopses;


    public static MainFrame getInstance() {
        return instance;
    }

    public MainFrame() {
        setTitle("304 Project");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                closeCon();

                System.exit(0);
            }
        });

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        addStartMenu();
        addManageMenu();

        mainWindow = new JPanel();
        mainWindow.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainWindow.setLayout(new MigLayout("",
                "[][grow][]", "[][][][grow][]"));
        setContentPane(mainWindow);

        JLabel fromStationLabel = new JLabel("From");
        mainWindow.add(fromStationLabel, "cell 0 0");

        JLabel toStationLabel = new JLabel("To");
        mainWindow.add(toStationLabel, "cell 0 1");

        JLabel dateLabel = new JLabel("Date");
        mainWindow.add(dateLabel, "cell 0 2");

        addFromStationComboBox();
        addToStationComboBox();
        addDateField();
        addPurchaseButton();
        addGetTrainsButton();
        addTrainsList();
    }

    protected void addFromStationComboBox() {
        ArrayList<Station> stations = new ArrayList<Station>();
        //TODO: get data from database

        ArrayList<String> statNameArr = new ArrayList<String>();
        for (Station next : stations) {
            statNameArr.add(next.getName());
        }

        fromStationModel = new DefaultComboBoxModel();
        synchronized (statNameArr) {
            for (String next : statNameArr) {
                fromStationModel.addElement(next);
            }
        }
        fromStationComboBox = new JComboBox(fromStationModel);
        fromStationComboBox.setMinimumSize(new Dimension(400, 27));
        fromStationComboBox.setMaximumRowCount(10);
        mainWindow.add(fromStationComboBox, "cell 1 0, growx");
    }

    protected void addToStationComboBox() {
        ArrayList<Station> stations = new ArrayList<Station>();
        //TODO: get data from database

        ArrayList<String> statNameArr = new ArrayList<String>();
        for (Station next : stations) {
            statNameArr.add(next.getName());
        }

        toStationModel = new DefaultComboBoxModel();
        synchronized (statNameArr) {
            for (String next : statNameArr) {
                toStationModel.addElement(next);
            }
        }
        toStationComboBox = new JComboBox(toStationModel);
        toStationComboBox.setMinimumSize(new Dimension(400, 27));
        toStationComboBox.setMaximumRowCount(10);
        mainWindow.add(toStationComboBox, "cell 1 1, growx");
    }

    protected void addGetTrainsButton() {
        JButton getTrainsButton = new JButton("Get Trains");
        mainWindow.add(getTrainsButton, "cell 2 2, alignx trailing");

        getTrainsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trainByStopses = new ArrayList<TrainByStops>();
                //TODO: get data from database
                /*
                try {
                    trainByStopses = fillTrains();
                } catch (SQLException f) {
                    f.printStackTrace();
                }
                */
            }
        });

    }

    protected void addTrainsList() {

        trainsListModel = new DefaultListModel();
        trainsList = new JList(trainsListModel);
        JScrollPane listScroller = new JScrollPane(trainsList);

        trainsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        trainsList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setPreferredSize(new Dimension(400, 200));
        mainWindow.add(listScroller, "cell 0 3 3 1,grow,span");
        trainsListModel.addElement(String.format("%25s %25s %25s %25s %25s",
                "Line Id.","TrainByStops Nos","Seat Class","Available Seats","Departure Time"));

        if (trainByStopses != null && trainByStopses.size() > 0) {
            ArrayList<String> trainsInfoArr = new ArrayList<String>();
            for (TrainByStops next : trainByStopses) {
                trainsInfoArr.add(next.toString());
            }
            synchronized (trainsInfoArr) {
                for (String next : trainsInfoArr) {
                    trainsListModel.addElement(next);
                }
            }

            trainsList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting() == false) {
                        if (trainsList.getSelectedIndex() != -1) {
                            index = trainsList.getSelectedIndex()-1;
                            currentTrainByStops = trainByStopses.get(index);
                        }
                    }
                }
            });
        }
    }

    protected void addDateField() {
        dateField = new JTextField();
        mainWindow.add(dateField, "cell 1 2, growx");
    }

    protected void addPurchaseButton() {
        JButton purchaseButton = new JButton("Purchase");
        mainWindow.add(purchaseButton, "cell 2 4");

        final MainFrame that = this;
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentTrainByStops == null) {
                    JOptionPane.showMessageDialog(that,
                            "No train is selected!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);

                } else {

                    int returnedTicketId=0;
                    //TODO database:
                    // (SP NAME: spReturnTicket) return string/int;

                    if(isTicketIdReturned()){
                        JOptionPane.showMessageDialog(that,
                                "Purchase successful ! You Ticket ID is"+ Integer.toString(returnedTicketId),
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(that,
                                "Purchase failed",
                                "Info",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
                System.out.println("MainFrame::Purchase Button is Pressed");
            }
        });
    }

    /**
     * fails if requesting UserId is not clerk and not owner of ticket or ticket doesnt exist
     * (SP NAME:  spReturnTicket)
     * @return cell(0,0) is 1 if success, 0 if failure.
     */
    protected boolean isTicketIdReturned(){
        //TODO:
        return false;
    }

    protected void addStartMenu() {
        startMenu = new JMenu("Start");
        menuBar.add(startMenu);

        addSignUpMenuItem();
        addLoginMenuItem();
    }

    protected void addLoginMenuItem(){
        final MainFrame that = this;
        JMenuItem loginMenuItem = new JMenuItem("Sign In");
        loginMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog dialog = new LoginDialog(that);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("MainFrame:: Start->Login is Pressed");
            }
        });
        startMenu.add(loginMenuItem);
    }


    protected void addSignUpMenuItem(){
        final MainFrame that = this;

        JMenuItem signUpMenuItem = new JMenuItem("Sign Up");
        signUpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpDialog dialog = new SignUpDialog(that);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("MainFrame:: Start->SignUp is Pressed");
            }
        });
        startMenu.add(signUpMenuItem);
    }

    protected void addPurchasedTicketsMenuItem() {
        final MainFrame that = this;
        JMenuItem ticketMenuItem = new JMenuItem("Purchased Tickets");
        ticketMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (user == null) {
                   /* JOptionPane.showMessageDialog(that,
                            "Sign In Please!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);*/

                     /*only for test*/
                    user = new User();
                    PurchasedTicketsDialog dialog = new PurchasedTicketsDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                     /*only for test*/
                } else {
                    PurchasedTicketsDialog dialog = new PurchasedTicketsDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                }
                System.out.println("MainFrame:: Manage->Tickets is Pressed");
            }
        });
        manageMenu.add(ticketMenuItem);
    }

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
                    UserInfoDialog dialog = new UserInfoDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                    /*only for test*/
                } else {
                    UserInfoDialog dialog = new UserInfoDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                }
                System.out.println("MainFrame:: Manage->PassengerInfo is Pressed");
            }
        });
        manageMenu.add(userMenuItem);
    }

    protected void addManageMenu() {

        manageMenu = new JMenu("Manage");
        menuBar.add(manageMenu);

        addPurchasedTicketsMenuItem();
        addUserInfoMenuItem();

    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
