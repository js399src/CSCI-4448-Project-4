package Observer;

// gets pushed a string from employee subject
public class OpenStoreObserver extends Observer{

    String log_message;
    public OpenStoreObserver(Logger logger, String str){
        this.logger = logger;
        this.logger.addObserver(this);
        this.log_message = str;
    }
    @Override
    public void update(){
        logger.log.add(log_message);
    }
}