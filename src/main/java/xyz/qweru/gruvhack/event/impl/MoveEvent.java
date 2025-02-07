package xyz.qweru.gruvhack.event.impl;

import xyz.qweru.api.event.Event;

public class MoveEvent extends Event {
    private double x;
    private double y;
    private double z;
    private boolean onGround;
    public boolean horizontalCollision;

    public MoveEvent(double x, double y, double z, boolean onGround, boolean horizontalCollision) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
        this.horizontalCollision = horizontalCollision;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
