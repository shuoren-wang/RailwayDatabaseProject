package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// import java.util.Date;
import model.*;

import javax.xml.transform.Result;
import java.util.HashSet;

/**
 * Created by shuorenwang on 2016-10-18.
 */
public class JDBC {

    static Connection con;
    private static Statement stmt;
    private static User currentUser;
    private static JDBC instance=new JDBC();

    JDBC(){};

    public static JDBC getInstance(){
        System.out.println("JDBC::Retrieve Database ");
//        if(instance==null) {
//            instance=new JDBC();
//        }

        return instance;
    }

    public void openCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://windflower.arvixe.com/ubc_cpsc304", "cpsc304", "trains");
            stmt = con.createStatement();
            System.out.println("Connection opened");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeCon() {
        try {
            con.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getCon() {
        return con;
    }

    /* User login. Checks username and password against database.
     * Arguments: String username, password
     * Returns:   UserID for successful login, -1 otherwise
     */
    public static int userLogin(String userLogin, String loginPassword) {
        try {
            // ResultSet rs = stmt.executeQuery("CALL spLogin('" + username + "','" + password + "');");
            ResultSet rs = stmt.executeQuery("CALL spLogin('" + userLogin + "','" + loginPassword + "')");
            System.out.println("rs: " + rs);
            if (rs.next()) {
                int userID = rs.getInt("userid");
                int userType = rs.getInt("usertype"); // 0 for passenger, 1 for clerk
                String name;
                if (userType == 0) { // passenger
                    rs = stmt.executeQuery("CALL spSearchPassengerInfo('" + userID + "')");
                    if (rs.next()) {
                        name = rs.getString("name");
                        String number = rs.getString("phonenumber");
                        int passengerID = rs.getInt("passengerid");
                        currentUser = new Passenger(userID, name, userLogin, loginPassword, true, passengerID, number); // currently no stored proc to check if user is active
                    } else {
                        System.out.println("Error: could not find passenger info");
                        return -1;
                        // return false;
                    }
                } else { // clerk
                    rs = stmt.executeQuery("CALL spClerkInfo('" + userID + "')");
                    if (rs.next()) {
                        name = rs.getString("name");
                        currentUser = new User(userID, name, userLogin, loginPassword, true); // currently no stored proc to find clerk info
                    } else {
                        System.out.println("Error: could not clerk info");
                        return -1;
                        // return false;
                    }
                }
                System.out.println("ID: " + userID +"\nName: " + name + "\nLogin: " + userLogin + "\nPassword: " + loginPassword);
                return userID;
                // return true;

            } else {
                System.out.println("Error in username or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Returning -1");
        return -1;
        // return false;
    }

    /* Arguments: int userID
     * Returns: The User associated with userID
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /* Clerk sign up.
     * Returns new User ID if Clerk was successfully added, -1 if username already exists.
     */
    public static int clerkSignUp(String uName, String pass, String fName, String position) {
        try {
            ResultSet rs = stmt.executeQuery("CALL spRegister('" + uName + "','" + pass + "','" + fName + "','0','" + position + "',0);");
            if (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                if (rsmd.getColumnType(1) == 12) { // code for varchar
                    System.out.println("Failed, username " + uName + " already exists");
                    return -1;
                } else {
                    int userID = rs.getInt(1);
                    System.out.println("Success, new userID: " + userID);
                    userLogin(uName, pass);
                    return userID;
                }
            } else {
                System.out.println("Failed, username " + uName + " already exists.");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Returning -1");
        return -1;
    }

    /* Passenger sign up.
     * Returns new User ID if Passenger was successfully added, -1 if username already exists.
     */
    public static int passengerSignUp(String uName, String pass, String fName, String phone) {
        try {
            ResultSet rs = stmt.executeQuery("CALL spRegister('" + uName + "','" + pass + "','" + fName + "','" + phone + "','0',0);");
            // System.out.println("rs: " + rs);
            if (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                if (rsmd.getColumnType(1) == 12) { // code for varchar
                    System.out.println("Failed, username " + uName + " already exists");
                    return -1;
                } else {
                    int userID = rs.getInt(1);
                    System.out.println("Success, new userID: " + userID);
                    userLogin(uName, pass);
                    return userID;
                }
            } else {
                System.out.println("Failed, username " + uName + " already exists.");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Returning -1");
        return -1;
    }

    public static boolean changePassword(int uid, String oldPassword, String newPassword) {
        int ret = 0;
        try {
            ResultSet rs = stmt.executeQuery("CALL spChangePassword(" + uid + ",'" + oldPassword + "','" + newPassword + "')");
            if (rs.next()) {
                ret = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (ret == 1) { return true; }
        else { return false; }
    }

    public static Clerk makeClerk(int uid) {
        String name, userName, password, position = "error";
        int employeeID = -1;
        boolean active = false;

        try {
            ResultSet rs = stmt.executeQuery("CALL spClerkInfo(" + uid + ")");
            if (rs.next()) {
                name = rs.getString("Name");
                userName = rs.getString("UserName");
                position = rs.getString("EmpPosition");
                rs = stmt.executeQuery("SELECT PASSWORD from Users where UserID = " + uid + ";");
                if (rs.next()) {
                    password = rs.getString("PASSWORD");
                    rs = stmt.executeQuery("SELECT EmployeeID from Clerks where UserID = " + uid + ";");
                    if (rs.next()) {
                        employeeID = rs.getInt("EmployeeID");
                        rs = stmt.executeQuery("Select Active from Users where UserID = " + uid + ";");
                        if (rs.next()) {
                            active = rs.getBoolean("Active");
                            Clerk newClerk = new Clerk(uid, name, userName, password, active, employeeID, position);
                            return newClerk;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Passenger makePassenger(int uid) {
        String name, userName, password, phone = "error";
        int passengerID = -1;
        boolean active = false;

        try {
            ResultSet rs = stmt.executeQuery("CALL spPassengerInfo(" + uid + ")");
            if (rs.next()) {
                name = rs.getString("Name");
                userName = rs.getString("UserName");
                phone = rs.getString("PhoneNumber");
                rs = stmt.executeQuery("SELECT PASSWORD from Users where UserID = " + uid + ";");
                if (rs.next()) {
                    password = rs.getString("PASSWORD");
                    rs = stmt.executeQuery("SELECT PassengerID from Passengers where UserID = " + uid + ";");
                    if (rs.next()) {
                        passengerID = rs.getInt("PassengerID");
                        rs = stmt.executeQuery("SELECT Active from Users where UserID = " + uid + ";");
                        if (rs.next()) {
                            active = rs.getBoolean("Active");
                            Passenger newPassenger = new Passenger(uid, name, userName, password, active, passengerID, phone);
                            return newPassenger;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Passenger> fillPassengerList(String orderBy, int take, int offset) {
        ArrayList<Passenger> passengerList = new ArrayList<Passenger>();
        int[] userID = new int[take];
        int[] passengerID = new int[take];
        boolean[] active = new boolean[take];
        String[] password = new String[take];
        String[] name = new String[take];
        String[] username = new String[take];
        String[] phone = new String[take];

        int i = 0;

        try {
            ResultSet rs = stmt.executeQuery("CALL spViewPassengerInfo('" + orderBy + "'," + take + "," + offset + ")");
            while (rs.next()) {
                userID[i] = rs.getInt("userid");
                passengerID[i] = rs.getInt("passengerid");
                username[i] = rs.getString("username");
                name[i] = rs.getString("name");
                phone[i] = rs.getString("phonenumber");
                i++;
            }
            for (int k = 0; k < i; k++) {
                rs = stmt.executeQuery("SELECT PASSWORD from Users where UserID = " + userID[k] + ";");
                if (rs.next()) {
                    password[k] = rs.getString("PASSWORD");
                }
            }
            for (int j = 0; j < i; j++) {
                rs = stmt.executeQuery("SELECT Active from Users where UserID = " + userID[j] + ";");
                if (rs.next()) {
                    active[j] = rs.getBoolean("Active");
                }
            }

            for (int x = 0; x < i; x++) {
                Passenger newPassenger = new Passenger(userID[x], name[x], username[x], password[x], active[x], passengerID[x], phone[x]);
                System.out.println("New Passenger: " + newPassenger.getPassengerID());
                passengerList.add(newPassenger);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengerList;
    }

    public static ArrayList<Passenger> searchPassenger(int uid) {
        ArrayList<Passenger> passenger = new ArrayList<Passenger>();
        int passengerID;
        boolean active;
        String password;
        String name;
        String username;
        String phone;

        try {
            ResultSet rs = stmt.executeQuery("CALL spPassengerInfo(" + uid + ")");
            if (rs.next()) {
                name = rs.getString("Name");
                username = rs.getString("UserName");
                phone = rs.getString("PhoneNumber");
                rs = stmt.executeQuery("SELECT PASSWORD from Users where UserID = " + uid + ";");
                if (rs.next()) {
                    password = rs.getString("PASSWORD");
                    rs = stmt.executeQuery("SELECT PassengerID from Passengers where UserID = " + uid + ";");
                    if (rs.next()) {
                        passengerID = rs.getInt("PassengerID");
                        rs = stmt.executeQuery("SELECT Active from Users where UserID = " + uid + ";");
                        if (rs.next()) {
                            active = rs.getBoolean("Active");
                            Passenger newPassenger = new Passenger(uid, name, username, password, active, passengerID, phone);
                            passenger.add(newPassenger);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passenger;
    }

    public static boolean isUsernameTaken(String username) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * from Users where UserName = '" + username + "';");
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateUserInformation(int uid, String username, String name, String phone, String position) {
        String query = "CALL spModifyUser(" + uid + ",'" + username + "','" + name + "','" + phone + "','" + position + "')";
        try {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                System.out.println("User info successfully changed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Ticket> viewPassengerTickets(int uid) {

        String query = "CALL spViewTickets(" + uid + ")";

        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        List<Integer> id = new ArrayList<Integer>();
        List<Date> departDate = new ArrayList<Date>();
        List<Integer> fromStationId = new ArrayList<Integer>();
        List<Integer> toStationId = new ArrayList<Integer>();
        List<Integer> passengerID = new ArrayList<Integer>();
        List<String> seatClass = new ArrayList<String>();
        List<Integer> seatNo = new ArrayList<Integer>();
        List<Integer> lineId = new ArrayList<Integer>();
        List<Integer> trainNo = new ArrayList<Integer>();
        // User[] user;
        int i = 0;

        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                id.add(i, rs.getInt("TicketId"));
                departDate.add(i, rs.getDate("travelDate"));
                fromStationId.add(i, rs.getInt("fromStationId"));
                toStationId.add(i, rs.getInt("toStationId"));
                passengerID.add(i, rs.getInt("forUserId"));
                // seatClass.add(i, rs.getString("SeatType"));
                // seatNo.add(i, rs.getInt(""))
                lineId.add(i, rs.getInt("LineID"));
                trainNo.add(i, rs.getInt("TrainNumber"));
                // user.add(i, getCurrentUser());
                i++;
            }
            for (int j = 0; j < i; j++) {
                rs = stmt.executeQuery("SELECT ForSeat_Number from Tickets where ID = " + id.get(j) + ";");
                if (rs.next()) {
                    seatNo.add(j, rs.getInt("ForSeat_Number"));
                }
            }
            for (int x = 0; x < i; x++) {
                rs = stmt.executeQuery("SELECT Class from Seats where TrainNumber = " + trainNo.get(x)
                        + " and SeatNumber = " + seatNo.get(x));
                if (rs.next()) {
                    seatClass.add(x, rs.getString("Class"));
                }
            }
            for (int k = 0; k < i; k++) {
                Ticket ticket = new Ticket(id.get(k), departDate.get(k), fromStationId.get(k), toStationId.get(k),
                        passengerID.get(k), seatClass.get(k), seatNo.get(k), lineId.get(k), trainNo.get(k), getCurrentUser());
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public static boolean returnTicket(int uid, int tid) {
        String query = "CALL spReturnTicket(" + uid + "," + tid + ")";
        try {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                if (rs.getInt(1) == 1) return true;
                else return false;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String[] accountRecovery(String username, String name, String phone) {
        String[] ret = new String[2];
        String query = "CALL spPasswordRecovery('" + username + "','" + name + "','" + phone + "')";

        try {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                ret[1] = rs.getString("password");
                rs = stmt.executeQuery("SELECT UserID from Users where UserName = '" + username + "';");
                if (rs.next()) {
                    ret[0] = String.valueOf(rs.getInt("UserID"));
                    return ret;
                }
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

/*
    public static ArrayList<model.Train> fillTrains() throws SQLException {
        try {
            ArrayList<model.Train> trains = new ArrayList<model.Train>();
            ResultSet rs = stmt.executeQuery("SELECT * from Trains");
            while (rs.next()) {
                int trainNumber = rs.getInt(1);
                int dayOfWeek = 2;
                for (int i = 2; i < 9; i++) {
                    if (rs.getBoolean(i)) dayOfWeek = i - 1;
                }
                String lineName = "placeholder";
                int lineId = rs.getInt(10);
                String seatClass = "placeholder";
                int availableSeats = 22; // placeholder;
                Date departTime = new Date(1000000000);
                Date arrivalTime = new Date(1000500000);

                Train newTrain = new Train(trainNumber, dayOfWeek, lineName, lineId, seatClass, availableSeats, departTime, arrivalTime);

                trains.add(newTrain);
            }
            System.out.println("trains filled");
            return trains;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

*/

    public static void printLineWithActiveStops(Statement stmt) throws SQLException {
        ResultSet rs = runStoredProc(stmt, "spShowAllLinesAndStops");

        while (rs.next()) {
            String lineName = rs.getString(1);
            Time arrivalTime = rs.getTime(2);
            Time departureTime = rs.getTime(3);
            String stationName = rs.getString(4);
            String stationAddr = rs.getString(5);

            System.out.println("lineName = " + lineName +
                    ", arrivalTime = " + arrivalTime +
                    "\n,departureTime = " + departureTime +
                    " ,stationName = " + stationName +
                    " ,stationAddress = " + stationAddr);
        }
    }

    public static ResultSet runStoredProc(Statement stmt, String spName, Object... params) throws SQLException {
        List<String> par = new ArrayList<String>();
        for (int i  =0;i<params.length;i++) {
            par.add("'" + params[i] + "'");
        }
        return stmt.executeQuery("CALL " + spName + "(" + String.join(",", par) + ")");
    }


    public static void printUserInfo(Statement stmt) throws SQLException {
        HashSet<User> users = new HashSet<User>();
        ResultSet rs = stmt.executeQuery("SELECT * from Users");

        while (rs.next()) {
            int userID = rs.getInt(1);
            String name = rs.getString(2);
            String userName = rs.getString(3);
            String password = rs.getString(4);
            boolean active=rs.getBoolean(5);

            users.add(new User(userID, name, userName, password,active));
        }

        for (User next : users) {
            System.out.println(" userID = " + next.getUserID() +
                    " name = " + next.getName() +
                    " userName = " + next.getUserName() +
                    " password = " + next.getPassword());
        }
    }

}