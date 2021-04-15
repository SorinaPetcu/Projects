package Utilities;

import Entities.Client;
import Simulation.HomeStore;

import java.util.List;

public class ServiceQueue {

    public void addClient(List<HomeStore> l, Client c) {
        int waitingTime=Integer.MAX_VALUE;
        int index=0;
        for(int i=0;i<l.size();i++){
            if( l.get(i).getWaitingTime()<waitingTime){            //pentru fiecare clasa se calculeaza suma timpilor de servire pentru clientii aflati deja la coada
                waitingTime=l.get(i).getWaitingTime();
                index=i;
            }
        }
        l.get(index).setHomeNumber(index);       //se seteaza numarul casei
        l.get(index).addClient(c);      //se adauga in coada
    }
}
