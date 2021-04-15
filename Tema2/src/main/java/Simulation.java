import Simulation.SimulationManager;


public class Simulation {

    public static void main(String[] args) {
        SimulationManager simulationManager;
        simulationManager = new SimulationManager(args[0], args[1]);
        Thread principalThread = new Thread(simulationManager);
        principalThread.start();
    }

}
