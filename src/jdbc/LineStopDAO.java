package jdbc;

import model.LineStop;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-14.
 */
public class LineStopDAO {
    private static final LineStopDAO instance = new LineStopDAO();

    private List<LineStop> lineStops;
    private Connection con;

    private LineStopDAO() {

        System.out.println("Instantiating LineStopDAO");
    }

    public static final LineStopDAO getInstance() {
        return instance;
    }

    public void init()  {
        try {
            System.out.println("Initializing LineStops");
            con = JDBC.getCon();

        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadData() {
        System.out.println(String.format("Load data from lineStops"));
        lineStops = new ArrayList<LineStop>();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spViewLineStops('id',20,0)}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                LineStop lineStop=new LineStop();
                lineStop.setId(rs.getInt("id"));
                lineStop.setArrivalTime(rs.getTime("arrivaltime"));
                lineStop.setStopsForDuration(rs.getTime("stopsforduration")); //'00:05:00'
                lineStop.setStatus(rs.getBoolean("status"));
                lineStop.setLocatedStationId(rs.getInt("locatedstation_id"));
                lineStop.setStationName(rs.getString("stationname"));
                lineStop.setForLineId(rs.getInt("forline_id"));
                lineStop.setLineName(rs.getString("linename"));
                lineStop.setCreatedByEmployeeId(rs.getInt("createdby_employeeid"));
                lineStop.setUpdatedByEmployeeId(rs.getInt("updatedby_employeeid"));
                lineStops.add(lineStop);
            }
            System.out.println("Load data from lineStops: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //TODO: may need test on time format
    //CALL spModifyLineStop(6,3,'00:10:00',1);
    public void modifyData(LineStop lineStop) {
        System.out.println("Modify data on lineStops");
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spModifyLineStop("
                    +lineStop.getCreatedByEmployeeId()+","
                    +lineStop.getId()+","
                    +"'"+lineStop.getStopsForDuration()+"',"
                    +(lineStop.isActive()? 1 : 0)+")}");
            cs.executeUpdate();
            System.out.println("Update data on lineStops: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertData(LineStop lineStop) {
        System.out.println("Insert data to lineStops");
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spCreateLineStop("
                    +lineStop.getUpdatedByEmployeeId()+","
                    +"'"+lineStop.getArrivalTime()+"',"
                    +"'"+lineStop.getStopsForDuration()+"',"
                    +(lineStop.isActive()? 1 : 0)+","
                    +lineStop.getLocatedStationId()+","
                    +lineStop.getForLineId()+")}");
            cs.executeUpdate();
            System.out.println("Insert data to lineStops success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<LineStop> getLineStops() {
        loadData();
        return lineStops;
    }
}
