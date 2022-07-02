package Strategy;

// implantation of interface
public class PositiveReinforcement implements Strategy{

    // 50% chance of changing from False to True
    public boolean training_algorithm(boolean housebroken){
        double rand = Math.random();
        // 20% chance of changing from true to false
        if (rand <= .5 && !housebroken){
            housebroken = true;
            System.out.println("Pos Reinforcement training method changed the housebroken trait of the pet.");
        } else {
            System.out.println("Pos Reinforcement training method didn't change the housebroken trait of the pet.");
        }
        return housebroken;
    }
}
