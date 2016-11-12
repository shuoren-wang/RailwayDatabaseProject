package model;

/**
 * Created by shuorenwang on 20161016.
 */
public class Station {
    private int id;
    private String addr;
    private String name;
    boolean isActive;
    private int CreatedByEmployeeID;
    private int UpdatedByEmployeeID;

    public Station(int id, String addr, String name, boolean isActive, int createdByEmployeeID, int updatedByEmployeeID) {
        this.id = id;
        this.addr = addr;
        this.name = name;
        this.isActive = isActive;
        CreatedByEmployeeID = createdByEmployeeID;
        UpdatedByEmployeeID = updatedByEmployeeID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCreatedByEmployeeID() {
        return CreatedByEmployeeID;
    }

    public void setCreatedByEmployeeID(int createdByEmployeeID) {
        CreatedByEmployeeID = createdByEmployeeID;
    }

    public int getUpdatedByEmployeeID() {
        return UpdatedByEmployeeID;
    }

    public void setUpdatedByEmployeeID(int updatedByEmployeeID) {
        UpdatedByEmployeeID = updatedByEmployeeID;
    }
}
