package Simulation;

import Entities.Client;
import Utilities.ServiceQueue;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<HomeStore> homeStores;         //multime de cozi
    private ServiceQueue queue;
    private ArrayList<Thread> serviceThread;       //multime de threduri

    public Scheduler(int noHome) {
        homeStores = new ArrayList<HomeStore>(noHome);         //setare numar case
        serviceThread = new ArrayList<Thread>(noHome);              //setare numarul de threaduri
        for (int i = 0; i < noHome; i++) {                              //pt fiecare casa
            HomeStore current = new HomeStore();     //se seteaza casa curenta
            Thread currentThread = new Thread(current);
            homeStores.add(current);                            //se adauga casa in multimea de case(cozi)
            serviceThread.add(currentThread);                      //se put thredurile in multime
        }
        queue = new ServiceQueue();
    }

    ArrayList<Thread> getServiceThread() {
        return serviceThread;
    }


    double findAvgWaitingTime() {
        double sum = 0;
        int noClients = 0;
        for (HomeStore i : homeStores) {
            for (Client j : i.getQueue()) {
                sum += j.getWaitingTime();
                noClients++;
            }
        }
        return sum / noClients;
    }

    List<HomeStore> getHomeStores() {
        return homeStores;
    }                   //obtinere multime de case

    void addClientToQueue(Client t) {
        queue.addClient(homeStores, t);
    }

}
