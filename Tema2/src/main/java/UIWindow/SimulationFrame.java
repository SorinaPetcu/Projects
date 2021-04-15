package UIWindow;

import Entities.ServiceConsumer;
import Utilities.TimerSeconds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SimulationFrame implements ActionListener {
    private JFrame WindowFrame;
    private UIWindow.Renderer renderer;              //panou aplicatie
    private final int WIDTH = 900, HEIGHT = 600;        //dimensiuni
    private ArrayList<ServiceConsumer> consumers;           //lista clientilor de desenat
    private TimerSeconds running;           //timer pentru sincronizare
    private boolean isRunning;          //starea ferestrei
    private int minInterval;            //input-urile de functionare
    private int maxInterval;
    private int minServiceTime;
    private int maxServiceTime;
    private int noQueues;               //numarul de cozi
    private int simInterval;            //perioada de simulare
    private boolean invalidData = false;        //starea datelor de intrare
    private Timer timer;

    public SimulationFrame() {          //initializare date
        consumers = new ArrayList<ServiceConsumer>();
        WindowFrame = new JFrame();
        MenuPanel startPanel = new MenuPanel();
        renderer = new Renderer();
        running = new TimerSeconds();
        timer = new Timer(500, this);         //timper care apeleaza metoda actionPerformed la fiecare 500 ms
        WindowFrame.add(startPanel);        //adaugarea panourilor pe fereastra
        WindowFrame.add(renderer);
        WindowFrame.setTitle("Service Queue Simulation");
        WindowFrame.setSize(WIDTH, HEIGHT);
        WindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WindowFrame.setVisible(true);
        WindowFrame.setResizable(true);
        isRunning = false;
        timer.start();      //pornire timer
    }

    private void addForm() {                    //adaugare forma pentru fiecare client(Rectangle)
        for (ServiceConsumer i : consumers) {
            i.setShape(i.getxPosition(), i.getyPosition());
        }
    }

    private void paintClients(Graphics g, ServiceConsumer c) {         //desenare efectiva
        g.setColor(Color.BLUE);
        g.fillRect(c.getShape().x, c.getShape().y, c.getShape().width, c.getShape().height);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        g.drawString("" + c.getClient().getId(), c.getShape().x + 10, c.getShape().y + 20);       //in fiecare patrat se va gasi un numar care reprezinta id-ul clientului in cauza
    }



    public void actionPerformed(ActionEvent e) {
        if (isRunning) {            //cat timp aplicatia trebuie sa ruleze se verifica fiecare daca e timpul ca clientul sa fie desenat si se transmite mai departe spre metoda repaint
            try {
                if (!invalidData)
                    running.start();
            } catch (Exception ignored) {
            }
            for (ServiceConsumer c : consumers) {
                if (running.getSeconds() == c.getClient().getArrivalTime()) {
                    c.setMustPaint(true);
                }
            }
            for (int c = 0; c < consumers.size(); c++) {
                if (running.getSeconds() > consumers.get(c).getClient().getArrivalTime() + consumers.get(c).getClient().getServiceTime() + consumers.get(c).getClient().getWaitingTime()) {
                    consumers.remove(c);
                }
            }
            detXPosition();         //se determina coordonatele si se seteaza forma "shape" la pozitile calculate
            detYPosition();
            addForm();
        }
        if (isRunning)
            renderer.repaint();
    }

    void repaint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);                //setare culoare
        paintHomeStores(g);                     //desenare case
        if (isRunning) {
            g.setColor(Color.black.darker());
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
            g.drawString("Seconds " + running.getSeconds(), 10, 30);      //afisare timer
        }
        if (invalidData) {          //daca datele de intrare sunt invalide se deseneaza "Invalid data set" pe fereastra
            g.setColor(Color.red);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
            g.drawString("Invalid data set", 500, 30);
        }
        for (ServiceConsumer c : consumers) {
            if (c.getMustPaint())           //atata timp cat sun clienti de desenat
                paintClients(g, c);                 //deseneaza
        }
    }

    private void paintHomeStores(Graphics g) {              //desenare case
        g.setColor(Color.GREEN);
        int xCord;
        for (int qNo = 0; qNo< noQueues; qNo++){
            g.setColor(Color.GREEN);
            xCord=35+qNo*140;
            g.fillRect(xCord, 520, 70, 30);

            g.setColor(Color.BLACK);
            g.setFont(new Font(Font.SERIF, Font.BOLD, 12));
            g.drawString("Queue "+ qNo , xCord+10 , 540);       //in fiecare patrat se va gasi un numar care reprezinta id-ul clientului in cauza
        }
    }

    private void detXPosition() {                   //setarea pozitie pe X in functie de casa
        for (ServiceConsumer i : consumers) {
            i.setxPosition(55+i.getHomeNumber()*140);
        }
    }

    private void detYPosition() {           //calcularea cooronatei Y pe fereastra
        int yPos = 520;
        int respectiveHome = 0;
        for (ServiceConsumer i : consumers) {
            if (i.getHomeNumber() != respectiveHome) {
                yPos = 520;
                respectiveHome = i.getHomeNumber();
            }
            yPos -= 52;
            if (yPos < 50) {
                yPos = -1000;
            }
            i.setyPosition(yPos);
        }
    }

    public TimerSeconds getRunning() {
        return running;
    }

    public int getMaxInterval() {
        return maxInterval;
    }

    void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public int getMaxServiceTime() {
        return maxServiceTime;
    }

    public int getMinInterval() {
        return minInterval;
    }

    public ArrayList<ServiceConsumer> getConsumers() {
        return consumers;
    }


    public void setInvalidData(boolean invalidData) {
        this.invalidData = invalidData;
    }

    public void setNoQueues(int noQueues) {
        this.noQueues = noQueues;
    }

    public void setMaxInterval(int maxInterval) {
        this.maxInterval = maxInterval;
    }

    public void setMinInterval(int minInterval) {
        this.minInterval = minInterval;
    }
    public int getNoQueues() {
        return noQueues;
    }

    public int getSimInterval() {
        return simInterval;
    }

    public int getMinServiceTime() {
        return minServiceTime;
    }


    public void setMaxServiceTime(int maxServiceTime) {
        this.maxServiceTime = maxServiceTime;
    }

    public void setMinServiceTime(int minServiceTime) {
        this.minServiceTime = minServiceTime;
    }

    public void setSimInterval(int simInterval) {
        this.simInterval = simInterval;
    }

    public boolean isSimulationStarted() {
        return isRunning;
    }

    public boolean isInvalidData() {
        return invalidData;
    }
}


