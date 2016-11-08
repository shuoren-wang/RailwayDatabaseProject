package model;

/**
 * Created by shuorenwang on 2016-10-16.
 */
public class Line {
    private int id;
    private boolean isActive ;
    private String lineName;
    private int createdByEmployeeId;

    public Line(int id, boolean isActive, String lineName, int createdByEmployeeId) {
        this.id = id;
        this.isActive = isActive;
        this.lineName = lineName;
        this.createdByEmployeeId = createdByEmployeeId;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getLineName() {
        return lineName;
    }

    public int getCreatedByEmployeeId() {
        return createdByEmployeeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setCreatedByEmployeeId(int createdByEmployeeId) {
        this.createdByEmployeeId = createdByEmployeeId;
    }
}
