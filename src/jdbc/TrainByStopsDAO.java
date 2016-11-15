package jdbc;


import model.TrainByStops;
import model.User;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.*;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-14.
 */
public class TrainByStopsDAO{
    private static final TrainByStopsDAO instance = new TrainByStopsDAO();

    private List<TrainByStops> trainByStopsList;
    private Connection con;

    private TrainByStopsDAO() {

        System.out.println("Instantiating TrainByStopsDAO");
    }

    public static final TrainByStopsDAO getInstance() {
        return instance;
    }

    public void init() {
        try {
            System.out.println("Initializing TrainByStopss");
            con = JDBC.getCon();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadData() {
        System.out.println(String.format("Load data from trainByStopsList"));
        trainByStopsList = new ArrayList<TrainByStops>();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spGetLineTrainSeats_by_FromToStationDate('id',20,0)}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                TrainByStops trainByStops = new TrainByStops();

                trainByStops.setDate(rs.getDate(1));
                trainByStops.setLineName(rs.getString(3));
                trainByStops.setLineId(rs.getInt(4));
                trainByStops.setTrainNumber(rs.getInt(5));
                trainByStops.setSeatClass(rs.getString(6));
                trainByStops.setAvailableSeats(rs.getInt(7));
                trainByStops.setDepartTime(rs.getDate(8));
                trainByStops.setArrivalTime(rs.getDate(9));

                trainByStopsList.add(trainByStops);
            }
            System.out.println("Load data from trainByStopsDAO: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<TrainByStops> getTrainByStopsList() {
        return trainByStopsList;
    }
}
