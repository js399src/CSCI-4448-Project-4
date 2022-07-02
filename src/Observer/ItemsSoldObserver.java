package Observer;

// this gets pushed info that needs to be sent to the tracker
public class ItemsSoldObserver extends Observer {

    int itemsSold;
    int employee_number;
    public ItemsSoldObserver(Tracker tracker, int employee_number, int itemsSold){
        this.tracker = tracker;
        this.tracker.addObserver(this);
        this.employee_number = employee_number;
        this.itemsSold = itemsSold;
    }

    @Override
    public void update(){
        tracker.list.get(employee_number).items += itemsSold;
    }
}
