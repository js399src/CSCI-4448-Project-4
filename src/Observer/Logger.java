package Observer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// this logger gets sent events and logs them to an ArrayList, it also wrtoes what happens each day to a file
public class Logger {

    //used for naming of file
    int day;

    ArrayList<String> log = new ArrayList<>();

    public Logger(){}
    public Logger(int day){
        this.day = day;
    }

    private ArrayList<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void writeToFile(int day){
        try {
            String fileName = "Logger-"+day+".txt";
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + fileName);
            } else {
                System.out.println("File already exists.");
            }
            FileWriter writer = new FileWriter(fileName);
            for (String item: log){
                writer.write(item);
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
