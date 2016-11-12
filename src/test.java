/**
 * Created by shuorenwang on 2016-11-11.
 */



import java.sql.*;

import model.Line;
import model.Train;
import model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class test {
    static Connection con;

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://windflower.arvixe.com/ubc_cpsc304", "cpsc304", "trains");
            Statement stmt = con.createStatement();

//            {
//                Line line = new Line();
//                line.setId(1);
//                line.setLineName("Prime");
//                line.setCreatedByEmployeeId(1);
//                line.setActive(false);
//
//                 modifyData(line);
//            }


//            System.out.println(Integer.getInteger(false));
//            insertData();
            loadData();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void printLineWithActiveStops(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT \n"+
                "  l.linename,\n"+
                "  ls.ArrivalTime,\n"+
                "  ADDTIME(ls.arrivaltime, ls.stopsforduration) AS DepartureTime,\n"+
                "  s.StationName,\n"+
                "  s.Address AS StationAddress\n"+
                "FROM \n"+
                "  line l \n"+
                "  INNER JOIN linestops ls ON l.id=ls.forline_id\n"+
                "  INNER JOIN stations s ON ls.locatedstation_id = s.id\n"+
                "WHERE \n"+
                "  STATUS=1\n"+
                "ORDER BY \n"+
                "  Arrivaltime ASC");

        while (rs.next()) {
            String lineName = rs.getString(1);
            Time arrivalTime = rs.getTime(2);
            Time departureTime = rs.getTime(3);
            String stationName = rs.getString(4);
            String stationAddr = rs.getString(5);

            System.out.println("lineName = "+lineName+
                    ", arrivalTime = "+arrivalTime+
                    "\n,departureTime = "+departureTime+
                    " ,stationName = "+stationName+
                    " ,stationAddress = "+stationAddr);
        }
    }

    public static void modifyData(Line line) {
        System.out.println(String.format("Modify data from lines"));
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spModifyLine("
                    +line.getUpdatedByEmployeeId()+","
                    +line.getId()+","
                    +line.getLineName()+","
                    +(line.isActive()? 1 : 0)+")}");
            cs.executeUpdate();
//            cs = con.prepareCall("CALL spCreateLine(1,'test Line',1);");
            cs.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadData() {

        System.out.println(String.format("Load data from trains"));
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spViewTrains('id',20,0)}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Train train=new Train();
                train.setId(rs.getInt(1));
                train.setRunsOnMon(rs.getBoolean(2));
                train.setRunsOnTue(rs.getBoolean(3));
                train.setRunsOnWed(rs.getBoolean(4));
                train.setRunsOnThu(rs.getBoolean(5));
                train.setRunsOnFri(rs.getBoolean(6));
                train.setRunsOnSat(rs.getBoolean(7));
                train.setRunsOnSun(rs.getBoolean(8));
                train.setTrainTypeId(rs.getInt(9));
                train.setTrainTypeColor(rs.getString(10));
                train.setLineId(rs.getInt(11));
                train.setLineName(rs.getString(12));
                train.setCreatedByEmployeeID(rs.getInt(13));
                train.setUpdatedByEmployeeID(rs.getInt(15));
                train.setActive(rs.getBoolean(16));
                System.out.println("id= "+train.getId()+";  name= "+train.getLineName()
                        +"; isActive= "+train.isActive());

            }
            System.out.println(String.format("Load data from trains: success!"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testProcedure() throws SQLException {
        CallableStatement cs = null;
        cs = con.prepareCall("{CALL spViewLines('id',10,0)}");
        ResultSet rs = cs.executeQuery();

        while (rs.next()) {
            int id=rs.getInt(1);

            System.out.println("id = "+id);
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

