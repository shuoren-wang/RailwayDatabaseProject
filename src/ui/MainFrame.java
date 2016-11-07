package ui;

import model.Station;
import model.Train;
import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class MainFrame extends JFrame {
    private User user;

    private static final MainFrame instance = new MainFrame();
    private JMenuBar menuBar;
    private JPanel mainWindow;

    private JComboBox fromStationComboBox;
    private DefaultComboBoxModel fromStationModel;
    private JComboBox toStationComboBox;
    private DefaultComboBoxModel toStationModel;
    private JTextField dateField;
    private JList trainsList;
    private DefaultListModel trainsListModel;
    private Train currentTrain;
    private int index;
    private ArrayList<Train> trains;


    public static MainFrame getInstance() {
        return instance;
    }

    private MainFrame() {
        setTitle("304 Project");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //TODO shutdown database

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

    private void addFromStationComboBox() {
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

    private void addToStationComboBox() {
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

    private void addGetTrainsButton() {
        JButton getTrainsButton = new JButton("Get Trains");
        mainWindow.add(getTrainsButton, "cell 2 2, alignx trailing");

        getTrainsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trains = new ArrayList<Train>();
                //TODO: get data from database
            }
        });

    }

    private void addTrainsList() {


        trainsListModel = new DefaultListModel();
        trainsList = new JList(trainsListModel);
        JScrollPane listScroller = new JScrollPane(trainsList);

        trainsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        trainsList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setPreferredSize(new Dimension(400, 200));
        mainWindow.add(listScroller, "cell 0 3 3 1,grow,span");
        trainsListModel.addElement(String.format("%25s %25s %25s %25s %25s",
                "Line Id.","Train Nos","Seat Class","Available Seats","Departure Time"));

        if (trains != null && trains.size() > 0) {
            ArrayList<String> trainsInfoArr = new ArrayList<String>();
            for (Train next : trains) {
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
                            currentTrain = trains.get(index);
                        }
                    }
                }
            });
        }
    }

    private void addDateField() {
        dateField = new JTextField();
        mainWindow.add(dateField, "cell 1 2, growx");
    }

    private void addPurchaseButton() {
        JButton purchaseButton = new JButton("Purchase");
        mainWindow.add(purchaseButton, "cell 2 4");

        final MainFrame that = this;
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentTrain == null) {
                    JOptionPane.showMessageDialog(that,
                            "No train is selected!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);

                } else {


                }
                System.out.println("MainFrame::Purchase Button is Pressed");
            }
        });
    }

    private void addStartMenu() {
        final MainFrame that = this;
        JMenu startMenu = new JMenu("Start");
        menuBar.add(startMenu);


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

        JMenuItem signUpMenuItem = new JMenuItem("Sign Up");
        signUpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PassengerSignUpDialog dialog = new PassengerSignUpDialog(that);
                dialog.setLocationRelativeTo(that);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                System.out.println("MainFrame:: Start->SignUp is Pressed");
            }
        });
        startMenu.add(signUpMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO shutdown database

                System.exit(0);
            }
        });
        startMenu.add(exitMenuItem);
    }

    private void addManageMenu() {
        final MainFrame that = this;
        JMenu manageMenu = new JMenu("Manage");
        menuBar.add(manageMenu);

        JMenuItem ticketMenuItem = new JMenuItem("Tickets");
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
                    PassengerTicketsDialog dialog = new PassengerTicketsDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                     /*only for test*/
                } else {
                    PassengerTicketsDialog dialog = new PassengerTicketsDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                }
                System.out.println("MainFrame:: Manage->Tickets is Pressed");
            }
        });
        manageMenu.add(ticketMenuItem);

        JMenuItem passengerMenuItem = new JMenuItem("PassengerInfo");
        passengerMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user == null) {
                    /*JOptionPane.showMessageDialog(that,
                            "Sign In Please!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);*/

                    /*only for test*/
                    user = new User();
                    PassengerInfoDialog dialog = new PassengerInfoDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                    /*only for test*/
                } else {
                    PassengerInfoDialog dialog = new PassengerInfoDialog(that);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                }
                System.out.println("MainFrame:: Manage->PassengerInfo is Pressed");
            }
        });
        manageMenu.add(passengerMenuItem);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
