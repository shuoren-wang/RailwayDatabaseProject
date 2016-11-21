package model;

import java.sql.Time;

/**
 * Created by shuorenwang on 2016-10-16.
 */
public class LineStop {
    private int id;
    private Time arrivalTime;
    private Time stopsForDuration;
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

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Time getStopsForDuration() {
        return stopsForDuration;
    }

    public void setStopsForDuration(Time stopsForDuration) {
        this.stopsForDuration = stopsForDuration;
    }

    public boolean isActive() {
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

    public String toString(){
        return "Stat:" +locatedStationId+" ;Line:"+forLineId+" ;active:"+ (isActive()?"true":"false")
                +" ;arrival:"+arrivalTime + " ;duration:"+stopsForDuration;}
}