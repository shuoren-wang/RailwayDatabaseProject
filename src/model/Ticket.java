package model;

import java.sql.Date;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class Ticket {
    private int id;
    private Date departDate;
    private int fromLineId;
    private int toLineId;
    private int passengerID;
    private int seatNo;
    private int trainNo;

    public Ticket(int id, Date departDate, int fromLineId, int toLineId, int passengerID, int seatNo, int trainNo) {
        this.id = id;
        this.departDate = departDate;
        this.fromLineId = fromLineId;
        this.toLineId = toLineId;
        this.passengerID = passengerID;
        this.seatNo = seatNo;
        this.trainNo = trainNo;
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
}
