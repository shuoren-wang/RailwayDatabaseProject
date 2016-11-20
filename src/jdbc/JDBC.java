package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// import java.util.Date;
import model.Passenger;
import model.User;
import model.Train;
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