/**
 * Created by shuorenwang on 2016-11-11.
 */



import java.sql.*;

import model.*;

import javax.swing.*;
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


    public static void loadData() {
        User user=new User();
        user.setUserID(2);
        System.out.println(String.format("Load data from trainByStopsList"));
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("CALL spViewTickets("+user.getUserID()+")");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();

                ticket.setUser(user);
                ticket.setId(rs.getInt("ticketId"));
                ticket.setFromStationId(rs.getInt("fromStationId"));
                ticket.setToStationId(rs.getInt("toStationId"));
                ticket.setDepartDate(rs.getDate("travelDate"));
                ticket.setSeatClass(rs.getString(6));
                ticket.setLineId(rs.getInt("lineID"));
                ticket.setTrainNo(rs.getInt("TrainNumber"));

                System.out.println(ticket.toString());
            }
            System.out.println("Load data from trainByStopsDAO: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int PurchaseTickets() {
        System.out.println("Purchase Tickets");
        int ticketID=0;
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("CALL spPurchase(9,1,5,'2016-10-18','Business',1,1);");

            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                ticketID = rs.getInt(0);
            }

            System.out.println("ticketID="+ticketID);
        } catch (Exception e) {
            System.out.println("throw error");

            System.out.println(e.getMessage());
//            JOptionPane.showMessageDialog(this,
//                    e.getMessage(),"Warning",
//                    JOptionPane.INFORMATION_MESSAGE);
//        }
        }
        return ticketID;
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

