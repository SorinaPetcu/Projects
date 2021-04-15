package Entities;

import java.awt.*;

public class ServiceConsumer {
    private Client client;
    private Rectangle shape;
    private int homeNumber;
    private int xPosition;
    private int yPosition;
    private boolean mustPaint;

    public ServiceConsumer() {
        shape = new Rectangle();
        client = new Client();
        mustPaint = false;
    }




    public void setShape(int xPos, int yPos) {
        shape.setBounds(xPos, yPos, 30, 30);
    }

    public int getHomeNumber() {
        return homeNumber;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setHomeNumber(int homeNumber) {
        this.homeNumber = homeNumber;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }


    public Rectangle getShape() {
        return shape;
    }

    public boolean getMustPaint() {
        return mustPaint;
    }

    public void setMustPaint(boolean mustPaint) {
        this.mustPaint = mustPaint;
    }
}
