// This is my example code for the Summer 2022 OOAD class Project 2.
// It is NOT perfect Java, it's just my approach.
// If you use this code for your own projects, please credit me in comments.
// Bruce Montgomery - June 2022

public class Main {

    static final int SIM_DAYS = 30;

    public static void main(String[] args) {
        // Short and to the point - instantiate objects and let them work
        Simulation sim = new Simulation();
        sim.startSim(SIM_DAYS);
        sim.summary();
    }

}
