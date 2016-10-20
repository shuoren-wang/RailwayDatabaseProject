package model;

import java.sql.Date;

/**
 * Created by shuorenwang on 2016-10-16.
 */
public class LineStop {
    private int id;
    private Date arrivalTime;
    private Date stopsForDuration;
    private boolean status;
    private int locatedStationId;
    private int forLineId;

    public LineStop(int id, Date arrivalTime, Date stopsForDuration, boolean status, int locatedStationId, int forLineId) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.stopsForDuration = stopsForDuration;
        this.status = status;
        this.locatedStationId = locatedStationId;
        this.forLineId = forLineId;
    }

    public int getId() {
        return id;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public Date getStopsForDuration() {
        return stopsForDuration;
    }

    public boolean isStatus() {
        return status;
    }

    public int getLocatedStationId() {
        return locatedStationId;
    }

    public int getForLineId() {
        return forLineId;
    }
}
