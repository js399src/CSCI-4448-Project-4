package Strategy;

// implantation of interface
public class Haphazard implements Strategy{

    // 10% chance of toggling the housebroken attribute (if True, becomes False; if False
    //becomes True)
    public boolean training_algorithm(boolean housebroken){
        double rand = Math.random();
        if (rand <= .1){
            housebroken = !housebroken;
            System.out.println("Haphazard training method changed the housebroken trait of the pet.");
        } else {
            System.out.println("Haphazard training method didn't change the housebroken trait of the pet.");
        }
        return housebroken;
    }
}
