package Strategy;

// implantation of interface
public class Training_Strategy {
    private Strategy strategy;

    public Training_Strategy(Strategy strategy){
        this.strategy = strategy;
    }

    public boolean executeStrategy(boolean housebroken){
        return strategy.training_algorithm(housebroken);
    }
}
