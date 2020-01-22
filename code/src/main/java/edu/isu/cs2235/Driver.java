package edu.isu.cs2235;

public class Driver {
    public static void main(String[] args) {
        Simulation sim = new Simulation(18, 10, 50, 1024);
        sim.runSimulation();
    }
}
