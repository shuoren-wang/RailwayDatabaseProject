package model;

import java.sql.Date;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class Ticket {
    private int id;
    private Date departDate; //2016-10-18
    private int fromStationId;
    private int toStationId;
    private int passengerID;
    private String seatClass;
    private int seatNo;
    private int lineId;
    private int trainNo;
    private User user;

    public Ticket(int id, Date departDate, int fromStationId, int toStationId,
                  int passengerID, String seatClass, int seatNo, int lineId, int trainNo, User user) {
        this.id = id;
        this.departDate = departDate;
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
        this.passengerID = passengerID;
        this.seatClass = seatClass;
        this.seatNo = seatNo;
        this.lineId = lineId;
        this.trainNo = trainNo;
        this.user = user;
    }

    public Ticket() {

    }


    //Used for test interface
   /* public Ticket(){
        this.user=new User();
        this.id = 0;
        this.departDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());;
        this.fromStationId = 0;
        this.toStationId = 0;
        this.passengerID = 0;
        this.seatNo = 0;
        this.trainNo = 0;
        this.seatClass="Economy";
    }*/

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public Date getDepartDate() {
        return departDate;
    }

    public int getFromStationId() {
        return fromStationId;
    }

    public int getToStationId() {
        return toStationId;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public void setFromStationId(int fromStationId) {
        this.fromStationId = fromStationId;
    }

    public void setToStationId(int toStationId) {
        this.toStationId = toStationId;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public void setTrainNo(int trainNo) {
        this.trainNo = trainNo;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String toString(){
        return "From Station Id: "+fromStationId+", To Station Id: "+toStationId
                +", Travel Date: "+departDate;
    }
}
