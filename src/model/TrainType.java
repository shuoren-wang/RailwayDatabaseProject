package model;

/**
 * Created by shuorenwang on 2016-11-07.
 */
public class TrainType {
    int typeId;
    String color;

    public TrainType(int typeId, String color) {
        this.typeId = typeId;
        this.color = color;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getColor() {
        return color;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
