package jdbc;

import model.TrainType;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-12.
 */
public class TrainTypeDAO {
    private static final TrainTypeDAO instance = new TrainTypeDAO();

    private List<TrainType> TrainTypes;
    private Connection con;

    private TrainTypeDAO() {
        System.out.println("TrainTypeDAO:: Instantiating ");
    }

    public static final TrainTypeDAO getInstance() {
        return instance;
    }

    public void init()  {
        try {
            System.out.println("TrainTypeDAO::Initializing ");
            con = JDBC.getCon();

        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadData() {
        System.out.println("TrainTypeDAO:: Load data");
        TrainTypes = new ArrayList<TrainType>();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spViewTrainTypes('id',20,0)}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                TrainType TrainType=new TrainType();
                TrainType.setTypeId(rs.getInt(1));
                TrainType.setColor(rs.getString(2));

                TrainTypes.add(TrainType);
            }
            System.out.println("TrainTypeDAO::Load data from TrainTypes: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<TrainType> getTrainTypes() {
        return TrainTypes;
    }
}
