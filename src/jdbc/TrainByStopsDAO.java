package jdbc;


import model.TrainByStops;
import model.User;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
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

    public void loadData(int fromStationId, int toStationId, Date travelDate) {
        System.out.println("Load data from trainByStopsList");
        trainByStopsList = new ArrayList<TrainByStops>();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spGetLineTrainSeats_by_FromToStationDate("
                    +fromStationId+","
                    +toStationId+","
                    +"'"+travelDate+"'"+")}");
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


    public List<TrainByStops> getTrainByStopsList(int fromStationId, int toStationId, Date travelDate) {
        loadData( fromStationId,  toStationId,  travelDate);

        return trainByStopsList;
    }
}
