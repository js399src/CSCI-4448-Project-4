import Observer.Logger;
import Observer.Tracker;

import java.util.ArrayList;

/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */

public class Store implements Log_output, Random {

    public String name;
    public Clerk activeClerk;
    public Trainer activeTrainer;
    public double cashInRegister;
    public double cashFromBank;
    public Inventory inventory;
    public int today;

    public Observer.Logger logger;

    // tracker is static because all stores will share the same tracker
    public static Observer.Tracker tracker;

    static boolean employeesInitialized = false;

    static ArrayList<Employee> clerks = new ArrayList<>();

    static ArrayList<Employee> trainers = new ArrayList<>();


    Store(String name) {

        // name the store
        this.name = name;

        // initialize the store's starting inventory
        inventory = new Inventory(this.name);

        cashInRegister = 0;   // cash register is empty to begin
        cashFromBank = 0;   // no cash from bank yet

        // Should only be called once even if multiple stores initialized
        initializeEmployees();

    }

    void initializeEmployees(){
        if (!employeesInitialized){
            clerks.add(new Clerk("Dante"));
            clerks.add(new Clerk("Randal"));
            clerks.add(new Clerk("Jason"));
            trainers.add(new Trainer("Alpa", 1));
            trainers.add(new Trainer("Kirk", 2));
            trainers.add(new Trainer("Ricky", 3));
            tracker = new Tracker(today, "Dante", "Randal", "Jason", "Alpa", "Kirk", "Ricky");
            employeesInitialized = true;
        } else {
            out("Employee list already initialized");
        }
    }

    void openToday(int day) {
        today = day;
        logger = new Logger(today);
        out(name+" Store opens today, day "+day);
        activeClerk = (Clerk) getValidEmployee(clerks);
        out(activeClerk.name + " is the clerk working today.");
        activeTrainer = (Trainer) getValidEmployee(trainers);
        out(activeTrainer.name + " is the trainer working today.");

        // Essentially, I just have the working clerk and trainer do their things
        activeClerk.arriveAtStore(this);
        activeTrainer.arriveAtStore(this);
        activeClerk.processDeliveries(this);
        activeTrainer.feedAnimals(this);
        activeClerk.checkRegister(this);
        activeClerk.doInventory(this);
        activeTrainer.trainAnimals(this);
        activeClerk.openTheStore(this);
        activeTrainer.cleanTheStore(this);
        activeClerk.cleanTheStore(this);
        activeTrainer.leaveTheStore(this);
        activeClerk.leaveTheStore(this);

        logger.notifyAllObservers();
        logger.writeToFile(today);

        tracker.notifyAllObservers();
        tracker.clearObservers();
        tracker.printSummary(today);
    }

    Employee getValidEmployee(ArrayList<Employee> employees) {
        // pick a random employee from the employee list provided
        // and manage the limit on days worked
        // make sure employees aren't assigned to work at two different stores on same day
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
        // do not set days worked to 0 if employee is working in other store
        // add the day they are working to the employees worked_on_day attribute
        if (employee.daysWorked < 3 && !employee.worked_on_day.contains((Integer)today)) {

            // increase days worked attribute
            employee.daysWorked += 1;

            // add today to their worked_on_day so other store does not assign same employee
            employee.worked_on_day.add((Integer)today);

            for (Employee other: employees) {
                // if other employees aren't the chosen employee to work
                // and they aren't working in the other store
                if ((other != employee) && !other.worked_on_day.contains((Integer)today))
                    other.daysWorked = 0; // they had a day off, so clear their counter
            }
        }
        // if they have worked more than 3 days in a row, set their days worked to 0 and get another clerk
        else if (employee.daysWorked >= 3){
            out(employee.name+" has worked maximum of 3 days in a row.");
            employee.daysWorked = 0;   // they can't work, get another clerk
            for (Employee other: employees) {
                if (other != employee && !other.worked_on_day.contains((Integer)today) && other.daysWorked<3) {
                    employee = other;
                    return employee;
                }
            }
        }
        // check if employee has already worked a day, if they have make sure they are not assigned
        //  to work the same day at a different store
        else if (employee.worked_on_day.contains((Integer)today)){
            out(employee.name+" is already working for the other store");
            for (Employee other: employees) {
                if (other != employee && !other.worked_on_day.contains((Integer)today) && other.daysWorked<3) {
                    employee = other;
                    return employee;
                }
            }
        }
        return employee;
    }
}
