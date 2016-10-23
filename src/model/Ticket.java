package model;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class Ticket {
    private int id;
    private Date departDate;
    private int fromLineId;
    private int toLineId;
    private int passengerID;
    private String seatClass;
    private int seatNo;
    private int trainNo;

    public Ticket(int id, Date departDate, int fromLineId, int toLineId, int passengerID, int seatNo, int trainNo, String seatClass) {
        this.id = id;
        this.departDate = departDate;
        this.fromLineId = fromLineId;
        this.toLineId = toLineId;
        this.passengerID = passengerID;
        this.seatNo = seatNo;
        this.trainNo = trainNo;
        this.seatClass=seatClass;
    }

    public Ticket(){
        this.id = 0;
        this.departDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());;
        this.fromLineId = 0;
        this.toLineId = 0;
        this.passengerID = 0;
        this.seatNo = 0;
        this.trainNo = 0;
        this.seatClass="";
    }

    public int getId() {
        return id;
    }

    public Date getDepartDate() {
        return departDate;
    }

    public int getFromLineId() {
        return fromLineId;
    }

    public int getToLineId() {
        return toLineId;
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

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public void setFromLineId(int fromLineId) {
        this.fromLineId = fromLineId;
    }

    public void setToLineId(int toLineId) {
        this.toLineId = toLineId;
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
}
