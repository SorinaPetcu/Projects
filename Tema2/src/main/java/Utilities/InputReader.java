package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class InputReader {
//    Logger logger;
//    private FileHandler fileHandler;

    private int minInterval;
    private int maxInterval;
    private int minServiceTime;
    private int maxServiceTime;
    private int noQueues;
    private int noClients;
    private int simInterval;


    public InputReader(String fileName) throws SecurityException,IOException{
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try{
            noClients =Integer.parseInt( br.readLine());
            noQueues =Integer.parseInt( br.readLine());
            simInterval = Integer.parseInt( br.readLine());
            String[] arrivalIntervals = br.readLine().split(",");
            minInterval =  Integer.parseInt( arrivalIntervals[0]);
            maxInterval =  Integer.parseInt( arrivalIntervals[1]);
            String[] serviceIntervals = br.readLine().split(",");
            minServiceTime =  Integer.parseInt( serviceIntervals[0]);
            maxServiceTime =  Integer.parseInt( serviceIntervals[1]);



        }finally {
            br.close();
        }

    }


    public int getMinInterval() {
        return minInterval;
    }

    public int getMaxInterval() {
        return maxInterval;
    }

    public int getMinServiceTime() {
        return minServiceTime;
    }

    public int getMaxServiceTime() {
        return maxServiceTime;
    }

    public int getNoQueues() {
        return noQueues;
    }

    public int getNoClients() {
        return noClients;
    }

    public int getSimInterval() {
        return simInterval;
    }

    @Override
    public String toString() {
        return "Utilities.InputReader{" +
                "minInterval=" + minInterval +
                ", maxInterval=" + maxInterval +
                ", minServiceTime=" + minServiceTime +
                ", maxServiceTime=" + maxServiceTime +
                ", noQueues=" + noQueues +
                ", noClients=" + noClients +
                ", simInterval=" + simInterval +
                '}';
    }
}
