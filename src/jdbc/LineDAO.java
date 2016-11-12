package jdbc;

import model.Line;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-11.
 */
public class LineDAO {
    private static final LineDAO instance = new LineDAO();

    private List<Line> lines;
    private Connection con;

    private LineDAO() {

        System.out.println("Instantiating LineDAO");
    }

    public static final LineDAO getInstance() {
        return instance;
    }

    public void init()  {
        try {
            System.out.println("Initializing Lines");
            con = JDBC.getCon();

        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadData() {
        System.out.println(String.format("Load data from lines"));
        lines = new ArrayList<Line>();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spViewLines('id',20,0)}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Line line=new Line();
                line.setId(rs.getInt(1));
                line.setLineName(rs.getString(2));
                line.setActive(rs.getBoolean(3));
                line.setCreatedByEmployeeId(rs.getInt(4));
                line.setUpdatedByEmployeeId(rs.getInt(5));

                lines.add(line);
            }
            System.out.println(String.format("Load data from lines: success!"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifyData(Line line) {
        System.out.println(String.format("Modify data to lines"));
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spModifyLine("
                    +line.getUpdatedByEmployeeId()+","
                    +line.getId()+","
                    +"'"+line.getLineName()+"',"
                    +(line.isActive()? 1 : 0)+")}");
            cs.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertData(Line line) {
        System.out.println(String.format("Insert data to lines"));
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spCreateLine("
                    +line.getCreatedByEmployeeId()+","
                    +"'"+line.getLineName()+"',"
                    +(line.isActive()? 1 : 0)+")}");
            cs.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Line> getLines() {
        return lines;
    }
}
