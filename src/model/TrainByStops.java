package model;


import java.sql.Date;

/**
 * Created by shuorenwang on 2016-11-05.
 * For spGetLineTrainSeats_by_FromToStationDate
 */
public class TrainByStops {
    private Date date;
    private int trainNumber;
    private int dayOfWeek; //(1 = Sunday, 2 = Monday, …, 7 = Saturday)
    private String lineName;
    private int lineId;
    private String seatClass;
    private int availableSeats;
    private Date departTime; //12:00:00
    private Date arrivalTime; //12:00:00
    private int fromStationId;
    private int toStationId;

    public TrainByStops() {}

    public int getFromStationId() {
        return fromStationId;
    }

    public void setFromStationId(int fromStationId) {
        this.fromStationId = fromStationId;
    }

    public int getToStationId() {
        return toStationId;
    }

    public void setToStationId(int toStationId) {
        this.toStationId = toStationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
