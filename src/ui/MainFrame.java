package ui;

import jdbc.JDBC;
import jdbc.StationDAO;
import jdbc.TicketDAO;
import jdbc.TrainByStopsDAO;
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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import static jdbc.JDBC.*;



/**
 * Created by shuorenwang on 2016-10-21.
 */
public class MainFrame extends JFrame {
	public boolean isPassenger;
	public boolean isClerk;
	
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
    protected JButton purchaseButton;



    protected TicketDAO ticketDAO;
    protected StationDAO stationDAO;
    protected TrainByStopsDAO trainByStopsDAO;

    protected Station fromStation;
    protected Station toStation;
    protected List<Station> stations;
    protected Date date;
    protected TrainByStops currentTrainByStops;
    protected int fromStationIndex;
    protected int toStationIndex;
    protected int index;
    //    protected ArrayList<TrainByStops> trainByStopses;
    protected JDBC jdbc;

    public JMenuItem ticketMenuItem;
    public JMenuItem loginMenuItem;
    public JMenuItem signUpMenuItem;
    public JMenuItem trainTypeMenuItem;
    public JMenuItem recoveryMenuItem;
    public JMenuItem lineMenuItem;
    public JMenuItem trainMenuItem;
    public JMenuItem linestopMenuItem;
    public JMenuItem stationMenuItem;
    public JMenuItem passengerMenuItem;
    public JMenuItem viewAllPassInfoMenuItem;
    public JMenuItem searchPassengerInfoMenuItem;
    public JMenuItem clerkInfoMenuItem;
    public JButton getTrainsButton;


    public static MainFrame getInstance() {
        return instance;
    }

    public MainFrame() {


        jdbc = JDBC.getInstance();
        ticketDAO = TicketDAO.getInstance();
        stationDAO = StationDAO.getInstance();
        trainByStopsDAO = TrainByStopsDAO.getInstance();
        ticketDAO.init();
        stationDAO.init();
        trainByStopsDAO.init();
        stations = stationDAO.getActiveStations();


        ticketMenuItem = new JMenuItem("Purchased Tickets");
        passengerMenuItem = new JMenuItem("PassengerInfo");
        signUpMenuItem = new JMenuItem("Sign Up");
        searchPassengerInfoMenuItem = new JMenuItem("Search Passenger Info");
        viewAllPassInfoMenuItem = new JMenuItem("View All Passenger Info");
        trainMenuItem = new JMenuItem("Manage Trains");
        lineMenuItem = new JMenuItem("Manage Lines");
        stationMenuItem = new JMenuItem("Manage Stations");
        clerkInfoMenuItem = new JMenuItem("ClerkInfo");
        trainTypeMenuItem = new JMenuItem("Manage Train Type");
        recoveryMenuItem = new JMenuItem("Passenger Account Recovery");
        linestopMenuItem = new JMenuItem("Manage Line Stops");
        purchaseButton = new JButton("Purchase");
        getTrainsButton = new JButton("Get Trains");

        ticketMenuItem.setEnabled(false);
        trainTypeMenuItem.setEnabled(false);
        recoveryMenuItem.setEnabled(false);
        lineMenuItem.setEnabled(false);
        linestopMenuItem.setEnabled(false);
        stationMenuItem.setEnabled(false);
        passengerMenuItem.setEnabled(false);
        clerkInfoMenuItem.setEnabled(false);
        searchPassengerInfoMenuItem.setEnabled(false);
        viewAllPassInfoMenuItem.setEnabled(false);
        trainMenuItem.setEnabled(false);
//        purchaseButton.setEnabled(false);
//        purchaseButton.setEnabled(false);

        setTitle("304 Project");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jdbc.closeCon();
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

        JLabel dateLabel = new JLabel("Date(yyyy-mm-dd):");
        mainWindow.add(dateLabel, "cell 0 2");

        addFromStationComboBox();
        addToStationComboBox();
        addDateField();
        addPurchaseButton();
        addGetTrainsButton();
        addEmptyTrainsList();
        pack();
//        setVisible(true);
    }

    //TODO: need to change to only active stations
    protected void addFromStationComboBox() {
        final MainFrame that=this;
        fromStationModel = new DefaultComboBoxModel();
        synchronized (stations) {
            for (Station next : stations) {
                fromStationModel.addElement(next.toString());
            }
        }
        fromStationComboBox = new JComboBox(fromStationModel);
        fromStationComboBox.setMinimumSize(new Dimension(400, 27));
        fromStationComboBox.setMaximumRowCount(10);
        fromStationComboBox.setSelectedIndex(0);
        fromStation = stations.get(0);

        fromStationComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    fromStationIndex = fromStationComboBox.getSelectedIndex();
                    fromStation = stations.get(fromStationIndex);
                    System.out.println("selected station: " + fromStation.getName());
                    that.revalidate();
                    that.repaint();
                }
            }
        });

        mainWindow.add(fromStationComboBox, "cell 1 0, growx");
    }

    //TODO: need to change to only active stations
    protected void addToStationComboBox() {
        final MainFrame that=this;
        toStationModel = new DefaultComboBoxModel();
        synchronized (stations) {
            for (Station next : stations) {
                toStationModel.addElement(next.toString());
            }
        }
        toStationComboBox = new JComboBox(toStationModel);
        toStationComboBox.setMinimumSize(new Dimension(400, 27));
        toStationComboBox.setMaximumRowCount(10);
        toStationComboBox.setSelectedIndex(0);
        toStation = stations.get(0);

        toStationComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    toStationIndex = toStationComboBox.getSelectedIndex();
                    toStation = stations.get(toStationIndex);
                    System.out.println("selected station: " + toStation.getName());
                    that.revalidate();
                    that.repaint();
//                    update();
                }
            }
        });

        //set default station as the first station in database
