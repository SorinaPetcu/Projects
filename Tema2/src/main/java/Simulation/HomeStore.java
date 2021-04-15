package Simulation;

import Entities.Client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class HomeStore implements Runnable {

    private BlockingQueue<Client> queue;
    private int homeNumber;

    HomeStore() {
        this.queue = new LinkedBlockingDeque<Client>();
    }

    public synchronized void addClient(Client client) {
        queue.add(client);
        findWaitingTime();
    }

    public int getWaitingTime() {
        int sum = 0;
        for (Client cl : queue) {
            sum += cl.getServiceTime();
        }
        return sum;
    }

    private void findWaitingTime() {             //determinare timp de asteptare
        int actualWaiting = 0;
        for (Client it : queue) {              //pentru fiecare client se calculeaza timul de asteptare in functie de timpul de sosire servire si asteptare pentru a fi serviti clientii din fata lui
            if (!it.isServed()) {
                actualWaiting -= it.getArrivalTime();
                if (actualWaiting < 0)
                    actualWaiting = 0;
                it.setWaitingTime(actualWaiting);
                actualWaiting = it.getServiceTime() + it.getWaitingTime() + it.getArrivalTime();
            }
        }
    }

    private Client nextServed() {
        for (Client i : this.queue) {
            if (!i.isServed()) {
                return i;
            }
        }
        return null;
    }

    public synchronized void run() {           //metoda apelata la pornirea unui thread de tipul clasei
//        if (open) {
            int timerun = 0;
            for (Client it : queue) {
                if (!it.isServed()) {
                    Client actual;
                    actual = this.nextServed();
                    it.setFinishTime(it.getServiceTime() + it.getWaitingTime());        //se seteaza timpul de iesire din queue relativ la intrare
                    if (timerun == 0) {
                        while (SimulationManager.frame.getRunning().getSeconds() != actual.getArrivalTime()) {
                            System.out.print("");
                        } //threadul e blocat intr-0 bucla nula pana la sosirea primului client
                    }
                    timerun++;
                    it.setServed(true);         //seteaza clientul ca servit
                    if (nextServed() == null) {           //asteapta o perioada de timp
                        while (SimulationManager.frame.getRunning().getSeconds() != queue.peek().getArrivalTime()) {
                            System.out.print("");
                        } //threadul e blocat intr-0 bucla nula pana la sosirea urmatorului client
                    }
                }
            }
//        }
    }

    public void setHomeNumber(int homeNumber) {
        this.homeNumber = homeNumber;
    }

    int getHomeNumber() {
        return homeNumber;
    }

    BlockingQueue<Client> getQueue() {
        return queue;
    }

}
