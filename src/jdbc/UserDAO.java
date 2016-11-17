package jdbc;

import model.Clerk;
import model.Passenger;
import model.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-14.
 */
public class UserDAO {
    private static final UserDAO instance = new UserDAO();

    private List<User> users;
    private Connection con;

    private UserDAO() {
        System.out.println("Instantiating UserDAO");
    }

    public static final UserDAO getInstance() {
        return instance;
    }

    public void init() {
        try {
            System.out.println("Initializing UserDAO");
            con = JDBC.getCon();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * cell(0,0) is 1 if success, 0 if failure. fails if old passoword do not match
     * @param userID
     * @param old_pwd
     * @param new_pwd
     */
    public boolean changePassword(int userID, String old_pwd, String new_pwd) {
        System.out.println(String.format("UserDAO:: changePassword()"));
        users = new ArrayList<User>();
        CallableStatement cs = null;
        boolean successful=false;
        try {
            //CALL spChangePassword(1,'user1password','user1passwordChanged');
            cs = con.prepareCall("{CALL spChangePassword("
                    +userID+","
                   +"'"+old_pwd+"',"
                   +"'"+new_pwd+"')}");
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                successful=(rs.getInt(1)==1);
            }
            System.out.println("UserDAO:: changePassword(): success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return successful;
    }

    public Clerk loadClerkInfo(int userId) {
        System.out.println(String.format("UserDAO:: getClerkInfo()"));
        users = new ArrayList<User>();
        CallableStatement cs = null;
        Clerk clerk=new Clerk();

        try {
            cs = con.prepareCall("CALL spClerkInfo("+userId+")");
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                clerk.setUserID(userId);
                clerk.setEmployeeId(rs.getInt("employeeid"));
                clerk.setUserName(rs.getString("username"));
                clerk.setName("name");
                clerk.setPosition("empposition");
            }
            System.out.println("UserDAO:: getClerkInfo(): success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return clerk;
    }

    public Passenger loadPassengerInfo(int userId) {
        System.out.println(String.format("Load data from trainByStopsList"));
        users = new ArrayList<User>();
        CallableStatement cs = null;
        Passenger passenger=new Passenger();

        try {
            cs = con.prepareCall("CALL spPassengerInfo("+userId+")");
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                passenger.setUserID(userId);
                passenger.setPassengerID(rs.getInt("phonenumber"));
                passenger.setUserName(rs.getString("username"));
                passenger.setName("name");
            }
            System.out.println("Load data from trainByStopsDAO: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return passenger;
    }

}
