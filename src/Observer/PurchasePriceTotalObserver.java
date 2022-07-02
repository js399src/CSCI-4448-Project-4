package Observer;

// this gets pushed info that needs to be sent to the tracker
public class PurchasePriceTotalObserver extends Observer {

    double purchasePrice;
    int employee_number;
    public PurchasePriceTotalObserver(Tracker tracker, int employee_number, double purchasePrice){
        this.tracker = tracker;
        this.tracker.addObserver(this);
        this.employee_number = employee_number;
        this.purchasePrice = purchasePrice;
    }

    @Override
    public void update(){
        tracker.list.get(employee_number).purchasePrice += purchasePrice;
    }
}
