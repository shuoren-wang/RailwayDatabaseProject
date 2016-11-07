package model;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class Passenger extends User{

    private int passengerID;
    private String phone;

    public Passenger(int userID, String name, String userName, String password, boolean active, int passengerID, String phone) {
        super(userID, name, userName, password, active);
        this.passengerID = passengerID;
        this.phone = phone;
    }

    //Used for test interface
    public Passenger(){
        super();
        passengerID=0;
        phone="7781234567";
    }

    public int getPassengerID() {
        return passengerID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
