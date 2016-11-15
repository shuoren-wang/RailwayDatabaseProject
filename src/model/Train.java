package model;

/**
 * Created by shuorenwang on 2016-11-06.
 */
public class Train {
    private int createdByEmployeeID;
    private int updatedByEmployeeID;
    private int id;
    private boolean runsOnSun;
    private boolean runsOnMon;
    private boolean runsOnTue;
    private boolean runsOnWed;
    private boolean runsOnThu;
    private boolean runsOnFri;
    private boolean runsOnSat;
    private boolean isActive;
    private int trainTypeId;
    private String trainTypeColor;
    private int lineId;
    private String  lineName;

    public Train() {}

    public int getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(int trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public String getTrainTypeColor() {
        return trainTypeColor;
    }

    public void setTrainTypeColor(String trainTypeColor) {
        this.trainTypeColor = trainTypeColor;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public int getCreatedByEmployeeID() {
        return createdByEmployeeID;
    }

    public void setCreatedByEmployeeID(int createdByEmployeeID) {
        this.createdByEmployeeID = createdByEmployeeID;
    }

    public int getUpdatedByEmployeeID() {
        return updatedByEmployeeID;
    }

    public void setUpdatedByEmployeeID(int updatedByEmployeeID) {
        this.updatedByEmployeeID = updatedByEmployeeID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRunsOnSun() {
        return runsOnSun;
    }

    public void setRunsOnSun(boolean runsOnSun) {
        this.runsOnSun = runsOnSun;
    }

    public boolean isRunsOnMon() {
        return runsOnMon;
    }

    public void setRunsOnMon(boolean runsOnMon) {
        this.runsOnMon = runsOnMon;
    }

    public boolean isRunsOnTue() {
        return runsOnTue;
    }

    public void setRunsOnTue(boolean runsOnTue) {
        this.runsOnTue = runsOnTue;
    }

    public boolean isRunsOnWed() {
        return runsOnWed;
    }

    public void setRunsOnWed(boolean runsOnWed) {
        this.runsOnWed = runsOnWed;
    }

    public boolean isRunsOnThu() {
        return runsOnThu;
    }

    public void setRunsOnThu(boolean runsOnThu) {
        this.runsOnThu = runsOnThu;
    }

    public boolean isRunsOnFri() {
        return runsOnFri;
    }

    public void setRunsOnFri(boolean runsOnFri) {
        this.runsOnFri = runsOnFri;
    }

    public boolean isRunsOnSat() {
        return runsOnSat;
    }

    public void setRunsOnSat(boolean runsOnSat) {
        this.runsOnSat = runsOnSat;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String toString(){
        String str1= "Type:"+ trainTypeColor+"; Line:"+lineName;
        String str2=String.format("; RunOndays: %s%s%s%s%s%s%s",
                (isRunsOnMon()?"M ":""),
                (isRunsOnTue()?"Tu ":""),
                (isRunsOnWed()?"W ":""),
                (isRunsOnThu()?"Th ":""),
                (isRunsOnFri()?"F ":""),
                (isRunsOnSat()?"Sa ":""),
                (isRunsOnSun()?"Su ":""));
        String str3=isActive()? "; Active": "; Disabled";
        return str1+str2+str3;
    }
}
