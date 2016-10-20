import model.User;

import java.sql.*;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://windflower.arvixe.com/ubc_cpsc304", "cpsc304", "trains");
            Statement stmt = con.createStatement();

            printLineWithActiveStops(stmt);
//            printUserInfo(stmt);

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet runStoredProc(Statement stmt, String spName, String... params) throws SQLException {
        return stmt.executeQuery("CALL " + spName + "(" + String.join(",", params) + ")");
    }

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


    public static void printUserInfo(Statement stmt) throws SQLException {
        HashSet<User> users = new HashSet<User>();
        ResultSet rs = stmt.executeQuery("SELECT * from Users");

        while (rs.next()) {
            int userID = rs.getInt(1);
            String name = rs.getString(2);
            String userName = rs.getString(3);
            String password = rs.getString(4);

            users.add(new User(userID, name, userName, password));
        }

        for (User next : users) {
            System.out.println(" userID = " + next.getUserID() +
                    " name = " + next.getName() +
                    " userName = " + next.getUserName() +
                    " password = " + next.getPassword());
        }
    }
}
