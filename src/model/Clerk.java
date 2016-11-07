package model;

/**
 * Created by shuorenwang on 2016-11-06.
 */
public class Clerk extends User {
    private int employeeId;
    private String position;

    public Clerk(int userID, String name, String userName, String password, boolean active, int employeeId, String position) {
        super(userID, name, userName, password, active);
        this.employeeId = employeeId;
        this.position = position;
    }

    public Clerk() {
        super();
        employeeId = 0;
        position = "employee";
    }


    public int getEmployeeId() {
        return employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
