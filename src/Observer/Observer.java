package Observer;

// provides the outline for the logger and tracker, update function is overloaded in the individual observers
public abstract class Observer {
    protected Logger logger;
    protected Tracker tracker;
    public abstract void update();
}
