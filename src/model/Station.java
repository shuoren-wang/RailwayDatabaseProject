package model;

/**
 * Created by shuorenwang on 2016-10-16.
 */
public class Station {
    int id;
    String addr;
    String name;
    boolean isActive;
    int createdByEmployeeId;


    public Station(int id, String addr, String name, boolean isActive,int createdByEmployeeId) {
        this.id = id;
        this.addr = addr;
        this.name = name;
        this.isActive=isActive;
        this.createdByEmployeeId = createdByEmployeeId;
    }

    public int getId() {
        return id;
    }

    public String getAddr() {
        return addr;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getCreatedByEmployeeId() {
        return createdByEmployeeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCreatedByEmployeeId(int createdByEmployeeId) {
        this.createdByEmployeeId = createdByEmployeeId;
    }
}