//        if (toStation == null) {
//            toStation = stations.get(0);
//        }
        mainWindow.add(toStationComboBox, "cell 1 1, growx");
    }

    protected void addGetTrainsButton() {

        mainWindow.add(getTrainsButton, "cell 2 2, alignx trailing");

        getTrainsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (validationCheck()) {
                    //test (1,5,2016-10-18)
                    loadTrainsList();
                }
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

// <<<<<<< HEAD
    protected Date strToDate(String dateStr) {
        return java.sql.Date.valueOf(dateStr);
    }

    protected String dateToStr(Date date) {
        SimpleDateFormat parse = new SimpleDateFormat("yyyy-MM-dd");

        String str = parse.format(date);

        return str;
    }

    protected String timeToStr(Date time) {
        SimpleDateFormat parse = new SimpleDateFormat("hh:mm");

        String str = parse.format(date);

        return str;
    }

    protected void addEmptyTrainsList() {
/* =======
    protected void addTrainsList() {

 >>>>>>> communication2 */

        trainsListModel = new DefaultListModel();
        trainsList = new JList(trainsListModel);
        JScrollPane listScroller = new JScrollPane(trainsList);

        trainsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        trainsList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setPreferredSize(new Dimension(400, 200));
        mainWindow.add(listScroller, "cell 0 3 3 1,grow,span");
        trainsListModel.addElement(String.format("%15s %25s %25s %25s %25s %25s",
                "Line Id.", "Train No", "Seat Class", "Available Seats", "Dep. Time", "Arr. Time"));
    }

    protected void loadTrainsList() {
        trainsListModel.removeAllElements();
        trainsListModel.addElement(String.format("%15s %25s %25s %25s %25s %25s",
                "Line Id.", "Train No", "Seat Class", "Available Seats", "Dep. Time", "Arr. Time"));

        final List<TrainByStops> trainByStopses =
                trainByStopsDAO.getTrainByStopsList(fromStation.getId(), toStation.getId(), strToDate(dateField.getText()));

        for (TrainByStops next : trainByStopses) {
            trainsListModel.addElement(String.format("%15s %25s %25s %25s %25s %25s",
                    Integer.toString(next.getLineId()),
                    Integer.toString(next.getTrainNumber()),
                    next.getSeatClass(),
                    Integer.toString(next.getAvailableSeats()),
                    timeToStr(next.getDepartTime()),
                    timeToStr(next.getArrivalTime())));
        }


        trainsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (trainsList.getSelectedIndex() != -1) {
                        index = trainsList.getSelectedIndex() - 1;
                        System.out.println("index="+index);

                        currentTrainByStops = trainByStopses.get(index);

                        System.out.println("currentTrainByStops: fromStatId="+currentTrainByStops.getLineId()
                                        +"; toStatId"+currentTrainByStops.getTrainNumber());

                    }
                }
            }
        });
    }

    protected void addDateField() {
        dateField = new JTextField();
        mainWindow.add(dateField, "cell 1 2, growx");
    }

    protected void addPurchaseButton() {

        mainWindow.add(purchaseButton, "cell 2 4");

        final MainFrame that = this;
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentTrainByStops == null || !validationCheck()) {
                    JOptionPane.showMessageDialog(that,
                            "No train is selected!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {

                    currentTrainByStops.setFromStationId(fromStation.getId());
                    currentTrainByStops.setToStationId(toStation.getId());
                    currentTrainByStops.setDate(strToDate(dateField.getText()));

                    System.out.println("MainFrame::addPurchaseButton():goto purchseTicket");
                    purchaseTicket();
                    loadTrainsList();
                }
                System.out.println("MainFrame::Purchase Button is Pressed");
            }
        });
    }


    /**
     * purchase tickets; fails if requesting UserId is not clerk and not owner of ticket or ticket doesnt exist
     */
    protected void purchaseTicket(){
        //TODO: delete after implement login, and PurchasedTicketsDialog implemented
        // user=new User();
        ////////////////
        user = getCurrentUser();

        int ticketId =ticketDAO.purchaseTickets(user, currentTrainByStops);

        if(ticketId!=-1){
            JOptionPane.showMessageDialog(this,
                    "Purchase successful ! You Ticket ID is" + Integer.toString(ticketId),
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this,
                    "Purchase failed",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }


    protected void addStartMenu() {
        startMenu = new JMenu("Start");
        menuBar.add(startMenu);

        addSignUpMenuItem();
        addLoginMenuItem();
    }

    protected void addLoginMenuItem() {
        final MainFrame that = this;
        loginMenuItem = new JMenuItem("Sign In");
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


    protected void addSignUpMenuItem() {
        final MainFrame that = this;

        signUpMenuItem = new JMenuItem("Sign Up");
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
        ticketMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (user == null) {
                   /* JOptionPane.showMessageDialog(that,
                            "Sign In Please!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);*/

                     /*only for test*/
                    user = getCurrentUser();
                    PurchasedTicketsDialog dialog = new PurchasedTicketsDialog(user);
                    dialog.setLocationRelativeTo(that);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                     /*only for test*/
                } else {
                    PurchasedTicketsDialog dialog = new PurchasedTicketsDialog(user);
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

    /**
     * @return true if no empty textFields, date format is write
     */
    private boolean validationCheck() {

        String dateStr = dateField.getText();

        if (toStation == null || fromStation == null || dateStr == null) {
            JOptionPane.showMessageDialog(this,
                    "toStation/fromStaion/date is empty",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (toStation.getId() == fromStation.getId()) {
            JOptionPane.showMessageDialog(this,
                    "toStation/fromStaion need to be different",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            try {
                date = strToDate(dateStr);
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "date format is wrong. (right format i.e.2016-10-18)",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);

                return false;
            }
        }
    }

}
