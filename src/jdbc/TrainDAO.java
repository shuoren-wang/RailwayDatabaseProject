package jdbc;

import model.Train;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-11.
 */
public class TrainDAO {
    private static final TrainDAO instance = new TrainDAO();

    private List<Train> trains;
    private Connection con;

    private TrainDAO() {
        System.out.println("TrainDAO:: Instantiating ");
    }

    public static final TrainDAO getInstance() {
        return instance;
    }

    public void init()  {
        try {
            System.out.println("Initializing Trains");
            con = JDBC.getCon();

        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadData() {
        System.out.println("TrainDAO:: Load data from trains");
        trains = new ArrayList<Train>();
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

                trains.add(train);
            }
            System.out.println("TrainDAO::Load data from trains: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifyData(Train train) {
        System.out.println("TrainDAO:: Modify data to trains");
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spModifyTrain("
                    +train.getId()+","
                    +train.getUpdatedByEmployeeID()+","
                    +(train.isActive()? 1 : 0)+")}");
            cs.executeUpdate();
            System.out.println("TrainDAO:: Modify trains: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertData(Train train) {
        System.out.println(String.format("Insert data to trains"));
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spCreateTrain("
                    +train.getCreatedByEmployeeID()+","
                    +(train.isRunsOnMon()? 1 : 0)+","
                    +(train.isRunsOnTue()? 1 : 0)+","
                    +(train.isRunsOnWed()? 1 : 0)+","
                    +(train.isRunsOnThu()? 1 : 0)+","
                    +(train.isRunsOnFri()? 1 : 0)+","
                    +(train.isRunsOnSat()? 1 : 0)+","
                    +(train.isRunsOnSun()? 1 : 0)+","
                    +train.getTrainTypeId()+","
                    +train.getLineId()+")}");
            cs.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Train> getTrains() {
        return trains;
    }
}
