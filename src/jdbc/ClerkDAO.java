//package jdbc;
//
//import model.Clerk;
//
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by shuorenwang on 2016-11-14.
// */
//public class ClerkDAO {
//    private static final ClerkDAO instance = new ClerkDAO();
//
//    private List<Clerk> clerks;
//    private Connection con;
//
//    private ClerkDAO() {
//
//        System.out.println("Instantiating ClerkDAO");
//    }
//
//    public static final ClerkDAO getInstance() {
//        return instance;
//    }
//
//    public void init()  {
//        try {
//            System.out.println("Initializing Clerks");
//            con = JDBC.getCon();
//
//        }  catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void loadData() {
//        System.out.println(String.format("Load data from clerks"));
//        clerks = new ArrayList<Clerk>();
//        CallableStatement cs = null;
//        try {
//            cs = con.prepareCall("{CALL spViewClerks('id',20,0)}");
//            ResultSet rs = cs.executeQuery();
//
//            if (rs.next()) {
//                Clerk clerk=new Clerk();
//                clerk.setId(rs.getInt(1));
//                clerk.setClerkName(rs.getString(2));
//                clerk.setActive(rs.getBoolean(3));
//                clerk.setCreatedByEmployeeId(rs.getInt(4));
//                clerk.setUpdatedByEmployeeId(rs.getInt(5));
//
//                clerks.add(clerk);
//            }
//            System.out.println("Load data from clerks: success!");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void modifyData(Clerk clerk) {
//        System.out.println("Modify data to clerks");
//        CallableStatement cs = null;
//        try {
//            cs = con.prepareCall("{CALL spModifyClerk("
//                    +clerk.getUpdatedByEmployeeId()+","
//                    +clerk.getId()+","
//                    +"'"+clerk.getClerkName()+"',"
//                    +(clerk.isActive()? 1 : 0)+")}");
//            cs.executeUpdate();
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void insertData(Clerk clerk) {
//        System.out.println(String.format("Insert data to clerks"));
//        CallableStatement cs = null;
//        try {
//            cs = con.prepareCall("{CALL spCreateClerk("
//                    +clerk.getCreatedByEmployeeId()+","
//                    +"'"+clerk.getClerkName()+"',"
//                    +(clerk.isActive()? 1 : 0)+")}");
//            cs.executeUpdate();
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}
