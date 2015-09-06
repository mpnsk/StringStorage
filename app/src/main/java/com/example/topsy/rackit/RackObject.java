package com.example.topsy.rackit;

/**
 * Created by topsy on 9/4/15.
 */
public class RackObject {
    String name;
    int xPosition, yPosition;

    public RackObject(String name) {
        this.name = name;
    }

    public RackObject(String name, int xPosition, int yPosition) {

        this.name = name;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RackObject that = (RackObject) o;

        if (getxPosition() != that.getxPosition()) return false;
        if (getyPosition() != that.getyPosition()) return false;
        return getName().equals(that.getName());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getxPosition();
        result = 31 * result + getyPosition();
        return result;
    }
}
