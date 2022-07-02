package Strategy;

// implantation of interface
public class NegativeReinforcement implements Strategy{

    // 20% chance of housebroken changing from True to False; 40% chance
    //of changing from False to True
    public boolean training_algorithm(boolean housebroken){
        double rand = Math.random();
        // 20% chance of changing from true to false
        if (rand <= .2 && housebroken){
            housebroken = false;
            System.out.println("Neg Reinforcement training method changed the housebroken trait of the pet.");
            return housebroken;
        } else {
            System.out.println("Neg Reinforcement training method didn't change the housebroken trait of the pet.");
        }
        // 40% chance of changing from false to true
        if (rand <= .4 && !housebroken){
            housebroken = true;
            System.out.println("Neg Reinforcement training method changed the housebroken trait of the pet.");
        } else {
            System.out.println("Neg Reinforcement training method didn't change the housebroken trait of the pet.");
        }
        return housebroken;
    }
}
