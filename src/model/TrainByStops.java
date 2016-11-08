package model;


import java.sql.Date;

/**
 * Created by shuorenwang on 2016-11-05.
 */
public class TrainByStops {
    private int trainNumber;
    private int dayOfWeek; //(1 = Sunday, 2 = Monday, …, 7 = Saturday)
    private String lineName;
    private int lineId;
    private String seatClass;
    private int availableSeats;
    private Date departTime; //12:00:00
    private Date arrivalTime; //12:00:00



    public TrainByStops(int trainNumber, int dayOfWeek, String lineName, int lineId, String seatClass, int availableSeats, Date departTime, Date arrivalTime) {
        this.trainNumber = trainNumber;
        this.dayOfWeek = dayOfWeek;
        this.lineName = lineName;
        this.lineId = lineId;
        this.seatClass = seatClass;
        this.availableSeats = availableSeats;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
    }

    public TrainByStops() {}

    public int getTrainNumber() {
        return trainNumber;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getLineName() {
        return lineName;
    }

    public int getLineId() {
        return lineId;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public Date getDepartTime() {
        return departTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
