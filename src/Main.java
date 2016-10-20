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


    public static void printLineWithActiveStops(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT \n" +
                "  l.linename,\n" +
                "  ls.ArrivalTime,\n" +
                "  ADDTIME(ls.arrivaltime, ls.stopsforduration) AS DepartureTime,\n" +
                "  s.StationName,\n" +
                "  s.Address AS StationAddress\n" +
                "FROM \n" +
                "  line l \n" +
                "  INNER JOIN linestops ls ON l.id=ls.forline_id\n" +
                "  INNER JOIN stations s ON ls.locatedstation_id = s.id\n" +
                "WHERE \n" +
                "  STATUS=1\n" +
                "ORDER BY \n" +
                "  Arrivaltime ASC");

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
