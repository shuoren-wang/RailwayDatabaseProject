package model;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class Passenger extends User{

    private int passengerID;
    private String phone;


    public Passenger(int passengerID, String phone, int userID, String name, String userName, String password) {
        super(userID, name, userName, password);
        this.passengerID=passengerID;
        this.phone=phone;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public String getPhone() {
        return phone;
    }
}
