package model;

import java.sql.Date;

/**
 * Created by shuorenwang on 2016-10-16.
 */
public class LineStop {
    private int id;
    private Date arrivalTime;
    private Date stopsForDuration;
    private boolean status;
    private int locatedStationId;
    private String stationName;
    private int forLineId;
    private String lineName;
    private int createdByEmployeeId;
    private int updatedByEmployeeId;

    public LineStop() {
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getStopsForDuration() {
        return stopsForDuration;
    }

    public void setStopsForDuration(Date stopsForDuration) {
        this.stopsForDuration = stopsForDuration;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getLocatedStationId() {
        return locatedStationId;
    }

    public void setLocatedStationId(int locatedStationId) {
        this.locatedStationId = locatedStationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getForLineId() {
        return forLineId;
    }

    public void setForLineId(int forLineId) {
        this.forLineId = forLineId;
    }

    public int getCreatedByEmployeeId() {
        return createdByEmployeeId;
    }

    public void setCreatedByEmployeeId(int createdByEmployeeId) {
        this.createdByEmployeeId = createdByEmployeeId;
    }

    public int getUpdatedByEmployeeId() {
        return updatedByEmployeeId;
    }

    public void setUpdatedByEmployeeId(int updatedByEmployeeId) {
        this.updatedByEmployeeId = updatedByEmployeeId;
    }
}
