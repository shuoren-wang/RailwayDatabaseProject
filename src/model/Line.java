package model;

/**
 * Created by shuorenwang on 2016-10-16.
 */
public class Line {
    private int id;
    private String address;
    private String StationName;
    private int createdByEmployeeId;

    public Line(int id, String address, String stationName, int createdByEmployeeId) {
        this.id = id;
        this.address = address;
        StationName = stationName;
        this.createdByEmployeeId = createdByEmployeeId;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getStationName() {
        return StationName;
    }

    public int getCreatedByEmployeeId() {
        return createdByEmployeeId;
    }
}
