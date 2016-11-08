package model;

/**
 * Created by shuorenwang on 2016-11-06.
 */
public class Train {
    private Clerk clerk;
    private boolean RunsOnSun;
    private boolean RunsOnMon;
    private boolean RunsOnTue;
    private boolean RunsONWed;
    private boolean RunsOnThu;
    private boolean RunsOnFri;
    private boolean RunsONSat;
    Train train;
    Line line;

    public Train(Clerk clerk, boolean runsOnSun, boolean runsOnMon, boolean runsOnTue, boolean runsONWed, boolean runsOnThu, boolean runsOnFri, boolean runsONSat, Train train, Line line) {
        this.clerk = clerk;
        RunsOnSun = runsOnSun;
        RunsOnMon = runsOnMon;
        RunsOnTue = runsOnTue;
        RunsONWed = runsONWed;
        RunsOnThu = runsOnThu;
        RunsOnFri = runsOnFri;
        RunsONSat = runsONSat;
        this.train = train;
        this.line = line;
    }

    public Clerk getClerk() {
        return clerk;
    }

    public boolean isRunsOnSun() {
        return RunsOnSun;
    }

    public boolean isRunsOnMon() {
        return RunsOnMon;
    }

    public boolean isRunsOnTue() {
        return RunsOnTue;
    }

    public boolean isRunsONWed() {
        return RunsONWed;
    }

    public boolean isRunsOnThu() {
        return RunsOnThu;
    }

    public boolean isRunsOnFri() {
        return RunsOnFri;
    }

    public boolean isRunsONSat() {
        return RunsONSat;
    }

    public Train getTrain() {
        return train;
    }

    public Line getLine() {
        return line;
    }

    public void setClerk(Clerk clerk) {
        this.clerk = clerk;
    }

    public void setRunsOnSun(boolean runsOnSun) {
        RunsOnSun = runsOnSun;
    }

    public void setRunsOnMon(boolean runsOnMon) {
        RunsOnMon = runsOnMon;
    }

    public void setRunsOnTue(boolean runsOnTue) {
        RunsOnTue = runsOnTue;
    }

    public void setRunsONWed(boolean runsONWed) {
        RunsONWed = runsONWed;
    }

    public void setRunsOnThu(boolean runsOnThu) {
        RunsOnThu = runsOnThu;
    }

    public void setRunsOnFri(boolean runsOnFri) {
        RunsOnFri = runsOnFri;
    }

    public void setRunsONSat(boolean runsONSat) {
        RunsONSat = runsONSat;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
