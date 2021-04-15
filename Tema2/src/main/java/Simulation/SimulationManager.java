package Simulation;

import Entities.Client;
import Entities.ServiceConsumer;
import UIWindow.SimulationFrame;
import Utilities.InputReader;
import Utilities.WriteOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable {
    private Scheduler scheduler;
    public static SimulationFrame frame;
    private ArrayList<Client> generatedClients;
    WriteOutput writeOutput;
    private InputReader log;

    public SimulationManager(String inputPath, String outputPath) {
        writeOutput = new WriteOutput(outputPath);
        frame = new SimulationFrame();
        generatedClients = new ArrayList<Client>();
        initializeSimulation(inputPath);
        scheduler = new Scheduler(log.getNoQueues());
    }

    private void initializeSimulation(String inputPath){
        try {
            log=new InputReader(inputPath);
            System.out.println(log);
            frame.setMinInterval(log.getMinInterval());
            frame.setMaxInterval(log.getMaxInterval());
            frame.setMinServiceTime(log.getMinServiceTime());
            frame.setMaxServiceTime(log.getMaxServiceTime());
            frame.setSimInterval(log.getSimInterval());
            frame.setNoQueues(log.getNoQueues());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setServiceCustomers() {
        for (HomeStore i : scheduler.getHomeStores()) {
                for (Client j : i.getQueue()) {
                    ServiceConsumer toSet = new ServiceConsumer();
                    toSet.setClient(j);
                    toSet.setHomeNumber(i.getHomeNumber());
                    frame.getConsumers().add(toSet);
                }
        }
    }

    private  void  startThreads() {
        for (int i = 0; i < frame.getNoQueues(); i++) {
            if (!frame.isInvalidData() && scheduler.getServiceThread().get(i).getState() == Thread.State.NEW) {
                try {
                    scheduler.getServiceThread().get(i).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void generateClients() {
        int i = log.getNoClients();          //numarul de clienti generati
        int id=0;
        int lastArrival = 0;
        int forService;
        int arrival;
        int inInterval;
        Random random = new Random();
        if (frame.getMinServiceTime() >= frame.getMaxServiceTime() || frame.getMinInterval() >= frame.getMaxInterval() || frame.getMinInterval() == 0){
            frame.setInvalidData(true);
            System.out.println("Invalid Input Data");
            return;
        }
        while (i > 0 && frame.isSimulationStarted()) {
            Client toAdd = new Client();
            forService = frame.getMinServiceTime() + random.nextInt(frame.getMaxServiceTime() - frame.getMinServiceTime());     //calculeaza timpul de servire random
            inInterval = random.nextInt(frame.getMaxInterval() - frame.getMinInterval()) + frame.getMinInterval();          //interval de sosire random
//            arrival = lastArrival + inInterval;     //susirea efectiva dupa un interval de sosire a celor dinainte
            arrival = inInterval;     //susirea efectiva dupa un interval de sosire a celor dinainte
            lastArrival = arrival;      //vechiul interval de sosire e actualizat
//            if (arrival > (frame.getSimInterval() - forService))
//                break;
            id += 1;
            toAdd.setArrivalTime(arrival);
            toAdd.setId(id);
            toAdd.setServiceTime(forService);
            generatedClients.add(toAdd);
            i--;
        }
    }

    private void waitRun() {
        while (!frame.isSimulationStarted()) {         //asteapta pana vine momentul de simulare
            System.out.print("");
        }
    }


    public synchronized void run() {
        waitRun();
        writeOutput.deleteFile();
        int control = 0;
        double waitingTime = 0.0;
        generateClients();
        int currentClient = -1;
        Collections.sort(generatedClients);
        while (frame.isSimulationStarted()) {
            while (generatedClients.size() > 0 && control==0) {
                    currentClient++;        // start with first client and increment
                    if ( currentClient >= generatedClients.size()) {
                        setServiceCustomers();
                        control++;
                        waitingTime = scheduler.findAvgWaitingTime();     //calculeza timpul mediu de asteptare
                        startThreads();          //pornire threaduri
                        break;
                    }else{
                        scheduler.addClientToQueue(generatedClients.get(currentClient));       //adauga client in coada
                    }
            }
            if (frame.getRunning().getSeconds() > frame.getSimInterval()) {
                break;
            }
            writeOutput();
            try {
                wait(990);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (control == 1) {
            writeOutput.writeToFile("");
            writeOutput.writeToFile("Average waiting time: "+ waitingTime);
        }

    }


    private void writeOutput(){
        writeOutput.writeToFile("Time "+ frame.getRunning().getSeconds());
        writeOutput.writeToFile("");
        writeOutput.writeToFile("Waiting clients:");
        for (Client c: generatedClients){
            if(frame.getRunning().getSeconds() < c.getArrivalTime()) {
                writeOutput.writeToFile("("+c.getId()+","+c.getArrivalTime()+","+c.getServiceTime()+") ");
            }
        }
        writeOutput.writeToFile("");
        int remainingTime;
        for(int eachHome=0; eachHome< scheduler.getHomeStores().size();eachHome++){
            boolean closed = true;
                writeOutput.writeToFile("Queue "+ eachHome+": ");

                for (Client c: scheduler.getHomeStores().get(eachHome).getQueue()){
                    if(frame.getRunning().getSeconds()>= c.getArrivalTime() && frame.getRunning().getSeconds()< c.getArrivalTime()+c.getServiceTime()){
                        remainingTime =    c.getArrivalTime() + c.getServiceTime()  - frame.getRunning().getSeconds();
                        writeOutput.writeToFile("("+c.getId()+","+c.getArrivalTime()+","+remainingTime+") ");
                        closed =  false;
                    }
                }
                if(closed ){
                    writeOutput.writeToFile(" closed");
                }
                writeOutput.writeToFile("");
        }
        writeOutput.writeToFile("");
    }
}
