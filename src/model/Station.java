package model;

/**
 * Created by shuorenwang on 2016-10-16.
 */
public class Station {
    int id;
    String addr;
    String name;
    int createdByEmployeeId;

    public Station(int id, String addr, String name, int createdByEmployeeId) {
        this.id = id;
        this.addr = addr;
        this.name = name;
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

    public int getCreatedByEmployeeId() {
        return createdByEmployeeId;
    }
}
