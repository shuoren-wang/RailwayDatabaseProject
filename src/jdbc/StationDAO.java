package jdbc;

import model.Station;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-14.
 */
public class StationDAO {
    private static final StationDAO instance = new StationDAO();

    private List<Station> stations;
    private Connection con;

    private StationDAO() {

        System.out.println("Instantiating StationDAO");
    }

    public static final StationDAO getInstance() {
        return instance;
    }

    public void init()  {
        try {
            System.out.println("Initializing Stations");
            con = JDBC.getCon();

        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadData() {
        System.out.println(String.format("Load data from stations"));
        stations = new ArrayList<Station>();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spViewStations('id',20,0)}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Station station=new Station();
                station.setId(rs.getInt("id"));
                station.setAddr(rs.getString("address"));
                station.setName(rs.getString("stationname"));
                station.setCreatedByEmployeeID(rs.getInt("createdby_employeeid"));
                station.setUpdatedByEmployeeID(rs.getInt("updatedby_employeeid"));
                stations.add(station);
            }
            System.out.println("Load data from stations: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifyData(Station station) {
        System.out.println("Modify data to stations");
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spModifyStation("
                    +station.getId()+","
                    +station.getUpdatedByEmployeeID()+","
                    +"'"+station.getAddr()+"',"
                    +"'"+station.getName()+"',"
                    +(station.isActive()? 1 : 0)+")}");
            cs.executeUpdate();
            System.out.println("Update data from stations: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertData(Station station) {
        System.out.println("Insert data from stations");
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spCreateStation("
                    +station.getCreatedByEmployeeID()+","
                    +"'"+station.getAddr()+"',"
                    +"'"+station.getName()+"',"
                    +(station.isActive()? 1 : 0)+")}");
            cs.executeUpdate();
            System.out.println("Insert data from stations success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Station> getStations() {
        return stations;
    }
}
