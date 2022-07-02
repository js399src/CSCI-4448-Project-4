package Observer;

import java.util.ArrayList;

class EmployeeTracker {
    String name;
    int items;
    double purchasePrice;

    EmployeeTracker(String name){
        this.name = name;
        items = 0;
        purchasePrice = 0;
    }
}

public class Tracker  {

    int day;
    private ArrayList<Observer> observers = new ArrayList<>();

    public ArrayList<EmployeeTracker> list = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public Tracker(int Day, String name1, String name2, String name3,
                   String name4, String name5, String name6){
        this.day = Day;
        list.add(new EmployeeTracker(name1));
        list.add(new EmployeeTracker(name2));
        list.add(new EmployeeTracker(name3));
        list.add(new EmployeeTracker(name4));
        list.add(new EmployeeTracker(name5));
        list.add(new EmployeeTracker(name6));
    };



    // populate tracker list with employees



   public void printSummary(int day){
        System.out.println("Tracker: Day " + day);
        System.out.println("Clerks                          Items Sold                      Purchase Price Total" );
        System.out.println(list.get(0).name + "                           " + list.get(0).items + "                             " + list.get(0).purchasePrice);
        System.out.println(list.get(1).name + "                          " + list.get(1).items + "                             " + list.get(1).purchasePrice);
        System.out.println(list.get(2).name + "                           " + list.get(2).items + "                             " + list.get(2).purchasePrice);
        System.out.println("Trainers                        Items Sold                      Purchase Price Total" );
        System.out.println(list.get(3).name + "                            " + list.get(3).items + "                             " + list.get(3).purchasePrice);
        System.out.println(list.get(4).name + "                            " + list.get(4).items + "                             " + list.get(4).purchasePrice);
        System.out.println(list.get(5).name + "                           " + list.get(5).items + "                             " + list.get(5).purchasePrice);
        System.out.println();

   }
}
/*
           1            10                 2          20                   4          40
 [John, itemsTotal, priceTotal]  [Jason, itemsTotal, priceTotal]  [name, itemsTotal, priceTotal]
  john      1          10         john     2+1        20 + 10            4 + 3        40 + 30



 */