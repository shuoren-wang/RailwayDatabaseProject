package model;

/**
 * Created by shuorenwang on 2016-11-07.
 */
public class TrainType {
    private int typeId;
    private String color;

    public TrainType() {
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

    public String toString(){
        return "Color = "+getColor();
    }
}
