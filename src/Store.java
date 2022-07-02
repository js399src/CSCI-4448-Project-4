import Observer.Logger;
import Observer.Tracker;

import java.util.ArrayList;

/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */

public class Store implements Log_output, Random {
    public Clerk activeClerk;
    public Trainer activeTrainer;
    public double cashInRegister;
    public double cashFromBank;
    public Inventory inventory;
    public int today;

    public Observer.Logger logger;

    public Observer.Tracker tracker;

    ArrayList<Employee> clerks;
    ArrayList<Employee> trainers;

    Store() {
        // initialize the store's starting inventory
        inventory = new Inventory();

        cashInRegister = 0;   // cash register is empty to begin
        cashFromBank = 0;   // no cash from bank yet

        // I let the store initialize the store's staff in the constructor
        // This would have to be reconsidered for multiple stores
        clerks = new ArrayList<>();
        clerks.add(new Clerk("Dante",this));
        clerks.add(new Clerk("Randal",this));
        clerks.add(new Clerk("Jason", this));
        trainers = new ArrayList<>();
        trainers.add(new Trainer("Alpa",this, 1));
        trainers.add(new Trainer("Kirk",this, 2));
        trainers.add(new Trainer("Ricky",this, 3));
        tracker = new Tracker(today, "Dante", "Randal", "Jason", "Alpa", "Kirk", "Ricky");
    }

    void openToday(int day) {
        today = day;
        logger = new Logger(today);
        out("Store opens today, day "+day);
        activeClerk = (Clerk) getValidEmployee(clerks);
        out(activeClerk.name + " is the clerk working today.");
        activeTrainer = (Trainer) getValidEmployee(trainers);
        out(activeTrainer.name + " is the trainer working today.");

        // Essentially, I just have the working clerk and trainer do their things
        activeClerk.arriveAtStore();
        activeTrainer.arriveAtStore();
        activeClerk.processDeliveries();
        activeTrainer.feedAnimals();
        activeClerk.checkRegister();
        activeClerk.doInventory();
        activeTrainer.trainAnimals();
        activeClerk.openTheStore();
        activeTrainer.cleanTheStore();
        activeClerk.cleanTheStore();
        activeTrainer.leaveTheStore();
        activeClerk.leaveTheStore();

        logger.notifyAllObservers();
        logger.writeToFile(today);

        tracker.notifyAllObservers();
        tracker.printSummary(today);
    }

    Employee getValidEmployee(ArrayList<Employee> employees) {
        // pick a random employee from the employee list provided
        // and manage the limit on days worked
        // I use the higher level class employee to do this,
        // might want to consider how this would change if I wanted
        // to keep the employee class abstract.  Hmmm...
        Employee employee = employees.get(Random.rndFromRange(0,employees.size()-1));
        // 10% chance employee is sick and cannot work
        if (Random.rnd() <= .1) {
            out("Employee " + employee.name + " is sick and can't work today");
            for (Employee other: employees) {
                // choose employee who isn't sick and hasn't worked 3 days in a row
                if (other.daysWorked <3 && other != employee) employee = other;
            }
            out("Employee " + employee.name + " is replacing them");
        }
        // if they are ok to work, set days worked on other clerks to 0
        if (employee.daysWorked < 3) {
            employee.daysWorked += 1;
            for (Employee other: employees) {
                if (other != employee) other.daysWorked = 0; // they had a day off, so clear their counter
            }
        }
        // if they are not ok to work, set their days worked to 0 and get another clerk
        else {
            out(employee.name+" has worked maximum of 3 days in a row.");
            employee.daysWorked = 0;   // they can't work, get another clerk
            for (Employee other: employees) {
                if (other != employee) {
                    employee = other;
                    break;
                }
            }
        }
        return employee;
    }
}
