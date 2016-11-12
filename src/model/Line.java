package model;

/**
 * Created by shuorenwang on 2016-10-16.
 */
public class Line {
    private int id;
    private boolean isActive ;
    private String lineName;
    private int createdByEmployeeId;
    private int updatedByEmployeeId;

    public Line() {}

    public int getUpdatedByEmployeeId() {
        return updatedByEmployeeId;
    }

    public void setUpdatedByEmployeeId(int updatedByEmployeeId) {
        this.updatedByEmployeeId = updatedByEmployeeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public int getCreatedByEmployeeId() {
        return createdByEmployeeId;
    }

    public void setCreatedByEmployeeId(int createdByEmployeeId) {
        this.createdByEmployeeId = createdByEmployeeId;
    }

    public String toString(){
        return "Name="+getLineName()+", Active= "+isActive();
    }
}
