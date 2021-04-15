package Entities;

public class Client implements Comparable {
    private int id;
    private int arrivalTime;
    private int serviceTime;
    private int finishTime;
    private boolean served =false;
    private int waitingTime;


    public Client() {
    }


    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isServed() {
        return served;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }


    public int compareTo(Object o) {
        return (this.arrivalTime - ((Client) o).arrivalTime);
    }
}
