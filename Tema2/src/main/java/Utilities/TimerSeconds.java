package Utilities;

import java.util.Timer;
import java.util.TimerTask;

 public class TimerSeconds {

    private int seconds =0;
    private Timer time=new Timer();
    private TimerTask task=new TimerTask() {
           @Override
           public void run() {
               seconds++;
           }
       };



    public void start(){
    time.scheduleAtFixedRate(task,1000, 1000);


    }
    public int getSeconds(){
         return seconds;
     }






}
